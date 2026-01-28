package Nemsi.AiStock.controller;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.service.PreStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
class StockApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PreStockService preStockService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        PreStock stock1 = new PreStock();
        stock1.setTicker("AAPL");
        stock1.setName("Apple Inc");
        stock1.setDate(LocalDate.of(2024, 1, 1));
        stock1.setCurPrice(150.0);
        stock1.setPredictPrice(160.0);
        preStockService.join(stock1);

        PreStock stock2 = new PreStock();
        stock2.setTicker("MSFT");
        stock2.setName("Microsoft");
        stock2.setDate(LocalDate.of(2024, 1, 2));
        stock2.setCurPrice(300.0);
        stock2.setPredictPrice(310.0);
        preStockService.join(stock2);

        PreStock stock3 = new PreStock();
        stock3.setTicker("AAPL");
        stock3.setName("Apple Inc");
        stock3.setDate(LocalDate.of(2024, 1, 2));
        stock3.setCurPrice(155.0);
        stock3.setPredictPrice(165.0);
        preStockService.join(stock3);
    }

    @Test
    void searchByTicker() throws Exception {
        mockMvc.perform(get("/api/stocks").param("ticker", "AAPL"))
                .andDo(result -> System.err.println("DEBUG RESPONSE: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // AAPL 데이터는 2개임
                .andExpect(jsonPath("$[0].ticker", is("AAPL")));
    }

    @Test
    void searchByName() throws Exception {
        mockMvc.perform(get("/api/stocks").param("name", "Micro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Micro를 포함한 데이터는 Microsoft 1개임
                .andExpect(jsonPath("$[0].name", org.hamcrest.Matchers.containsString("Microsoft")));
    }

    @Test
    void searchByDate() throws Exception {
        mockMvc.perform(get("/api/stocks").param("date", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].ticker", is("AAPL")));
    }
}