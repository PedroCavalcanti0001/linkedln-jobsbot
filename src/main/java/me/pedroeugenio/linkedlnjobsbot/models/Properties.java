package me.pedroeugenio.linkedlnjobsbot.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pedroeugenio.linkedlnjobsbot.enums.MomentFilterEnum;
import me.pedroeugenio.linkedlnjobsbot.utils.TimeUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Properties {
    Integer taskInterval;
    Integer jobsInterval;
    String order;
    String botToken;
    Long botChannel;
    String location;
    MomentFilterEnum moment;
    Long geoId;
    Boolean allowPromoted;
    Integer removeJobFromDbTime;
    Boolean sendJobsNotFoundMessage;
    Boolean excludeWeekends;
    Boolean allowTimeInterval;
    String timeInterval;

    public Boolean isInInterval(LocalDateTime localDateTime) {
        return localDateTime.isAfter(getInterval().getLeft()) && localDateTime.isBefore(getInterval().getRight());
    }

    public Pair<LocalDateTime, LocalDateTime> getInterval() {
        String[] split = timeInterval.split("-");
        LocalDateTime first = TimeUtils.parseTimeFromInterval(split[0]);
        LocalDateTime second = TimeUtils.parseTimeFromInterval(split[1]);
        return Pair.of(first, second);
    }

}
