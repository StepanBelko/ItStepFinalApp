package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.entity.User;
import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.util.JSONParser;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.repository.CoinRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return coinRepository.findAll(Sort.by(Sort.Direction.ASC, "rank"));
    }

    @Override
    public boolean updateCoinList() {
        List<Coin> coins = JSONParser.getCoinsListFromUrl();
        if (coins != null) {
            coinRepository.saveAll(coins);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCoinList(List<Coin> coins) {
        if (coins != null) {
            coinRepository.saveAll(coins);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateElement(Coin changeable, Coin coin) {
        System.out.println("Delete coin : " + changeable.getName());
//        coinRepository.findBy();

        return true;
    }

    @Override
    public Coin findByRank(int rank) {
        for (Coin coin : coinRepository.findAll()) {
            if (coin.getRank() == rank) {
                return coin;
            }
        }
        return null;
    }

    @Override
    public Page<Coin> pagination(int page, int size, String sortByField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return coinRepository.findAll(pageable);
    }
}
