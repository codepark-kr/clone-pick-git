package com.pg.pickgit.config.database;

import com.pg.pickgit.user.domain.search.UserSearchEngine;
import lombok.Value;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = UserSearchEngine.class)
@Configuration
public class ElasticSearchConfiguration extends AbstractElasticsearchConfiguration {

    private final String elasticSeartchHost;

    public ElasticSearchConfiguration(
            @Value("${security.elasticsearch.host}") String elasticSeartchHost
    ) {
        this.elasticSeartchHost = elasticSeartchHost;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticSeartchHost)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
