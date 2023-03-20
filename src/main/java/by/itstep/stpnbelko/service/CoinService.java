package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CoinService {
    List<Coin> getAllCoinsFromWeb();
    List<Coin> getAllCoinsFromDB();
    boolean updateCoinList();

    Page<Coin> pagination(int pageNo, int pageSize, String sortField, String sortDir);

    boolean compareLists();

    LocalDateTime getLastUpdTime();

}
