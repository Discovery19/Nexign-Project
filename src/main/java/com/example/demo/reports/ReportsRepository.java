package com.example.demo.reports;

import com.example.demo.models.Udr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportsRepository extends JpaRepository<Udr, Long> {
    Udr findByPhoneNumberAndMonth(Long phoneNumber, String month);

    List<Udr> findAllByPhoneNumber(Long phoneNumber);
}
