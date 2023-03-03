package by.itstep.stpnbelko.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private double priceUsd;

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


    protected boolean canEqual(final Object other) {
        return other instanceof Coin;
    }

}
