package by.itstep.stpnbelko.repository;

import by.itstep.stpnbelko.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {
}
