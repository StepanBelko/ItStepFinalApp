package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.dto.HistoryDto;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryService {

    Page<History> pagination(int pageNo, int pageSize, String sortField, String sortDir);
    public List<HistoryDto> findAllHistory();
}
