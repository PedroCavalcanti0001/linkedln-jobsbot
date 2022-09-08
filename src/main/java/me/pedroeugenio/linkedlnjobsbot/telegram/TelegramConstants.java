package me.pedroeugenio.linkedlnjobsbot.telegram;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TelegramConstants {
    protected static final Logger LOGGER = LogManager.getLogger(TelegramBot.class.getName());
    protected static final String TOKEN = AppConfig.load().getBotToken();
    protected static final com.pengrad.telegrambot.TelegramBot BOT = new com.pengrad.telegrambot.TelegramBot(TOKEN);
    protected static final Long CHAT = AppConfig.load().getBotChannel();
    protected static final String TEMPLATE_MESSAGE = "\uD83D\uDCBB Vaga: ".concat("{vaga}")
            .concat("\n").concat("\uD83C\uDFE6 Empresa: ").concat("{empresa}").concat("\n").concat("⏱ Postada ")
            .concat("{strTime}").concat("\n").concat("📌 Localização: ").concat("{loc}").concat("\n")
            .concat("\uD83D\uDC49 Link: ").concat("{link}").concat("\n\n");
}
