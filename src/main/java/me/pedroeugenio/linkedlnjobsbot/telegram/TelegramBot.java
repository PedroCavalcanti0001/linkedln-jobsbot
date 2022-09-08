package me.pedroeugenio.linkedlnjobsbot.telegram;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TelegramBot {

    private StringBuilder getStringBuilder(List<Job> jobs){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDD0E Vagas encontradas ({quantidade}):".replace("{quantidade}", String.valueOf(jobs.size())));
        stringBuilder.append("\n\n");
        return stringBuilder;
    }

    private StringBuilder addJobsToStringBuilder(List<Job> jobs){
        StringBuilder stringBuilder = getStringBuilder(jobs);
        for (Job job : jobs) {
            String template = TelegramConstants.TEMPLATE_MESSAGE.replace("{empresa}", job.getCompany())
                    .replace("{vaga}", job.getTitle())
                    .replace("{strTime}", job.getStrTime())
                    .replace("{loc}", job.getLocation())
                    .replace("{link}", job.getShortLink());
            stringBuilder.append(template);
        }
        stringBuilder.append("\uD83D\uDCE4 Uma nova busca será realizada as "
                .concat(TimeUtils.formattedTime(LocalDateTime.now().plus(AppConfig.load().getInterval(), ChronoUnit.MINUTES))));
        return stringBuilder;
    }

    public void sendJobsMessage(List<Job> jobs) {
        TelegramConstants.LOGGER.info("Enviando vagas para o telegram");
        StringBuilder jobsAsStr = addJobsToStringBuilder(jobs);
        SendResponse response = TelegramConstants.BOT.execute(new SendMessage(TelegramConstants.CHAT, jobsAsStr.toString()));
        if(response.isOk())
            TelegramConstants.LOGGER.info("Vagas enviadas com sucesso");
        else
            TelegramConstants.LOGGER.error("Houve um erro ao enviar -> ".concat(String.valueOf(response.errorCode())));
    }
}