package Nemsi.AiStock.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class PreStockTest {

    @Test
    void createPreStock() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 12);
        PreStock preStock = new PreStock(1L, date, "APPL", "Apple", 15000L, 16000L, 1000L, "Y");

        // when & then
        assertThat(preStock.getId()).isEqualTo(1L);
        assertThat(preStock.getDate()).isEqualTo(date);
        assertThat(preStock.getTicker()).isEqualTo("APPL");
        assertThat(preStock.getPredictGap()).isEqualTo(1000L);
        assertThat(preStock.getUseYN()).isEqualTo("Y");
    }
}
