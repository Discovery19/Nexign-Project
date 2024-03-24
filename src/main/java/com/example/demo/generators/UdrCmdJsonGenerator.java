package com.example.demo.generators;

import com.example.demo.models.Udr;
import com.example.demo.models.UdrJson;
import com.example.demo.reports.ReportsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UdrCmdJsonGenerator {
    private final ObjectMapper objectMapper;
    private final ReportsRepository repository;

    public void generateReport() {
        Map<Long, Duration> incomingCalls = new HashMap<>();
        Map<Long, Duration> outgoingCalls = new HashMap<>();
        List<Udr> reports = repository.findAll();
        for (Udr udr : reports) {
            incomingCalls.merge(udr.getPhoneNumber(), Duration.ofSeconds(udr.getIncomeCalls()), Duration::plus);
            outgoingCalls.merge(udr.getPhoneNumber(), Duration.ofSeconds(udr.getOutgoingCalls()), Duration::plus);
        }
        System.out.println("Phone Number\t\tIncome\t\tOutgoing");
        incomingCalls.forEach((phone, duration)
                -> System.out.println(phone + "\t\t" + parseTime(duration) + "\t\t" + parseTime(outgoingCalls.get(phone))));
    }

    public void generateReport(Long phoneNumber) {
        Map<String, Duration> incomingCalls = new HashMap<>();
        Map<String, Duration> outgoingCalls = new HashMap<>();
        List<Udr> reports = repository.findAllByPhoneNumber(phoneNumber);
        for (Udr udr : reports) {
            incomingCalls.merge(udr.getMonth(), Duration.ofSeconds(udr.getIncomeCalls()), Duration::plus);
            outgoingCalls.merge(udr.getMonth(), Duration.ofSeconds(udr.getOutgoingCalls()), Duration::plus);
        }
        System.out.println("Phone Number\t\t" + phoneNumber);
        System.out.println("Month\t\tIncome\t\tOutgoing");
        incomingCalls.forEach((month, duration)
                -> System.out.println(month + "\t\t" + parseTime(duration) + "\t\t" + parseTime(outgoingCalls.get(month))));
    }

    public void generateReport(Long phoneNumber, String month) {
        Udr report = repository.findByPhoneNumberAndMonth(phoneNumber, month);

        System.out.println("Phone Number\t\tMonth\t\tIncome\t\tOutgoing");
        System.out.println(
                report.getPhoneNumber()
                        + "\t\t" + report.getMonth()
                        + "\t\t" + Duration.ofSeconds(report.getIncomeCalls())
                        + "\t\t" + Duration.ofSeconds(report.getOutgoingCalls()));

    }

    private String parseTime(Duration duration) {
        if (duration == null) {
            return "00:00:00";
        }
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    public void generateJsonReport(Map<Long, Duration> income, Map<Long, Duration> outgoing, String month) {
        income.forEach((phoneNumber, inDuration) -> {
            try {
                String inTime = parseTime(inDuration);
                String outTime = parseTime(outgoing.get(phoneNumber));
                String fileName = phoneNumber + "_" + month + ".json";
                FileWriter fileWriter = new FileWriter("src/reports/" + fileName);
                UdrJson json = new UdrJson(phoneNumber, inTime, outTime);
                objectMapper.writeValue(fileWriter, json);
                fileWriter.close();
            } catch (IOException e) {
                log.error("Error saving report: " + e.getMessage());
            }
        });
    }
}
