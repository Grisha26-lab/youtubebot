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
    private val botToken: String
) : TelegramLongPollingBot() {

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

            val responseText = when (message.text) {
                "/start" -> "Добро пожаловать"
                else -> "Вы написали: ${message.text}"
            }

            sendNotification(chatId, responseText)
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val command = SendMessage().also {
            it.chatId = chatId.toString()
            it.text = responseText
            it.enableMarkdown(true)
        }

        execute(command)
    }
}