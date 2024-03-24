package com.example.demo.reports;

import com.example.demo.generators.UdrCmdJsonGenerator;
import com.example.demo.models.Udr;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {
    private static final String FOLDER_PATH = "src/cdr";
    private final ReportsRepository repository;
    private final UdrCmdJsonGenerator generator;

    public void generateUdr() {
        try {
            var files = Files.list(Paths.get(FOLDER_PATH)).filter(Files::isRegularFile).toList();
            for (Path file : files) {
                parseCdrFile(file);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void parseCdrFile(Path filePath) {
        Map<Long, Duration> incomingCalls = new HashMap<>();
        Map<Long, Duration> outgoingCalls = new HashMap<>();
        String month = Paths.get(filePath.toString()).toString().split("_")[2].substring(0, 2);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {

                    byte type = Byte.parseByte(parts[0]);
                    long phoneNumber = Long.parseLong(parts[1]);
                    long startTimeUnix = Long.parseLong(parts[2]);
                    long endTimeUnix = Long.parseLong(parts[3]);

                    OffsetDateTime startTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(startTimeUnix), ZoneOffset.UTC);
                    OffsetDateTime endTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(endTimeUnix), ZoneOffset.UTC);
                    Duration duration = Duration.between(startTime, endTime);

                    if (type == 1) {
                        incomingCalls.merge(phoneNumber, duration, Duration::plus);
                    } else if (type == 2) {
                        outgoingCalls.merge(phoneNumber, duration, Duration::plus);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        repoWorks(incomingCalls, outgoingCalls, month);
        generator.generateJsonReport(incomingCalls, outgoingCalls, month);
    }

    private void repoWorks(Map<Long, Duration> income, Map<Long, Duration> outgoing, String month) {
        income.forEach((phoneNumber, inDuration) -> {
            var inTime = inDuration != null ? inDuration.getSeconds() : 0;
            var outTime = outgoing.get(phoneNumber) != null ? outgoing.get(phoneNumber).getSeconds() : 0;
            Udr udr = new Udr();
            udr.setPhoneNumber(phoneNumber);
            udr.setMonth(month);
            udr.setIncomeCalls(inTime);
            udr.setOutgoingCalls(outTime);
            repository.save(udr);
        });
    }
}