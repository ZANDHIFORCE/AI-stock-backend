package Nemsi.AiStock;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.service.PreStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AiStockIntegrationTest {

    @Autowired PreStockService preStockService;

    @Test
    void full_flow_integration() {
        // given
        PreStock preStock = new PreStock();
        preStock.setTicker("INTEG_TEST");
        preStock.setDate(LocalDate.now());
        preStock.setCurPrice(100L);
        preStock.setPredictPrice(200L);
        // predictGap logic is inside service

        // when
        Long savedId = preStockService.join(preStock);

        // then
        // 1. Verify retrieval
        PreStock findOne = preStockService.findOne("INTEG_TEST", LocalDate.now(), savedId).get();
        assertThat(findOne.getPredictGap()).isEqualTo(100L); // 200 - 100
        assertThat(findOne.getUseYN()).isEqualTo("Y");

        // 2. Verify active list
        List<PreStock> activeStocks = preStockService.findActivePreStocks();
        assertThat(activeStocks).extracting("ticker").contains("INTEG_TEST");
    }
}
