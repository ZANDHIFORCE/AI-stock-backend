package Nemsi.AiStock.controller;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.service.PreStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PreStockController {

    private final PreStockService preStockService;

    @Autowired
    public PreStockController(PreStockService preStockService) {
        this.preStockService = preStockService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/stocks")
    public String list(Model model) {
        List<PreStock> stocks = preStockService.findActivePreStocks();
        model.addAttribute("stocks", stocks);
        return "stocks/stockList";
    }

    @GetMapping("/stocks/new")
    public String createForm() {
        return "stocks/createStockForm";
    }

    @PostMapping("/stocks/new")
    public String create(PreStock form) {
        // The form data is automatically bound to the PreStock object
        // assuming input names match field names (ticker, name, date, curPrice, predictPrice)
        preStockService.join(form);
        return "redirect:/stocks";
    }
}