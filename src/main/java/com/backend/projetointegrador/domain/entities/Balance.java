package com.backend.projetointegrador.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_balance")
public class Balance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter(AccessLevel.NONE)
    private Float amount;

    @OneToOne
    private Account account;
    @OneToMany(mappedBy = "balance")
    @Setter(AccessLevel.NONE)
    private Set<Transaction> transactions = new HashSet<>();

    public Balance(Float amount, Account account) {
        this.amount = amount;
        this.account = account;
    }

    //region equals/hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;
        return Objects.equals(id, balance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    //endregion

    public void addBalance(Float value) {
        this.amount += value;
    }

    public void subtractBalance(Float value) {
        if (this.amount < value) {
            throw new RuntimeException("Insufficient balance");
        }
        this.amount -= value;
    }
}
