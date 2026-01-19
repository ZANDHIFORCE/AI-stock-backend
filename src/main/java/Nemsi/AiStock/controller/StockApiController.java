package Nemsi.AiStock.controller;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.service.PreStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<PreStock> index(@RequestParam(name = "useYN", required = false) String useYN){
        if ("Y".equals(useYN)) {
            return preStockService.findActivePreStocks();
        }
        return preStockService.findPreStocks();
    }

    //POST
    //PATCH
    //DELETE
}
