package org.yunzhong.CommonTest.util.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
public class LocalBetweenTest {

    /**
     * 两个日期之间相差多少天
     */
    @Test
    public void testBetweenDate() {
        LocalDate nowDate = LocalDate.parse("2019-12-02");
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDate localDate = nowTime.toLocalDate();
        long between1 = ChronoUnit.DAYS.between(nowDate, localDate);
        System.out.println(between1);
    }

    @Test
    public void testBetweenSecond() {
        LocalDateTime startDate = LocalDateTime.parse("2019-12-02T11:11:11.11");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-02T12:11:11.11");
        long seconds = Duration.between(startDate, endDate).get(ChronoUnit.SECONDS);
        System.out.println("between seconds:" + seconds);
        long between = ChronoUnit.SECONDS.between(startDate, endDate);
        System.out.println("between seconds:" + between);
    }

    @Test
    public void testBetweenNanoSecond() {
        LocalDateTime startDate = LocalDateTime.parse("2019-12-02T11:11:11.01");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-02T12:11:11.11");
        // 忽略日期
        long seconds = Duration.between(startDate, endDate).get(ChronoUnit.NANOS);
        System.out.println("between NANOS:" + seconds);
        // 包含日期
        long between = ChronoUnit.NANOS.between(startDate, endDate);
        System.out.println("between NANOS:" + between);
        long until = startDate.until(endDate, ChronoUnit.NANOS);
        System.out.println("between NANOS until:" + until);
    }

    @Test
    public void testDuration() {
        Duration duration = Duration.between(LocalTime.NOON, LocalTime.MAX);
        LocalDateTime date = LocalDateTime.now();
        System.out.println(date);
        date = (LocalDateTime) duration.addTo(date);
        System.out.println(date);
    }

    @Test
    public void testMinusDay() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime minusDays = date.minusDays(300);
        System.out.println(minusDays);
    }

    @Test
    public void testMinusYear() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime minusDays = date.minusYears(1);
        System.out.println(minusDays);
    }

    @Test
    public void testMinusMonth() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime minusDays = date.minusMonths(2);
        System.out.println(minusDays);
    }
}
