package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.entity.Coin;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface CoinService {
    List<Coin> getAllCoinsFromWeb();
    List<Coin> getAllCoinsFromDB();
    void updateCoinList();

    Page<Coin> pagination(int pageNo, int pageSize, String sortField, String sortDir);

    boolean compareLists();

    LocalDateTime getLastUpdTime();

}
