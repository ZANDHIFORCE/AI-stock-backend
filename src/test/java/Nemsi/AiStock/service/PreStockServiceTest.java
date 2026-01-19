package Nemsi.AiStock.service;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.respository.PreStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PreStockServiceTest {

    @Mock
    PreStockRepository preStockRepository;

    @InjectMocks
    PreStockService preStockService;

    @Test
    void join_calculates_predictGap() {
        // given
        PreStock preStock = new PreStock();
        preStock.setTicker("TEST");
        preStock.setCurPrice(10000L);
        preStock.setPredictPrice(12000L);

        // when
        preStockService.join(preStock);

        // then
        assertThat(preStock.getPredictGap()).isEqualTo(2000L); // 12000 - 10000
        verify(preStockRepository).save(any(PreStock.class));
    }
}
