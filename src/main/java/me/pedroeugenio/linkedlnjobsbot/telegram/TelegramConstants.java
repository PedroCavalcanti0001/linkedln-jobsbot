package me.pedroeugenio.linkedlnjobsbot.telegram;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TelegramConstants {
    protected static final Logger LOGGER = LogManager.getLogger(TelegramBot.class.getName());
    protected static final String TOKEN = System.getenv("LBOT_TELEGRAM_TOKEN");
    protected static final com.pengrad.telegrambot.TelegramBot BOT = new com.pengrad.telegrambot.TelegramBot(TOKEN);
    protected static final Long CHAT = -603392344L;
    protected static final String TEMPLATE_MESSAGE = "Vaga: ".concat("{vaga}").concat("\n").concat("Empresa: ").concat("{empresa}")
            .concat("\n").concat("Postada ").concat("{strTime}").concat("\n").concat("Localização: ").concat("{loc}")
            .concat("\n").concat("Link: ").concat("{link}").concat("\n\n");
}
