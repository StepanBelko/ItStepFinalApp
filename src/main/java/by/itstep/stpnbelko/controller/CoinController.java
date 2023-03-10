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

import java.util.List;

@Controller
public class CoinController {

    private final CoinService coinService;
    private final HistoryService historyService;

    public CoinController(CoinService coinService, HistoryService historyService) {
        this.coinService = coinService;
        this.historyService = historyService;
    }


    @GetMapping("/coins")
    public String showAllCoins(Model model) {
        coinService.compareLists();
/*        List<Coin> coinFromWeb = coinService.getAllCoinsFromWeb();
        List<Coin> coins = coinService.getAllCoinsFromDB();

        if (coins.isEmpty()) {
            coinService.updateCoinList();
            coins = coinService.getAllCoinsFromDB();
        }

        for (int i = 0; i < coinFromWeb.size(); i++) {
            if (!coins.get(i).equals(coinFromWeb.get(i))) {
                System.out.println("Change : " + coins.get(i).getName());
                coins.set(i, coinFromWeb.get(i));
            }

        }

        System.out.println(coins.equals(coinFromWeb));
        coinService.updateCoinList(coins);

        model.addAttribute("coins", coins);
        return "coins";*/
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
        return "coinsNewTemplate";
    }

    @GetMapping("/coins/update/{pageNo}")
    public String update(@PathVariable(value = "pageNo") int pageNo,
                         @RequestParam("sortField") String sortField,
                         @RequestParam("sortDir") String sortDir,
                         Model model) {

        coinService.compareLists();
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
