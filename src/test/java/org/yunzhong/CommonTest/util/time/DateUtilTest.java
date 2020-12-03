package org.yunzhong.CommonTest.util.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
class DateUtilTest {

    @Test
    void testasLocalDate() {
        LocalDate asLocalDate = DateUtil.asLocalDate(new Date());
        assertNotNull(asLocalDate);
        System.out.println(asLocalDate);
    }

    @Test
    void testasLocalDateTime() {
        LocalDateTime asLocalDate = DateUtil.asLocalDateTime(new Date());
        assertNotNull(asLocalDate);
        System.out.println(asLocalDate);
    }

    @Test
    void testAsDate() {
        Date asDate = DateUtil.asDate(LocalDate.now());
        assertNotNull(asDate);
        System.out.println(asDate);
    }

    @Test
    void testAsLocalDateTime() {
        Date asDate = DateUtil.asDate(LocalDateTime.now());
        assertNotNull(asDate);
        System.out.println(asDate);
    }

}
