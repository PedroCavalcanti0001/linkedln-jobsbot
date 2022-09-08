package me.pedroeugenio.linkedlnjobsbot.telegram;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.config.MessageConfig;
import me.pedroeugenio.linkedlnjobsbot.models.NewJobsMessage;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TelegramConstants {
    protected static final Properties PROPERTIES = AppConfig.getSingleton();
    protected static final NewJobsMessage MESSAGES = MessageConfig.getSingleton();
    protected static final Logger LOGGER = LogManager.getLogger(TelegramBot.class.getName());
    protected static final String TOKEN = PROPERTIES.getBotToken();
    protected static final com.pengrad.telegrambot.TelegramBot BOT = new com.pengrad.telegrambot.TelegramBot(TOKEN);
    protected static final Long CHAT = PROPERTIES.getBotChannel();
}
