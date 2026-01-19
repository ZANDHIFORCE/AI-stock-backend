package Nemsi.AiStock.respository;

import Nemsi.AiStock.domain.PreStock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PreStockRepository {
    PreStock save(PreStock preStock);
    Optional<PreStock> findById(String ticker, LocalDate date, Long id);
    List<PreStock> findAll();
    List<PreStock> findAllByUseYN(String useYN);
    void update(String ticker, LocalDate date, Long id, PreStock updateParam);
    void delete(String ticker, LocalDate date, Long id);
}
