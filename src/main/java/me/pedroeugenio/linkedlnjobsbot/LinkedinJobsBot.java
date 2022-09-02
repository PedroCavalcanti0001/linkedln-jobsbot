package me.pedroeugenio.linkedlnjobsbot;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.enums.SortEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.services.LinkedlnBotService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LinkedinJobsBot {
    private static final Logger LOGGER = LogManager.getLogger(LinkedinJobsBot.class.getName());
    private static final MomentFilterEnum MOMENT = MomentFilterEnum.DAY;
    private static final Integer TIME_TO_FILTER_JOBS = 200;
    private static final LinkedlnBotService LINKEDLN_BOT_SERVICE = new LinkedlnBotService();
    private static final String LOCATION = "Brasil";
    private static final SortEnum SORT = SortEnum.TIME;

    public static void main(String[] args) {
        start();
    }

    static void start() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Buscando novas vagas...");
                List<Job> jobList = LINKEDLN_BOT_SERVICE.getAllJobList(MOMENT, TIME_TO_FILTER_JOBS, LOCATION, SORT);
                LOGGER.info("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
                for (Job job : jobList) {
                    LOGGER.info("Vaga: "
                            .concat(job.getTitle())
                            .concat(" | Empresa: ")
                            .concat(job.getCompany())
                            .concat(" | Localização: ")
                            .concat(job.getLocation())
                            .concat(" | Tempo: ")
                            .concat(String.valueOf(job.getTime().toMinutes()))
                            .concat(" minutos atrás")
                    );
                }
                LOGGER.info("");
            }
        };
        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(timerTask, 30, TimeUnit.MINUTES.toMillis(5));
    }

}
