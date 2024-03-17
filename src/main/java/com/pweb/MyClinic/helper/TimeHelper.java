package com.pweb.MyClinic.helper;

import java.time.format.DateTimeFormatter;

public class TimeHelper {

    private static final String PATTERN = "yyyy.MM.dd hh:mm:ss";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static final DateTimeFormatter DATE_FORMATTER_0 = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    public static final DateTimeFormatter DATE_FORMATTER_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}