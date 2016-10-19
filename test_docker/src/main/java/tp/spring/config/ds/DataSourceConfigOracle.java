package tp.spring.config.ds;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db/oracle/db.properties")
@Profile("oracle")
public class DataSourceConfigOracle extends AbstractDataSourceConfig {
	

}
