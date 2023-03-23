package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.dto.HistoryDto;
import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.entity.History;
import by.itstep.stpnbelko.entity.User;
import by.itstep.stpnbelko.repository.HistoryRepository;
import by.itstep.stpnbelko.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<HistoryDto> findAllHistory() {
        List<History> historyDtoList = (List<History>) historyRepository.findAll();
        return historyDtoList.stream().map((history) -> convertEntityToDto(history))
                .collect(Collectors.toList());
    }

    @Override
    public void clearHistory() {
        historyRepository.deleteAll();
    }

    private HistoryDto convertEntityToDto(History history) {
        HistoryDto historyDto = new HistoryDto();

        historyDto.setId(history.getId());
        historyDto.setAction(history.getAction());
        historyDto.setCoinId(history.getCoinId());
        historyDto.setRank(history.getCoinRank());
        historyDto.setName(history.getName());
        historyDto.setSymbol(history.getSymbol());
        historyDto.setPercent_change_1h(history.getPercentChange1H());
        historyDto.setPercent_change_24h(history.getPercentChange24H());
        historyDto.setPercent_change_7d(history.getPercentChange7D());
        historyDto.setPrice_btc(history.getPriceBtc());
        historyDto.setPrice_usd(history.getPriceUsd());

        return historyDto;
    }


    public Page<History> pagination(int page, int size, String sortByField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        System.out.println("!!! Sort field : " + sortByField);

        //Тут всё валится из-за нижнего подчёркивания price_usd

        Page<History> coinPage = historyRepository.findAll(pageable);

        return coinPage;


    }
}
