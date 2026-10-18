package nemsi.aistock.config;

import nemsi.aistock.repository.JdbcPreStockRepository;
import nemsi.aistock.repository.PreStockRepository;
import nemsi.aistock.service.PreStockService;
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
