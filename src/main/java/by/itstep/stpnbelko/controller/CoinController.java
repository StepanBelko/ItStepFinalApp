package by.itstep.stpnbelko.controller;

import by.itstep.stpnbelko.entity.Coin;
import by.itstep.stpnbelko.entity.History;
import by.itstep.stpnbelko.service.CoinService;
import by.itstep.stpnbelko.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CoinController {

    private final CoinService coinService;
    private final HistoryService historyService;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss; dd MMMM yyyy");

    public CoinController(CoinService coinService, HistoryService historyService) {
        this.coinService = coinService;
        this.historyService = historyService;
    }


    @GetMapping("/coins")
    public String showAllCoins(Model model) {
//        coinService.compareLists();
        return pagination(1, "rank", "ASC", model);
    }


    @GetMapping("/coins/page/{pageNo}")
    public String pagination(@PathVariable(value = "pageNo") int pageNo,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             Model model) {
        int pageSize = 20;


        Page<Coin> page = coinService.pagination(pageNo, pageSize, sortField, sortDir);
        List<Coin> listCoins = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCoins", listCoins);

        model.addAttribute("lastUpdTime", coinService.getLastUpdTime().format(TIME_FORMATTER));
        return "coinsNewTemplate";
    }

    @GetMapping("/coins/update/{pageNo}")
    public String update(@PathVariable(value = "pageNo") int pageNo,
                         @RequestParam("sortField") String sortField,
                         @RequestParam("sortDir") String sortDir,
                         Model model) {

        coinService.compareLists();
        model.addAttribute("lastUpdTime", coinService.getLastUpdTime().format(TIME_FORMATTER));
        return pagination(pageNo, sortField, sortDir, model);
    }


    @GetMapping("/history")
    public String showAllHistory(Model model) {
        return historyPagination(1, "timeStamp", "DESC", model);
    }


    @GetMapping("/history/page/{pageNo}")
    public String historyPagination(@PathVariable(value = "pageNo") int pageNo,
                                    @RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String sortDir,
                                    Model model) {
        int pageSize = 20;


        Page<History> page = historyService.pagination(pageNo, pageSize, sortField, sortDir);
        List<History> listHistory = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listHistory", listHistory);
        return "history";
    }
}
