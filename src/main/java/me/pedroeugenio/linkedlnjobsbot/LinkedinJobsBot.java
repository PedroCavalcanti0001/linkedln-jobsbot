package me.pedroeugenio.linkedlnjobsbot;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.services.LinkedlnBotService;

import java.util.List;

public class LinkedinJobsBot {
    public static final Logger LOGGER = Logger.getLogger(LinkedinJobsBot.class.getName());
    private static final MomentFilterEnum MOMENT = MomentFilterEnum.DAY;
    private static final Integer TIME_TO_FILTER_JOBS = 60;
    private static final LinkedlnBotService LINKEDLN_BOT_SERVICE = new LinkedlnBotService();
    private static final String LOCATION = "brasil";

    public static void main(String[] args) {
        LinkedlnBotService service = new LinkedlnBotService();
        List<Job> jobList = service.getJobList(moment);
        System.out.println("Quantidade de vagas encontradas ".concat(String.valueOf(jobList.size())));
        for (Job job : jobList) {
            System.out.println(job);
            System.out.println();
        }
    }

}
