package com.example.app;

import io.lettuce.core.ReadFrom;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
@ConditionalOnProperty("redis.master")
public class RedisConfig {

    private String master;
    private List<String> slaves = Collections.emptyList();

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration
                .builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        RedisStaticMasterReplicaConfiguration staticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(
                this.getMaster().split(":")[0],
                Integer.parseInt(this.getMaster().split(":")[1]));
        this.getSlaves().forEach(slave -> staticMasterReplicaConfiguration.addNode(
                slave.split(":")[0],
                Integer.parseInt(slave.split(":")[1])));
        return new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);
    }

}