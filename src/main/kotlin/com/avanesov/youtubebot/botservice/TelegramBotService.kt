package com.avanesov.youtubebot.botservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


@Service
class TelegramBotService(

    @Value("\${app.bot.username}")
    private val botName: String,

    @Value("\${app.bot.token}")
    private val botToken: String,
    private val wordService: WordService

) : TelegramLongPollingBot() {


    override fun getBotToken(): String = botToken


    override fun getBotUsername(): String = botName

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
                else -> "Вы написали: $messageText"
            }

            wordService.saveWord(messageText.split("\\s+".toRegex()))

            sendNotification(chatId, responseText)
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage().also {
            it.chatId = chatId.toString()
            it.text = responseText
        }
        execute(responseMessage)
    }
}