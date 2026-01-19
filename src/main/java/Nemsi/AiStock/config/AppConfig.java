package Nemsi.AiStock.config;

import Nemsi.AiStock.respository.JdbcPreStockRepository;
import Nemsi.AiStock.respository.PreStockRepository;
import Nemsi.AiStock.service.PreStockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    private final DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PreStockService preStockService() {
        return new PreStockService(preStockRepository());
    }

    @Bean
    public PreStockRepository preStockRepository() {
        return new JdbcPreStockRepository(dataSource);
    }
}
