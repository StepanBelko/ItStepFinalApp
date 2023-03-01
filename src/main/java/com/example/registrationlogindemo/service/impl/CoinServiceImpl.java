package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.entity.Coin;
import com.example.registrationlogindemo.repository.CoinRepository;
import com.example.registrationlogindemo.service.CoinService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.registrationlogindemo.util.JSONParser.getCoinsListFromUrl;

@Service
public class CoinServiceImpl implements CoinService {
    private CoinRepository coinRepository;

    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<Coin> getAllCoinsFromWeb() {
        return getCoinsListFromUrl();
    }

    @Override
    public List<Coin> getAllCoinsFromDB() {
        return coinRepository.findAll();
    }

    @Override
    public void updateCoinList() {
        List<Coin> coins = getCoinsListFromUrl();
        System.out.println("_________________");
        System.out.println(coins);
        coinRepository.saveAll(coins);
    }

}
