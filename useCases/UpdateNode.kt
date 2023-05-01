package com.csahmed.deardiary.domain.useCases.board

import com.csahmed.deardiary.domain.repository.BoardSessionRepository

class UpdateNode(private val boardSessionRepository: BoardSessionRepository) {
    suspend operator fun invoke(content:String,nodeId: String){
        boardSessionRepository.updateNode(content, nodeId)
    }
}