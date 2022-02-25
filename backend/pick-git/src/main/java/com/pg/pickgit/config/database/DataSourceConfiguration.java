package com.pg.pickgit.config.database;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "prod", "staging" })
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(MasterDataSourceProperties.class)
@Configuration
public class DataSourceConfiguration {

    private final MasterDataSourceProperties dataSourcecProperties;
    private final JpaProperties jpaProperties;

    public DataSourceConfiguration(
            MasterDataSourceProperties dataSourceProperties,
            JpaProperties jpaProperties
    ){
        this.dataSourcecProperties = dataSourceProperties;
        this.jpaProperties = jpaProperties;
    }
}
