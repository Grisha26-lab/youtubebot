package com.avanesov.youtubebot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YoutubebotApplication

fun main(args: Array<String>) {
    runApplication<YoutubebotApplication>(*args)
}
