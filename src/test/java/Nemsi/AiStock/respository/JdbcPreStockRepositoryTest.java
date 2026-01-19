package Nemsi.AiStock.respository;

import Nemsi.AiStock.domain.PreStock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JdbcPreStockRepositoryTest {

    @Autowired
    JdbcPreStockRepository repository;

    @Test
    void save_and_findById() {
        // given
        PreStock preStock = new PreStock();
        preStock.setTicker("SAMSUNG");
        preStock.setDate(LocalDate.of(2026, 1, 12));
        preStock.setName("Samsung Electronics");
        preStock.setCurPrice(70000L);
        preStock.setPredictPrice(75000L);
        preStock.setPredictGap(5000L);

        // when
        repository.save(preStock);

        // then
        Optional<PreStock> result = repository.findById("SAMSUNG", LocalDate.of(2026, 1, 12), preStock.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getTicker()).isEqualTo("SAMSUNG");
        assertThat(result.get().getUseYN()).isEqualTo("Y");
    }

    @Test
    void save_updates_previous_useYN() {
        // given
        LocalDate date = LocalDate.of(2026, 1, 12);
        
        PreStock first = new PreStock();
        first.setTicker("SAMSUNG");
        first.setDate(date);
        repository.save(first);

        // when (save second record with same ticker/date)
        PreStock second = new PreStock();
        second.setTicker("SAMSUNG");
        second.setDate(date);
        repository.save(second);

        // then
        // First record should be 'N'
        Optional<PreStock> oldRecord = repository.findById("SAMSUNG", date, first.getId());
        assertThat(oldRecord.get().getUseYN()).isEqualTo("N");

        // Second record should be 'Y' and ID should be incremented
        Optional<PreStock> newRecord = repository.findById("SAMSUNG", date, second.getId());
        assertThat(newRecord.get().getUseYN()).isEqualTo("Y");
        assertThat(second.getId()).isEqualTo(first.getId() + 1);
    }
}
