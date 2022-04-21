package com.avanesov.youtubebot.botservice

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional(propagation = Propagation.REQUIRED)
@Service
class WordService(
    private val wordRepository: WordRepository
) {
    fun saveWords(words: Iterable<String>) {
        for (word in words) {
            wordRepository.save(Word().apply {
                id = UUID.randomUUID()
                name = word
            })
        }
    }

    fun getStats(): String {
        return wordRepository.getStats().joinToString("\n")
    }
}

interface WordRepository : JpaRepository<Word, UUID> {
    @Query("select new com.avanesov.youtubebot.botservice.WordStat(w.name, count(w.id) as total) from Word w group by w.name order by total")
    fun getStats(): List<WordStat>
}

data class WordStat(val name: String, val total: Long)