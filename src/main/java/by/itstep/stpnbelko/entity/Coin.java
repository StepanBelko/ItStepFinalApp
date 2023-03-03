package by.itstep.stpnbelko.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coins")
@Data
public class Coin {


    @Id
    //@GeneratedValue(strategy = GenerationType.)
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

    @Column
    private double price_usd;

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

    

}
