package com.csahmed.deardiary.ui.presentation.addEditBoardSession

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csahmed.deardiary.domain.useCases.board.BoardSessionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardSessionViewModel @Inject constructor(
    private val boardSessionUseCases: BoardSessionUseCases,
    savedStateHandle: SavedStateHandle) :ViewModel() {

    private val _state = mutableStateOf(BoardSessionState())
    val sessionState: State<BoardSessionState> = _state

    private var sid = mutableStateOf(savedStateHandle.get<String>("sessionId"))

    private var getNodesJob: Job? = null

    init {
        getNodes(sessionId = sid.value!!)
    }

    fun onBoardEvent(sessionEvent: BoardSessionEvent){
        when(sessionEvent){
            is BoardSessionEvent.AddNode -> {
            viewModelScope.launch {
                sessionEvent.node.sessionId = sid.value!!
                boardSessionUseCases.addNode(sessionEvent.node)
            }
            }
            is BoardSessionEvent.DeleteNode -> {
                viewModelScope.launch {
                    boardSessionUseCases.deleteNode(sessionEvent.nodeId)
                }
            }
            is BoardSessionEvent.UpdateNode -> {
                viewModelScope.launch {
                    boardSessionUseCases.updateNode(sessionEvent.content,sessionEvent.nodeId)
                }
            }
        }
    }
    private fun getNodes(sessionId:String) {
        getNodesJob?.cancel()
        getNodesJob = boardSessionUseCases.getNodes(sessionId)
            .onEach { nodes ->
                _state.value = _state.value.copy(
                   nodes = nodes
                )
            }
            .launchIn(viewModelScope)
    }


}