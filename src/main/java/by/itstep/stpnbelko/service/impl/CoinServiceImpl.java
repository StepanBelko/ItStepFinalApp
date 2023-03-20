package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.util.JSONParser;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.repository.CoinRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;
    private LocalDateTime lastUpdTime;

    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
        lastUpdTime = LocalDateTime.now();
    }

    @Override
    public List<Coin> getAllCoinsFromWeb() {
        List<Coin> coinList = JSONParser.getCoinsListFromUrl();

        for (Coin coin : coinList) {
            coin.setPrice(coin.getPrice_usd());
        }

        return coinList;
    }

    @Override
    public List<Coin> getAllCoinsFromDB() {
        return coinRepository.findAll(Sort.by(Sort.Direction.ASC, "rank"));
    }

    @Override
    public boolean updateCoinList() {
        List<Coin> coins = getAllCoinsFromWeb();
        if (coins != null) {
            lastUpdTime = LocalDateTime.now();
            coinRepository.saveAll(coins);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean updateCoinList(List<Coin> coins) {
//        if (coins != null) {
//            coinRepository.saveAll(coins);
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public boolean updateElement(Coin changeable, Coin coin) {
//        System.out.println("Delete coin : " + changeable.getName());
////        coinRepository.findBy();
//
//        return true;
//    }

//    @Override
//    public Coin findByRank(int rank) {
//        for (Coin coin : coinRepository.findAll()) {
//            if (coin.getRank() == rank) {
//                return coin;
//            }
//        }
//        return null;
//    }

    @Override
    public Page<Coin> pagination(int page, int size, String sortByField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        System.out.println("!!! Sort field : " + sortByField);

        //Тут всё валится из-за нижнего подчёркивания price_usd
        Page<Coin> coinPage = coinRepository.findAll(pageable);

        return coinPage;
    }

    @Override
    public boolean compareLists() {
        List<Coin> coinsFromDB = getAllCoinsFromDB();
        List<Coin> coinsFromAPI = getAllCoinsFromWeb();
        int counter = 0;

        if (coinsFromDB.isEmpty()) {
            updateCoinList();    // Обновить базу
            coinsFromDB = coinRepository.findAll(); // Взять из неё данные
        }

        boolean isTablesEquals = coinsFromDB.equals(coinsFromAPI);

        System.out.println("Equals???? -      " + isTablesEquals);

        if (!isTablesEquals) {
            for (int i = 0; i < coinsFromAPI.size(); i++) {
                if (!coinsFromDB.get(i).equals(coinsFromAPI.get(i))) {
                    coinsFromDB.get(i).setPrice(coinsFromDB.get(i).getPrice_usd());
                    coinsFromDB.set(i, coinsFromAPI.get(i));
                    System.out.println("Change : " + coinsFromDB.get(i).getName());
                    counter++;
                }
            }
        }
        lastUpdTime = LocalDateTime.now();

        System.out.println("Total changed : " + counter + ". Successfully " + coinsFromDB.equals(coinsFromAPI));
        coinRepository.saveAll(coinsFromDB);

        return coinsFromDB.equals(coinsFromAPI);
    }

    @Override
    public LocalDateTime getLastUpdTime() {
        return lastUpdTime;
    }
}
