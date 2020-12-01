package org.yunzhong.CommonTest.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
public class LocalDateTest {

    /**
     * 两个日期之间相差多少天
     */
    @Test
    public void testBetweenDate() {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDate localDate = nowTime.toLocalDate();
        long between1 = ChronoUnit.DAYS.between(nowDate, localDate);
        System.out.println(between1);
    }

}
