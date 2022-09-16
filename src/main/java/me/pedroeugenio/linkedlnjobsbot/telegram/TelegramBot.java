package me.pedroeugenio.linkedlnjobsbot.telegram;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;

public class TelegramBot {
    private final Properties properties;
    private final StringBuilder sb;

    public TelegramBot(Properties properties) {
        this.properties = properties;
        this.sb = new StringBuilder();
    }

    private void insertJobsTextInSb(List<Job> jobs) {
        for (Job job : jobs) {
            String template = TelegramConstants.MESSAGES.jobTextAsString().replace("{empresa}", job.getCompany())
                    .replace("{vaga}", job.getTitle())
                    .replace("{strTime}", job.getStrTime().toLowerCase())
                    .replace("{loc}", job.getLocation())
                    .replace("{link}",
                            job.getJobId() != 0
                                    ? "https://br.linkedin.com/jobs/view/".concat(String.valueOf(job.getJobId()))
                                    : job.getShortLink());
            sb.append(template);
        }
    }

    public void sendJobsMessage(List<Job> jobs) {
        TelegramConstants.LOGGER.info("Enviando vagas para o telegram");
        insertStart(jobs);
        insertJobsTextInSb(jobs);
        insertEnd();
        try {
            SendResponse response = TelegramConstants.BOT.execute(new SendMessage(TelegramConstants.CHAT, sb.toString()));
            if (response.isOk())
                TelegramConstants.LOGGER.info("Vagas enviadas com sucesso");
            else
                TelegramConstants.LOGGER.error("Houve um erro ao enviar -> ".concat(String.valueOf(response.errorCode())));
        }catch (Exception ex){
            TelegramConstants.LOGGER.error("Houve uma excessão ao enviar mensagem para o telegram ", ex);
        }finally {
            sb.setLength(0);
        }
    }

    private void insertEnd() {
        this.sb.append(TelegramConstants.MESSAGES.endAsString()
                .replace("{tempoNovaBusca}",
                        TimeUtils.nextSearchStrTime(LocalDateTime.now())));
    }

    private void insertStart(List<Job> jobs) {
        this.sb.append(TelegramConstants.MESSAGES.startAsString().replace("{quantidadeVagas}", String.valueOf(jobs.size())));
    }

    public void sendJobsNotFoundMessage() {
        try {
            SendResponse response = TelegramConstants.BOT.execute(new SendMessage(TelegramConstants.CHAT,
                    TelegramConstants.MESSAGES.jobsNotFoundAsString()
                            .replace("{tempoNovaBusca}",
                                    TimeUtils.nextSearchStrTime(LocalDateTime.now()))));
            if (response.isOk())
                TelegramConstants.LOGGER.info("Mensagem de vagas não encontradas, enviada.");
            else
                TelegramConstants.LOGGER.error("Houve um erro ao enviar a mensagem de vagas não encontradas -> "
                        .concat(String.valueOf(response.errorCode())));
        } catch (Exception ex) {
            TelegramConstants.LOGGER.error("Houve uma excessão ao enviar mensagem de vagas não encontradas para o telegram ",
                    ex);
        } finally {
            sb.setLength(0);
        }
    }
}