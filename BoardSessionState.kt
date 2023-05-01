package com.csahmed.deardiary.ui.presentation.addEditBoardSession

import com.csahmed.deardiary.domain.model.Node

data class BoardSessionState(
    val nodes : List<Node> = emptyList()
)
