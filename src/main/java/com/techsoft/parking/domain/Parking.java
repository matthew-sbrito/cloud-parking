package com.techsoft.parking.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @Column(nullable = true)
    private LocalDateTime exitDate;

    @Column(nullable = true)
    private Double bill;
}
