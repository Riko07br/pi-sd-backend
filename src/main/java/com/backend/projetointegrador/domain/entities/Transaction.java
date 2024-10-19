package com.backend.projetointegrador.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private Balance balance;

    public Transaction(Long id, Float valor, String type) {
        this.id = id;
        this.valor = valor;
        this.type = type;
        this.timestamp = Instant.now();
    }

    //region equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    //endregion
}
