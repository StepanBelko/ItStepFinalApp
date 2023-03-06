package by.itstep.stpnbelko.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "coins_history", schema = "it_step_final_db", catalog = "")
public class History {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "time_stamp", nullable = true, length = 45)
    private String timeStamp;
    @Basic
    @Column(name = "action", nullable = true, length = 45)
    private String action;


    @Basic
    @Column(name = "coin_id", nullable = true)
    private Integer coinId;

    @Basic
    @Column(name = "csupply", nullable = true, precision = 0)
    private Double csupply;

    @Basic
    @Column(name = "market_cap_usd", nullable = true, precision = 0)
    private Double marketCapUsd;

    @Basic
    @Column(name = "msupply", nullable = true, precision = 0)
    private Double msupply;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;

    @Basic
    @Column(name = "nameid", nullable = true, length = -1)
    private String nameid;

    @Basic
    @Column(name = "percent_change_1h", nullable = true, precision = 0)
    private Double percentChange1H;

    @Basic
    @Column(name = "percent_change_24h", nullable = true, precision = 0)
    private Double percentChange24H;

    @Basic
    @Column(name = "percent_change_7d", nullable = true, precision = 0)
    private Double percentChange7D;

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Double price;

    @Basic
    @Column(name = "price_usd", nullable = true, precision = 0)
    private Double priceUsd;

    @Basic
    @Column(name = "price_btc", nullable = true, precision = 0)
    private Double priceBtc;

    @Basic
    @Column(name = "coin_rank", nullable = true)
    private Integer coinRank;

    @Basic
    @Column(name = "symbol", nullable = true, length = -1)
    private String symbol;

    @Basic
    @Column(name = "tsupply", nullable = true, precision = 0)
    private Double tsupply;

    @Basic
    @Column(name = "volume24", nullable = true, precision = 0)
    private Double volume24;

    @Basic
    @Column(name = "volume24a", nullable = true, precision = 0)
    private Double volume24A;

    @Basic
    @Column(name = "volume_native", nullable = true, precision = 0)
    private Double volumeNative;

}
