package com.learning.buildpack.utility;

import com.learning.buildpack.configuration.RedisBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/*
    Uitility class to handle the Redis Connection
 */
@Configuration
public class RedisUtility {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtility.class);

    private static JedisPool jedisPool;

    @Autowired
    private RedisBean redisBean;

    /**
     * Initialize Jedis Pool with Default Timeout
     */
    public void initializeJedisPool() {
        if (jedisPool == null) {
            jedisPool = new JedisPool(new JedisPoolConfig(),
                    redisBean.getHost(),
                    Integer.parseInt(redisBean.getPort()),
                    Protocol.DEFAULT_TIMEOUT);
            //        redisBean.getPassword());
            logger.info("Jedis Pool is Initialized");
        }
    }

    /**
     * Method to return Jedis Pool. If the pool is null, it calls
     * initialize method and then returns the pool
     * @return {@link JedisPool}
     */
    public JedisPool getJedisPool() {
        if (jedisPool == null) {
            initializeJedisPool();
        }
        return jedisPool;
    }

    /**
     * Method to close and destroy Jedis Pool
     */
    public void destroyJedisPool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            jedisPool.destroy();
            logger.info("Closed and Destroyed Jedis Pool");
        }
    }

}
