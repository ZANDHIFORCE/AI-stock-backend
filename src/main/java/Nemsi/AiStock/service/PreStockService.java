package Nemsi.AiStock.service;

import Nemsi.AiStock.domain.PreStock;
import Nemsi.AiStock.respository.PreStockRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PreStockService {

    private final PreStockRepository preStockRepository;

    public PreStockService(PreStockRepository preStockRepository) {
        this.preStockRepository = preStockRepository;
    }

    /**
     * 주식 예측 데이터 저장
     * - predictGap 자동 계산
     * - 레포지토리 save 호출 (ID 생성 및 useYN 갱신 로직 포함)
     */
    public Long join(PreStock preStock) {
        // 비즈니스 로직: predictGap 계산
        if (preStock.getPredictPrice() != null && preStock.getCurPrice() != null) {
            preStock.setPredictGap(preStock.getPredictPrice() - preStock.getCurPrice());
        }

        preStockRepository.save(preStock);
        return preStock.getId();
    }

    /**
     * 전체 조회
     */
    public List<PreStock> findPreStocks() {
        return preStockRepository.findAll();
    }

    /**
     * 사용 중인(최신) 데이터 전체 조회 (useYN = 'Y')
     */
    public List<PreStock> findActivePreStocks() {
        return preStockRepository.findAllByUseYN("Y");
    }

    /**
     * 단건 조회 (복합키)
     */
    public Optional<PreStock> findOne(String ticker, LocalDate date, Long id) {
        return preStockRepository.findById(ticker, date, id);
    }
}