package com.csahmed.deardiary.domain.useCases.board



data class BoardSessionUseCases(
    val getNodes: GetNodes,
    val deleteNode: DeleteNode,
    val addNode: AddNode,
    val updateNode: UpdateNode
)
