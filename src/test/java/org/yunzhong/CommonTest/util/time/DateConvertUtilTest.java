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
class DateConvertUtilTest {

    @Test
    void testasLocalDate() {
        LocalDate asLocalDate = DateConvertUtil.asLocalDate(new Date());
        assertNotNull(asLocalDate);
        System.out.println(asLocalDate);
    }

    @Test
    void testasLocalDateTime() {
        LocalDateTime asLocalDate = DateConvertUtil.asLocalDateTime(new Date());
        assertNotNull(asLocalDate);
        System.out.println(asLocalDate);
    }

    @Test
    void testAsDate() {
        Date asDate = DateConvertUtil.asDate(LocalDate.now());
        assertNotNull(asDate);
        System.out.println(asDate);
    }

    @Test
    void testAsLocalDateTime() {
        Date asDate = DateConvertUtil.asDate(LocalDateTime.now());
        assertNotNull(asDate);
        System.out.println(asDate);
    }

}
