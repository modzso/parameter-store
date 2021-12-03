/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package parameter.store.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import parameter.store.jpa.Parameter;
import parameter.store.jpa.ParameterRepository;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = ParameterRepository.class)
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("parameter.store.jpa");
        factory.setDataSource(dataSource());
        return factory;
    }


    @Bean
    public CommandLineRunner demo(ParameterRepository repository) {
        return (args) -> {
            repository.save(new Parameter("test", "/app/parameter-1", "Hello"));
            repository.save(new Parameter("test", "/app/parameter-2", "World!"));

            log.info("Listing all params for /app");
            List<Parameter> params =  repository.findByEnvironmentAndNameStartingWith("test", "/app");
            for (Parameter parameter : params) {
                log.info("{}", parameter);
            }

            log.info("Listing all params in environment: ");
            params = repository.findByEnvironment("test");
            for (Parameter parameter : params) {
                log.info("{}", parameter);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

}