package com.csahmed.deardiary.domain.useCases.board


import com.csahmed.deardiary.domain.repository.BoardSessionRepository

class DeleteNode(private val boardSessionRepository: BoardSessionRepository) {

    suspend operator fun invoke(nodeId: String) {
        boardSessionRepository.deleteNode(nodeId)
    }

}
