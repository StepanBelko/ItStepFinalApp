package by.itstep.stpnbelko.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coins")
public class Coin {


    @Id
    @Column(nullable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nameid;

    @Column(name = "coin_rank")
    private int rank;


    @Column(name = "price_usd")
    private double price_usd;

    @Column(name ="price")
    @JsonIgnore
    private double price;

    @Column
    private double percent_change_24h;

    @Column
    private double percent_change_1h;

    @Column
    private double percent_change_7d;

    @Column
    private double market_cap_usd;

    @Column
    private double volume24;

    @Column
    private double volume24a;

    @Column
    private double volume_native;

    @Column
    private double csupply;

    @Column
    private double price_btc;

    @Column
    private double tsupply;

    @Column
    private double msupply;

    @OneToMany()
    @JoinColumn(name = "coin_id")
    private List<History> coinHistory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coin coin = (Coin) o;
        boolean difference = Math.abs(coin.price_usd - this.price_usd) < this.price_usd * 0.01;
        return id == coin.id && rank == coin.rank && difference && Double.compare(coin.price_btc, price_btc) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rank, price_usd, price_btc);
    }
}
