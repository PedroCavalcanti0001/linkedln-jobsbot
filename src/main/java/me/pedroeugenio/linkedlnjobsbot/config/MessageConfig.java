package me.pedroeugenio.linkedlnjobsbot.config;

import me.pedroeugenio.linkedlnjobsbot.models.NewJobsMessage;

public class MessageConfig extends AbstractConfig<NewJobsMessage> {
    private static final NewJobsMessage JOBS_MESSAGE = new MessageConfig().load();

    @Override
    String getFilename() {
        return "mensagens_bot.yml";
    }

    @Override
    String getTemplateName() {
        return "mensagens_bot_template.yml";
    }

    public static NewJobsMessage getSingleton() {
        return JOBS_MESSAGE;
    }
}
