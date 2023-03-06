package by.itstep.stpnbelko.repository;

import by.itstep.stpnbelko.dto.HistoryDto;
import by.itstep.stpnbelko.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
