package com.avanesov.youtubebot.botservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


@Service
class TelegramBotService(
    @Value("\${bot.username}")
    private val botName: String,

    @Value("\${bot.token}")
    private val botToken: String,

    private val wordService: WordService
) : TelegramLongPollingBot() {

//    https://www.youtube.com/results?search_query=one+two
//    https://www.youtube.com/results?search_query=google-one
    // one 2
    // two 1
    // google 1

    /**
     * music
     * car
     * music
     * car
     * music
     *
     * 5 -> music: 3, car: 2
     *
     * music: 3
     * car: 2
     */

    override fun getBotToken() = botToken

    override fun getBotUsername() = botName

    override fun onUpdateReceived(update: Update?) {
        if (update == null) {
            return
        }

        if (update.hasMessage()) {
            val message = update.message

            val chatId = message.chatId

            if (!message.hasText()) {
                sendNotification(chatId, "Я понимаю только текст")
            }

            val messageText = message.text

            val responseText = when (messageText) {
                "/start" -> "Добро пожаловать"
                "/stats" -> wordService.getStats()
                else -> "Вы написали: ${messageText}"
            }

            wordService.saveWords(messageText.split("\\s+".toRegex()))

            sendNotification(chatId, responseText)
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val command = SendMessage().also {
            it.chatId = chatId.toString()
            it.text = responseText
//            it.enableMarkdown(true)
        }

        execute(command)
    }
}