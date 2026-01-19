package Nemsi.AiStock.controller;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.service.PreStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StockApiController {

    private final PreStockService preStockService;

    @Autowired
    public StockApiController(PreStockService preStockService){
        this.preStockService = preStockService;
    }

    //GET
    @GetMapping("api/stocks")
    public List<PreStock> index(
            @RequestParam(name = "ticker", required = false) String ticker,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "useYN", required = false) String useYN){
        
        LocalDate localDate = null;
        if (date != null && !date.isEmpty()) {
            localDate = LocalDate.parse(date);
        }

        return preStockService.searchPreStocks(ticker, name, localDate, useYN);
    }

    //POST
    //PATCH
    //DELETE
}
