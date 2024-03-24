package com.example.demo.generators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

@Slf4j
@Component
public class CdrFileGenerator {
    private final String[] subscribers = {
            "79876543221", "79996667755"
            , "79998887766", "79995556677"
            , "79994445588", "79000445588"
            , "89994445588", "89000445588"
            , "99994445588", "99000445588"};
    private static final int YEAR = 2024;
    private static final String PATH = "src/cdr";
    private static final int NUM_MONTH = 4;//длительность по месяцам
    private static final int NUM_CALLS = 10;//максимальное кол-во вызовов для абонента
    private static final Random random = new Random();

    public void generateCDRFile() {
        try {
            for (int month = 1; month <= NUM_MONTH; month++) {
                String cdrFileName = PATH + "/cdr_" + YEAR + "_" + String.format("%02d", month) + ".txt";
                generateCDRFile(cdrFileName, subscribers, YEAR, month);
                log.info("CDR файл для месяца " + month + " сгенерирован.");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private void generateCDRFile(String fileName, String[] subscribers, int year, int month) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < NUM_CALLS; i++) {
                long startTimestamp = getRandomTimestampInMonth(year, month);
                log.info("Start of call: " + startTimestamp);
                long endTimestamp = startTimestamp + (random.nextInt(3600) + 1) * 1000; // случайная длительность звонка до 1 часа
                log.info("End of call: " + endTimestamp);
                String subscriber = subscribers[random.nextInt(0, subscribers.length)];
                String cdrRecord = getRandomCallType() + "," + subscriber + "," + startTimestamp + "," + endTimestamp + "\n";
                writer.write(cdrRecord);
            }
        }
    }

    private long getRandomTimestampInMonth(int year, int month) {
        int maxDayOfMonth;
        if (month == 2) {
            maxDayOfMonth = (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDayOfMonth = 30;
        } else {
            maxDayOfMonth = 31;
        }
        int day = random.nextInt(1, maxDayOfMonth);
        log.info("random day: " + day);
        return getRandomTimestampInDay(year, month, day);
    }

    private long getRandomTimestampInDay(int year, int month, int day) {
        long startOfDay = getUnixTimestamp(year, month, day, 0, 0, 0);
        long endOfDay = getUnixTimestamp(year, month, day, 23, 59, 59);
        return startOfDay + (long) (random.nextDouble() * (endOfDay - startOfDay));
    }

    private long getUnixTimestamp(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000L;
    }

    private String getRandomCallType() {
        return (random.nextInt(2) == 0) ? "01" : "02";
    }
}
