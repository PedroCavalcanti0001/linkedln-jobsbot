package me.pedroeugenio.linkedlnjobsbot;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.services.LinkedlnBotService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LinkedinJobsBot {
    public static final Logger LOGGER = Logger.getLogger(LinkedinJobsBot.class.getName());
    private static final MomentFilterEnum MOMENT = MomentFilterEnum.DAY;
    private static final Integer TIME_TO_FILTER_JOBS = 60;
    private static final LinkedlnBotService LINKEDLN_BOT_SERVICE = new LinkedlnBotService();
    private static final String LOCATION = "brasil";

    public static void main(String[] args) {
        start();
    }

    static void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Buscando novas vagas...");
                List<Job> jobList = LINKEDLN_BOT_SERVICE.getAllJobList(MOMENT, TIME_TO_FILTER_JOBS, LOCATION);
                LOGGER.info("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
                for (Job job : jobList) {
                    LOGGER.info(job.toString().concat("\n"));
                }
            }
        };
        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(timerTask, 30, TimeUnit.MINUTES.toMillis(1));
    }

}
