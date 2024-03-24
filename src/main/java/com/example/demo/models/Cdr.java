package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "cdr")
public class Cdr {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "phone_number")
    Long phoneNumber;
    @Column(name = "type")
    Byte type;
    @Column(name = "start_time")
    OffsetDateTime startTime;
    @Column(name = "end_time")
    OffsetDateTime endTime;
}
