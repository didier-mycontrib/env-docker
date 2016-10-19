package tp.spring.config.ds;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db/h2/db.properties")
@Profile("h2")
public class DataSourceConfigH2 extends AbstractDataSourceConfig {
	

}
