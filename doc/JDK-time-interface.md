
## JDK1.8 之前支持的时间操作：
- System.currentTimeMillis() ：表示自1970年1月1日起的当前日期和时间（以毫秒为单位）。
- java.util.Date ：表示特定时间点，以毫秒为单位。
- java.util.Calendar ：一个抽象类，提供用于在实例之间进行转换并以不同的方式处理日历字段的方法。
- java.text.SimpleDateFormat ：来格式化和解析日期。
- java.util.TimeZone ：代表时区偏移量，还可以计算出夏令时。

## 问题

- 一个Date类应代表一个日期，但它代表一个具有小时，分钟和秒的实例。
- Date没有任何关联的时区。它会自动选择默认时区。
- 类是可变的。
- 日期格式化类也不是线程安全的。
- 和java.sql.Date重名
- 使用其他时区创建日期非常棘手，通常会导致错误的结果。
- 月使用从零开始的索引，让人费解

## jdk8的新api
- java.time.LocalDate：表示ISO日历中的年月日，对于表示没有时间的日期很有用。它可用于表示仅日期的信息，例如出生日期或结婚日期。
- java.time.LocalTime：仅按时交易。它对于表示基于人的时间（例如电影时间或本地图书馆的开放和关闭时间）很有用。
- java.time.LocalDateTime：处理日期和时间，没有时区。它是LocalDate和LocalTime的组合。
- java.time.ZonedDateTime：将LocalDateTime类与ZoneId类中提供的区域信息结合在一起。它代表完整的日期时间戳以及时区信息。
- java.time.OffsetTime ：处理时间与格林威治/ UTC有相应时区偏移的时间，而没有时区ID。
- java.time.OffsetDateTime ：处理日期和时间，该日期和时间具有与格林威治/ UTC相对应的时区偏移，但没有时区ID。
- java.time.Clock：提供对任何给定时区中的当前时刻，日期和时间的访问。尽管使用Clock类是可选的，但是此功能使我们可以测试其他时区的代码，也可以使用时间不变的固定时钟来测试您的代码。
- java.time.Instant：表示时间轴上的纳秒的开始（自EPOCH开始），对于生成表示机器时间的时间戳很有用
- java.time.Duration ：两个瞬间之间的差异，以秒或纳秒为单位，并且不使用基于日期的构造，例如年，月和日，尽管该类提供了转换为天，小时和分钟的方法。
- java.time.Period ：以基于日期的值（年，月，日）定义日期之间的差异。
- java.time.ZoneId：指定时区标识符，并提供在Instant和LocalDateTime之间进行转换的规则。
- java.time.ZoneOffset ：指定相对格林威治/ UTC时间的时区偏移量。
- java.time.format.DateTimeFormatter：提供了许多预定义的格式化程序，或者我们可以定义自己的格式化程序。它提供parse（）或format（）方法来解析和格式化日期时间值。

## Local时间与Date转换
```
/**
 * @author yunzhong
 *
 */
public class DateConvertUtil {

    /**
     * 忽略时间，只有日期
     * 
     * @param localDate
     * @return
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}

```

## Local时间格式化

```

/**
 * @author yunzhong
 *
 */
public class DateFormatUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");
    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
```

## Local时间操作

```

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

```

## Local时间差计算

```

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

    /**
     * 日期之间相差多少秒
     */
    @Test
    public void testBetweenSecond() {
        LocalDateTime startDate = LocalDateTime.parse("2019-12-02T11:11:11.11");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-02T12:11:11.11");
        long seconds = Duration.between(startDate, endDate).get(ChronoUnit.SECONDS);
        System.out.println("between seconds:" + seconds);
        long between = ChronoUnit.SECONDS.between(startDate, endDate);
        System.out.println("between seconds:" + between);
    }

    /**
     * 日期之间相差多少纳秒
     */
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

    /**
     * 增加多少天
     */
    @Test
    public void testMinusDay() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime minusDays = date.minusDays(300);
        System.out.println(minusDays);
    }

    /**
     * 减少多少天
     */
    @Test
    public void testPlusDay() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime plusDays = date.plusDays(10);
        System.out.println(plusDays);
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

```
