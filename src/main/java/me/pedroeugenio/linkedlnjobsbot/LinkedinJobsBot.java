package me.pedroeugenio.linkedlnjobsbot;

import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.models.Job;
import me.pedroeugenio.linkedlnjobsbot.services.LinkedlnBotService;

import java.util.List;

public class LinkedinJobsBot {
    private static final MomentFilterEnum moment = MomentFilterEnum.ANY;
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
