package com.csahmed.deardiary.domain.useCases.board

import com.csahmed.deardiary.domain.model.Node
import com.csahmed.deardiary.domain.repository.BoardSessionRepository
import kotlinx.coroutines.flow.Flow

class GetNodes(
    private val boardSessionRepository: BoardSessionRepository
    ) {
    operator fun invoke(sessionId:String): Flow<List<Node>> {
        return boardSessionRepository.getAllNodes(sessionId)
    }
}
