package com.avanesov.youtubebot.botservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update


@Service
class TelegramBotService : TelegramLongPollingBot() {

    @Value("\${bot.username}")
    private val botName = ""

    @Value("\${bot.token}")
    private val botToken = ""


    override fun getBotToken(): String = botToken


    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            if (update.hasMessage())
            val  message:Message = update.message
            val chatId = message.chatId
            val responseText = if (message.hasText()) {
                when (val messageText = message.text) {
                    "/start" -> "Добро пожаловать!"
                    else -> "Вы написали :$messageText"
                }
            }else{
                "Я понимаю только текст"
            }
            sendNotification(chatId,responseText)
        }
    }
    private fun sendNotification(chatId:Long,responseText:String){
        val responseMessage = SendMessage(chatId.toString(),responseText)
        responseMessage.enableMarkdownV2(true)
        execute(responseMessage)
    }
}