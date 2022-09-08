package me.pedroeugenio.linkedlnjobsbot.services;

import me.pedroeugenio.linkedlnjobsbot.config.AppConfig;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.models.Properties;
import me.pedroeugenio.linkedlnjobsbot.scraper.JobsSearch;
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

public class JobSearchService {
    private static final Logger LOGGER = LogManager.getLogger(JobSearchService.class.getName());
    private static final JobsSearch JOBS_SEARCH = new JobsSearch();
    private static final Properties PROPERTIES = AppConfig.getSingleton();
    private static final TelegramBot TELEGRAM_BOT = new TelegramBot(PROPERTIES);

    public static void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Buscando novas vagas...");
                List<Job> jobList = JOBS_SEARCH.getAllJobList();
                LOGGER.info("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
                if (!jobList.isEmpty())
                    TELEGRAM_BOT.sendJobsMessage(jobList);
                LOGGER.info("Nova busca será realizada as "
                        .concat(TimeUtils.formattedTime(LocalDateTime.now().plus(PROPERTIES.getTaskInterval(), ChronoUnit.MINUTES))));
            }
        };
        Timer timer = new Timer("linkedlnjobsbot-timer");
        timer.scheduleAtFixedRate(timerTask, 0, TimeUnit.MINUTES.toMillis(PROPERTIES.getTaskInterval()));
    }
}
