package me.pedroeugenio.linkedlnjobsbot.services;

import com.google.common.collect.Lists;
import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.exceptions.JobAlreadySubmittedException;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import me.pedroeugenio.linkedlnjobsbot.scraper.JobsSearch;
import me.pedroeugenio.linkedlnjobsbot.tasks.JobControlTask;
import me.pedroeugenio.linkedlnjobsbot.telegram.TelegramBot;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JobSearchService {
    private static final Logger LOGGER = LogManager.getLogger(JobSearchService.class.getName());
    private static final JobsSearch JOBS_SEARCH = new JobsSearch();
    private static final Properties PROPERTIES = AppConfig.getSingleton();
    private static final TelegramBot TELEGRAM_BOT = new TelegramBot(PROPERTIES);
    private static final JobControlTask JOB_CONTROL_TASK = new JobControlTask();

    public static void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Buscando novas vagas...");
                Boolean allowTimeInterval = PROPERTIES.getAllowTimeInterval();
                LocalDateTime now = LocalDateTime.now();
                if(allowTimeInterval){
                    LOGGER.info("Verificando se está no intervalo de tempo");
                    if(!PROPERTIES.isInInterval(now)) {
                        LOGGER.info("Não está dentro do intervalo de tempo");
                        return;
                    }
                }
                if(PROPERTIES.getExcludeWeekends() && TimeUtils.isWeekend(now)){
                    LOGGER.info("execução não realizada, pois a configuração de exclusão de final de semana está ativa");
                    return;
                }
                List<Job> jobList = checkJobs(JOBS_SEARCH.getAllJobList());
                LOGGER.info("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
                if (!jobList.isEmpty())
                    TELEGRAM_BOT.sendJobsMessage(jobList);
                else if(PROPERTIES.getSendJobsNotFoundMessage())
                    TELEGRAM_BOT.sendJobsNotFoundMessage();
                LOGGER.info("Nova busca será realizada as "
                        .concat(TimeUtils.formattedTime(LocalDateTime.now().plus(PROPERTIES.getTaskInterval(), ChronoUnit.MINUTES))));
            }

            private List<Job> checkJobs(List<Job> jobList) {
                return Lists.newArrayList(jobList).stream().filter(e -> {
                    try {
                        JOB_CONTROL_TASK.verify(e);
                        return true;
                    } catch (JobAlreadySubmittedException ex) {
                        return false;
                    }
                }).collect(Collectors.toList());
            }
        };
        Timer timer = new Timer("linkedlnjobsbot-timer");
        timer.scheduleAtFixedRate(timerTask, 0, TimeUnit.MINUTES.toMillis(PROPERTIES.getTaskInterval()));
    }
}
