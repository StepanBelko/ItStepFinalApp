package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.entity.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getAllCoinsFromWeb();
    List<Coin> getAllCoinsFromDB();
    boolean updateCoinList();
    boolean updateCoinList(List<Coin> coinList);

    boolean updateElement(Coin changeable, Coin coin);
    Coin findByRank(int rank);
}
