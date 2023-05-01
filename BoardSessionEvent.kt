package com.csahmed.deardiary.ui.presentation.addEditBoardSession

import com.csahmed.deardiary.domain.model.Node

sealed class BoardSessionEvent {
    data class AddNode(val node: Node) : BoardSessionEvent()
    data class DeleteNode(val nodeId: String) : BoardSessionEvent()
    data class UpdateNode(val content:String,val nodeId:String) : BoardSessionEvent()
}
