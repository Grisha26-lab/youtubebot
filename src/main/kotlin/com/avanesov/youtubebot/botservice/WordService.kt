package com.avanesov.youtubebot.botservice

import com.avanesov.youtubebot.entity.Word
import com.avanesov.youtubebot.repository.WordRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class WordService(
        private val wordRepository: WordRepository
){
    fun saveWord(words:Iterable<String>){
        for (word in words) {
            wordRepository.save(Word().apply {
                id = UUID.randomUUID()
                name = word
            })
        }
    }
    fun getStats(): String {
        return wordRepository.getByStatus().joinToString("\n")
    }
}




