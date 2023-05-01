package com.csahmed.deardiary.domain.useCases.board


import com.csahmed.deardiary.domain.model.Node
import com.csahmed.deardiary.domain.repository.BoardSessionRepository

class AddNode (private val boardSessionRepository: BoardSessionRepository){

    suspend operator fun invoke(node: Node){
        boardSessionRepository.insertNode(node)
    }
}
