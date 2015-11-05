package com.crossover;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class JpaConfig implements TransactionManagementConfigurer {
//    @Value("${spring.datasource.driver-class-name}")
//    private String driver;
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.jpa.properties.hibernate.dialect}")
//    private String dialect;
//    @Value("${spring.jpa.hibernate.ddl-auto}")
//    private String hbm2ddlAuto;
//    @Value("${spring.jpa.show-sql}")
//    private String showSql;
//    @Value("${spring.jpa.format-sql}")
//    private String formatSql;

//    @Bean
//    public DataSource dataSource() {
//        final HikariConfig config = new HikariConfig();
//        config.setDriverClassName(driver);
//        config.setJdbcUrl(url);
//        config.setUsername(username);
//        config.setPassword(password);
//        return new HikariDataSource(config);
//    }

//    @Autowired
//    DataSource dataSource;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPackagesToScan("com.crossover.entity");
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        final Properties jpaProperties = new Properties();
//        jpaProperties.put(Environment.DIALECT, dialect);
//        jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
//        jpaProperties.put(Environment.SHOW_SQL, showSql);
//        jpaProperties.put(Environment.FORMAT_SQL, formatSql);
//        entityManagerFactoryBean.setJpaProperties(jpaProperties);
//
//        return entityManagerFactoryBean;
//    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }
}
