package by.itstep.stpnbelko.controller;

import by.itstep.stpnbelko.entity.History;
import by.itstep.stpnbelko.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HistoryController {
    private final HistoryService historyService;


    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
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


    @GetMapping("/history/clear")
    public String clearHistory(@RequestParam(value = "action", required = false) String action, Model model) {
        if (action.equals("yes")) {
            historyService.clearHistory();
        }
        return "redirect:/history";
    }

}
