package org.yunzhong.CommonTest.util.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
class DateFormatUtilTest {

    @Test
    void testParseLocalDateStringString() {
        LocalDate parseLocalDate = DateFormatUtil.parseLocalDate("20200202", "yyyyMMdd");
        System.out.println(parseLocalDate);
        parseLocalDate = DateFormatUtil.parseLocalDate("20200202132020", "yyyyMMddHHmmss");
        System.out.println(parseLocalDate);
    }

    @Test
    void testParseLocalDateTimeStringString() {
        LocalDateTime parseLocalDate = DateFormatUtil.parseLocalDateTime("20200202132020", "yyyyMMddHHmmss");
        System.out.println(parseLocalDate);
        parseLocalDate = DateFormatUtil.parseLocalDateTime("202002021320", "yyyyMMddHHmm");
        System.out.println(parseLocalDate);
        parseLocalDate = DateFormatUtil.parseLocalDateTime("2020020213", "yyyyMMddHH");
        System.out.println(parseLocalDate);
        // 如果只有日期，则会抛出异常
        // parseLocalDate = DateFormatUtil.parseLocalDateTime("20200202", "yyyyMMdd");
        // System.out.println(parseLocalDate);
    }

    @Test
    void testParseLocalTimeStringString() {
        LocalTime localTime = DateFormatUtil.parseLocalTime("20200202132020", "yyyyMMddHHmmss");
        System.out.println(localTime);
        localTime = DateFormatUtil.parseLocalTime("132020", "HHmmss");
        System.out.println(localTime);
    }

    @Test
    void testFormatLocalDateLocalDateString() {
        String formatLocalDate = DateFormatUtil.formatLocalDate(LocalDate.now(), "yyyy这里是年MM月dd");
        System.out.println(formatLocalDate);
    }

    @Test
    void testFormatLocalDateTimeLocalDateTimeString() {
        String formatLocalDateTime = DateFormatUtil.formatLocalDateTime(LocalDateTime.now(), "yyyy这里是年MM月dd");
        System.out.println(formatLocalDateTime);
        formatLocalDateTime = DateFormatUtil.formatLocalDateTime(LocalDateTime.now(), "yyyy这里是年MM月dd HHmmss");
        System.out.println(formatLocalDateTime);
    }

    @Test
    void testFormatLocalTimeLocalTimeString() {
        System.out.println(DateFormatUtil.formatLocalTime(LocalTime.now(), "HHmmss"));
    }

    @Test
    void testParseLocalDateString() {
        LocalDate parseLocalDate = DateFormatUtil.parseLocalDate("2020-02-02");
        System.out.println(parseLocalDate);
    }

    @Test
    void testParseLocalDateTimeString() {
        LocalDateTime localDateTime = DateFormatUtil.parseLocalDateTime("2020-02-02 13:20:20");
        System.out.println(localDateTime);
    }

    @Test
    void testParseLocalTimeString() {
        LocalTime localTime = DateFormatUtil.parseLocalTime("13:20:20");
        System.out.println(localTime);
    }

    @Test
    void testFormatLocalDateLocalDate() {
        String formatLocalDate = DateFormatUtil.formatLocalDate(LocalDate.now());
        System.out.println(formatLocalDate);
    }

    @Test
    void testFormatLocalDateTimeLocalDateTime() {
        String formatLocalDateTime = DateFormatUtil.formatLocalDateTime(LocalDateTime.now());
        System.out.println(formatLocalDateTime);
    }

    @Test
    void testFormatLocalTimeLocalTime() {
        System.out.println(DateFormatUtil.formatLocalTime(LocalTime.now()));
    }

}
