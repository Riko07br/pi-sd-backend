package com.backend.projetointegrador.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float valor;
    private String type;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private Instant timestamp;

    @ManyToOne
    private Balance balance;
}