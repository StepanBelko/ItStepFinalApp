package by.itstep.stpnbelko.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

    @NotEmpty
    private int id;

    @NotEmpty
    private String action;

    @NotEmpty
    private int coinId;

    @NotEmpty
    private String symbol;

    @NotEmpty
    private String name;

    @NotEmpty
    private int rank;

    @NotEmpty
    private double price_usd;

    @NotEmpty
    private double price_btc;

    @NotEmpty
    private double percent_change_24h;

    @NotEmpty
    private double percent_change_1h;

    @NotEmpty
    private double percent_change_7d;




}
