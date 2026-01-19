package Nemsi.AiStock.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class PreStockTest {

    @Test
    void testPreStockConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        PreStock preStock = new PreStock(1L, date, "APPL", "Apple", 15000.0, 16000.0, 1000.0, "Y");

        assertThat(preStock.getId()).isEqualTo(1L);
        assertThat(preStock.getDate()).isEqualTo(date);
        assertThat(preStock.getTicker()).isEqualTo("APPL");
        assertThat(preStock.getPredictGap()).isEqualTo(1000L);
        assertThat(preStock.getUseYN()).isEqualTo("Y");
    }
}
