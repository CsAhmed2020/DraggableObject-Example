package com.csahmed.deardiary.ui.presentation.addEditBoardSession


import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.csahmed.deardiary.R
import com.csahmed.deardiary.domain.model.Node
import com.csahmed.deardiary.ui.presentation.addEditBoardSession.components.BoardTextField
import com.csahmed.deardiary.ui.presentation.notes.components.EmptyListInfo
import com.csahmed.deardiary.ui.theme.FabBackColor
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


//session>> refer to a screen contain some related DraggableObjects
@Composable
fun EditBoard() {
    val boardSessionViewModel: BoardSessionViewModel = hiltViewModel()
    val state = boardSessionViewModel.sessionState.value
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()
    val isInSaving = remember { mutableStateOf(false)}

    Scaffold(modifier = Modifier.fillMaxSize(),
    scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize().padding(it)
        ) {
            if (state.nodes.isEmpty()) { //no available DraggableObjects
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EmptyListInfo(
                        iconResId = R.drawable.ic_note_push_attachments_3604607,
                        messageResId = R.string.empty_sessions,
                        errorMessageResId = R.string.empty_sessions_msg
                    )
                }

            } else { //show last-saved DraggableObjects if exist
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    state.nodes.forEach { node ->
                        MultipleDraggableObject(
                            nodeItem = NodeItem(node.content, id = node.id),
                            onDeleteNode = { nodeId ->
                                boardSessionViewModel.onBoardEvent(
                                    BoardSessionEvent.DeleteNode(
                                        nodeId
                                    )
                                )
                            },
                            isSaving = isInSaving
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton( //save DraggableObjects button
                    onClick = {
                        if (state.nodes.isNotEmpty()) {
                            isInSaving.value = !isInSaving.value
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "Saved",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }else {
                            snackbarCoroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "Create nodes to save them!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                    },
                    Modifier
                        .background(color = MaterialTheme.colors.FabBackColor, shape = CircleShape)
                        .padding(start = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Save,
                        contentDescription = "save session",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { //add new DraggableObject (called node here)
                        boardSessionViewModel.onBoardEvent(BoardSessionEvent.AddNode(Node(content = "New Node")))
                    },
                    Modifier
                        .background(color = MaterialTheme.colors.FabBackColor, shape = CircleShape)
                        .padding(end = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add node",
                        tint = Color.White
                    )
                }
            }
        }
    }

}

//composable function for DraggableObject
@Composable
fun MultipleDraggableObject(nodeItem: NodeItem,onDeleteNode:(nodeId:String) -> Unit,
                            isSaving:MutableState<Boolean>
                            ) {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }

    val content = remember { mutableStateOf(nodeItem.content)}

    val isInSaving = remember { mutableStateOf(isSaving)}

    val boardSessionViewModel: BoardSessionViewModel = hiltViewModel()

    if (isInSaving.value.value){

        boardSessionViewModel.onBoardEvent(BoardSessionEvent.UpdateNode(content = content.value, nodeId = nodeItem.id))
    }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = offsetY.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            }
            .size(200.dp)
    ) {
        IconButton(modifier = Modifier //icon button to delete created node from db
            .align(Alignment.TopCenter)
            .background(MaterialTheme.colors.FabBackColor)
            .size(20.dp)
            ,onClick = {
            onDeleteNode.invoke(nodeItem.id)
        }) {
            Icon(imageVector = Icons.Default.Cancel, contentDescription = "delete Node",
            tint = Color.White)
        }
            Image( //the background image / shape of DraggableObject
                painter = painterResource(id = R.drawable.card_back),
                contentDescription = "back",
                modifier = Modifier.fillMaxSize()
            )
                BoardTextField(text = content.value, //TextField (our DraggableObject)
                    onValueChange = {
                        content.value = it

                    }, textStyle = MaterialTheme.typography.body1
                )

    }
}


