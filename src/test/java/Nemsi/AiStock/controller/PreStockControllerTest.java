package Nemsi.AiStock.controller;

import Nemsi.AiStock.service.PreStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class PreStockControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private PreStockService preStockService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createStock_ShouldBindDateCorrectly() throws Exception {
        mockMvc.perform(post("/stocks/new")
                        .param("ticker", "TEST_TICKER")
                        .param("name", "Test Stock")
                        .param("date", "2026-01-12") // Form sends string
                        .param("curPrice", "1000")
                        .param("predictPrice", "1200"))
                .andExpect(status().is3xxRedirection());
    }
}
