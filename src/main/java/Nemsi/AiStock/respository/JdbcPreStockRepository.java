package Nemsi.AiStock.respository;

import Nemsi.AiStock.domain.PreStock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class JdbcPreStockRepository implements PreStockRepository {

    private final JdbcTemplate jdbcTemplate;
    // 날짜 변환을 위한 포맷터 (yyyy-MM-dd)
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public JdbcPreStockRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PreStock save(PreStock preStock) {
        // 모든 컬럼이 VARCHAR일 경우를 대비한 조회 및 저장
        String maxIdSql = "SELECT MAX(CAST(id AS BIGINT)) FROM stock WHERE ticker = ? AND date = ?";
        Long maxId = jdbcTemplate.queryForObject(maxIdSql, Long.class, preStock.getTicker(), preStock.getDate().format(formatter));
        long nextId = (maxId == null) ? 0L : maxId + 1;

        String updateOldSql = "UPDATE stock SET useyn = 'N' WHERE ticker = ? AND date = ?";
        jdbcTemplate.update(updateOldSql, preStock.getTicker(), preStock.getDate().format(formatter));

        preStock.setId(nextId);
        preStock.setUseYN("Y");

        // 데이터를 String으로 변환하여 저장
        String insertSql = "INSERT INTO stock(id, date, ticker, name, curprice, predictprice, predictgap, useyn) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                String.valueOf(preStock.getId()),
                preStock.getDate().format(formatter),
                preStock.getTicker(),
                preStock.getName(),
                (preStock.getCurPrice() == null) ? null : String.format("%.3f", preStock.getCurPrice()),
                (preStock.getPredictPrice() == null) ? null : String.format("%.3f", preStock.getPredictPrice()),
                (preStock.getPredictGap() == null) ? null : String.format("%.3f", preStock.getPredictGap()),
                preStock.getUseYN());

        return preStock;
    }

    @Override
    public Optional<PreStock> findById(String ticker, LocalDate date, Long id) {
        String sql = "SELECT * FROM stock WHERE ticker = ? AND date = ? AND id = ?";
        List<PreStock> result = jdbcTemplate.query(sql, preStockRowMapper(), ticker, date.format(formatter), String.valueOf(id));
        return result.stream().findAny();
    }

    @Override
    public List<PreStock> findAll() {
        String sql = "SELECT * FROM stock";
        return jdbcTemplate.query(sql, preStockRowMapper());
    }

    @Override
    public List<PreStock> findAllByUseYN(String useYN) {
        String sql = "SELECT * FROM stock WHERE useyn = ?";
        return jdbcTemplate.query(sql, preStockRowMapper(), useYN);
    }

    @Override
    public void update(String ticker, LocalDate date, Long id, PreStock updateParam) {
        String sql = "UPDATE stock SET name=?, curprice=?, predictprice=?, predictgap=?, useyn=? WHERE ticker=? AND date=? AND id=?";
        jdbcTemplate.update(sql,
                updateParam.getName(),
                (updateParam.getCurPrice() == null) ? null : String.format("%.3f", updateParam.getCurPrice()),
                (updateParam.getPredictPrice() == null) ? null : String.format("%.3f", updateParam.getPredictPrice()),
                (updateParam.getPredictGap() == null) ? null : String.format("%.3f", updateParam.getPredictGap()),
                updateParam.getUseYN(),
                ticker,
                date.format(formatter),
                String.valueOf(id));
    }

    @Override
    public void delete(String ticker, LocalDate date, Long id) {
        String sql = "DELETE FROM stock WHERE ticker = ? AND date = ? AND id = ?";
        jdbcTemplate.update(sql, ticker, date.format(formatter), String.valueOf(id));
    }

    private RowMapper<PreStock> preStockRowMapper() {
        return (rs, rowNum) -> {
            PreStock preStock = new PreStock();
            // String으로 읽어서 타입 변환
            String idStr = rs.getString("id");
            if (idStr != null) preStock.setId(Long.parseLong(idStr));

            String dateStr = rs.getString("date");
            if (dateStr != null) preStock.setDate(LocalDate.parse(dateStr, formatter));

            preStock.setTicker(rs.getString("ticker"));
            preStock.setName(rs.getString("name"));

            // 소수점을 처리하기 위해 Double.parseDouble 사용
            String curPriceStr = rs.getString("curprice");
            if (curPriceStr != null) preStock.setCurPrice(Double.parseDouble(curPriceStr));

            String predictPriceStr = rs.getString("predictprice");
            if (predictPriceStr != null) preStock.setPredictPrice(Double.parseDouble(predictPriceStr));

            String predictGapStr = rs.getString("predictgap");
            if (predictGapStr != null) preStock.setPredictGap(Double.parseDouble(predictGapStr));

            preStock.setUseYN(rs.getString("useyn"));
            return preStock;
        };
    }
}
