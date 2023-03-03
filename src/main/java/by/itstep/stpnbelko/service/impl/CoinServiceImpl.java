package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.util.JSONParser;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.repository.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;

    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<Coin> getAllCoinsFromWeb() {
        return JSONParser.getCoinsListFromUrl();
    }

    @Override
    public List<Coin> getAllCoinsFromDB() {
        return coinRepository.findAll();
    }

    @Override
    public void updateCoinList() {
        List<Coin> coins = JSONParser.getCoinsListFromUrl();
        System.out.println("_________________");
        System.out.println(coins);
        coinRepository.saveAll(coins);
    }

}
