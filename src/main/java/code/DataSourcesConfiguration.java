package code;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration primary data sources.
 */
@Configuration
@EnableTransactionManagement
public class DataSourcesConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.ascdb")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }


//    @Bean("jpaTransactionManager")
//    @Primary
//    public JpaTransactionManager jpaTransactionManager(){
//        var jpa= new JpaTransactionManager();
//        jpa.setDataSource(primaryDataSource());
//        jpa.setJpaDialect(new HibernateJpaDialect());
//        return jpa;
//    }
}


