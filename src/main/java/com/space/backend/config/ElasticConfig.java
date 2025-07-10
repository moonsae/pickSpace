package com.space.backend.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.space.backend.elastic")
public class ElasticConfig{

    /*@Bean
    public RestClient restClient() {
        return RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ).build();
    }*/
    @Value("${spring.elasticsearch.host}")
    private String esHost;

    @Value("${spring.elasticsearch.port}")
    private int esPort;

    @Value("${spring.elasticsearch.scheme}")
    private String esScheme;

    @Bean
    public RestClient restClient() {
        return RestClient.builder(
                new HttpHost(esHost, esPort, esScheme)
        ).build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        return new ElasticsearchClient(
                new RestClientTransport(restClient, new JacksonJsonpMapper())
        );
    }

}