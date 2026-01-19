package Nemsi.AiStock.controller;

import Nemsi.AiStock.service.PreStockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiTestController {

    @GetMapping("/api/test")
    public String hello(){
        return "hello RestApi!";
    }

}
