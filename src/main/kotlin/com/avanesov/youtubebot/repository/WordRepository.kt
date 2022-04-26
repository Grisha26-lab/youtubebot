package com.avanesov.youtubebot.repository

import com.avanesov.youtubebot.entity.Word
import com.avanesov.youtubebot.entity.WordStat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface WordRepository: JpaRepository<Word, UUID> {
    @Query("select new com.avanesov.youtubebot.entity.WordStat(w.name, count(w.id) as total) from Word w group by w.name order by total")
    fun getByStatus():List<WordStat>
}