package com.example.demo.commutator;

import com.example.demo.models.Cdr;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
@Slf4j
public class Commutator {
    private final CommutatorRepository commutatorRepository;
    private static final String FOLDER_PATH = "src/cdr";
    public void processAllCDRFiles() {
        try {
            Files.list(Paths.get(FOLDER_PATH))
                    .filter(Files::isRegularFile)
                    .forEach(this::processCDRFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    //for testing...
    public void processAllCDRFiles(String folderPath) {
        try {
            log.info(folderPath);
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .forEach(this::processCDRFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processCDRFile(Path filePath) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Long phoneNumber = Long.valueOf(parts[1]);
                    byte type = Byte.parseByte(parts[0]);
                    long startTimeUnix = Long.parseLong(parts[2]);
                    long endTimeUnix = Long.parseLong(parts[3]);

                    OffsetDateTime startTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(startTimeUnix), ZoneOffset.UTC);
                    OffsetDateTime endTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(endTimeUnix), ZoneOffset.UTC);

                    Cdr cdr = new Cdr();
                    cdr.setPhoneNumber(phoneNumber);
                    cdr.setType(type);
                    cdr.setStartTime(startTime);
                    cdr.setEndTime(endTime);
                    commutatorRepository.save(cdr);
                } else {
                    log.error("Некорректный формат строки в файле " + filePath + ": " + line);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
