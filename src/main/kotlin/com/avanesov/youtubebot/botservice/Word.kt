package com.avanesov.youtubebot.botservice

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "words")
class Word {

    @Id
    var id: UUID = UUID.randomUUID()

    @Column
    var name: String = ""
}