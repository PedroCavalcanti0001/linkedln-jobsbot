package me.pedroeugenio.linkedlnjobsbot.services;

import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.scraper.JobsSearch;
import me.pedroeugenio.linkedlnjobsbot.telegram.TelegramBot;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class JobSearchService {
    private static final Logger LOGGER = LogManager.getLogger(JobSearchService.class.getName());
    private static final TelegramBot telegramBot = new TelegramBot();
    private static final JobsSearch jobsSearch = new JobsSearch();

    public static void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Buscando novas vagas...");
                List<Job> jobList = jobsSearch.getAllJobList();
                LOGGER.info("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
                if (!jobList.isEmpty())
                    telegramBot.sendJobsMessage(jobList);
            }
        };
        Timer timer = new Timer("linkedlnjobsbot-timer");
        timer.scheduleAtFixedRate(timerTask, 0, TimeUnit.MINUTES.toMillis(5));
    }
}
