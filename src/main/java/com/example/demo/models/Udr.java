package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Udr {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "phone_number")
    Long phoneNumber;
    @Column(name = "month")
    String month;
    @Column(name = "income_calls")
    Long incomeCalls;
    @Column(name = "outgoing_calls")
    Long outgoingCalls;
}
