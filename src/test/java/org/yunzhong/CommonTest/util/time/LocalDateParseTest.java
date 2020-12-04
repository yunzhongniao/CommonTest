package org.yunzhong.CommonTest.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
public class LocalDateParseTest {

    @Test
    public void testDateFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当前日期
        LocalDate now = LocalDate.now();
        String todayStr = dateTimeFormatter.format(now);
        System.out.println(todayStr);
        // 获取昨天的日期
        LocalDate yesterday = now.plusDays(-1);
        String yesStr = dateTimeFormatter.format(yesterday);
        System.out.println(yesStr);
        // 根据年月日获取日期
        LocalDate setDate = LocalDate.of(2020, 1, 1);
        String setStr = dateTimeFormatter.format(setDate);
        System.out.println(setStr);
        // 字符串转日期
        LocalDate parseDate = LocalDate.parse("2020-10-01");
        String parseStr = dateTimeFormatter.format(parseDate);
        System.out.println(parseStr);
    }

    @Test
    public void testDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前日期
        LocalDateTime now = LocalDateTime.now();
        String todayStr = dateTimeFormatter.format(now);
        System.out.println(todayStr);
        // 获取昨天的日期
        LocalDateTime yesterday = now.plusDays(-1);
        String yesStr = dateTimeFormatter.format(yesterday);
        System.out.println(yesStr);
        // 获取时间
        LocalDateTime setDate = LocalDateTime.of(2020, 1, 1, 12, 10);
        String setStr = dateTimeFormatter.format(setDate);
        System.out.println(setStr);
        // 字符串转时间，注意中间的那个"T"
        LocalDateTime parseDate = LocalDateTime.parse("2020-10-01T13:00:00");
        String parseStr = dateTimeFormatter.format(parseDate);
        System.out.println(parseStr);
    }
}
