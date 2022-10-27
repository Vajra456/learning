package com.learning.buildpack.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RedisBean {

    // TODO: Pick up the configuration from environment variables
    @Value("${services.my-redis.connection.host}")
    private String host;
    @Value("${services.my-redis.connection.password}")
    private String password;
    @Value("${services.my-redis.connection.port}")
    private String port;

}
