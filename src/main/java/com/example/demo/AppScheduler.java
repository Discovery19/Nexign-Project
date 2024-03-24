package com.example.demo;

import com.example.demo.commutator.Commutator;
import com.example.demo.generators.CdrFileGenerator;
import com.example.demo.generators.UdrCmdJsonGenerator;
import com.example.demo.reports.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppScheduler {
    private final Commutator commutator;
    private final CdrFileGenerator generator;
    private final ReportsService reportsService;
    private final UdrCmdJsonGenerator reports;
    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        generator.generateCDRFile();
        log.info("generating CDR file");
        commutator.processAllCDRFiles();
        log.info("commutator works");
        reportsService.generateUdr();
        log.info("generator works");
        reports.generateReport();
        log.info("generate report in cmd");

    }
}
