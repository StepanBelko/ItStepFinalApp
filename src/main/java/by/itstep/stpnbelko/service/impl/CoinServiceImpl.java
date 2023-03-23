package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.util.JSONParser;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.repository.CoinRepository;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@EnableScheduling
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
    
    @Scheduled(initialDelayString = "${initialDelay}", fixedRateString = "${fixedRate}")
    @Override
    public void updateCoinList() {
        System.out.println("\nUPDATE DATA \n" + lastUpdTime.format(DateTimeFormatter.ofPattern("HH:mm:ss; dd MMMM yyyy")) + "\n\n");
        List<Coin> coins = getAllCoinsFromWeb();
        if (coins != null) {
            lastUpdTime = LocalDateTime.now();
            coinRepository.saveAll(coins);
        }
    }

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
