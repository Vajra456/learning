package com.learning.buildpack.controller;
import com.learning.buildpack.model.PacketMetaData;
import com.learning.buildpack.utility.RedisUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private RedisUtility redisUtility;

    private JedisPool jedisPool = null;

    @GetMapping("/")
    public String index() {
        return "{ 'message' : 'Jai Jagannath'}";
    }

    @RequestMapping("/testConnection")
    public String testConnection() {
        String message = null;

        jedisPool = redisUtility.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            if(jedis.isConnected()) {
                message = "connection successful";
            } else {
                message = "connection failed";
            }
        } catch (Exception ex) {
            logger.error("Error in conencting to redis. Error Message: " + ex.getMessage());
            message = "connection failed";
        }
        return message;
    }

    @RequestMapping("/addData")
    public String addData() {
        String key = null;
        String returnMsg = null;
        Long returnCode =  Long.valueOf(-1);
        int min = 100;
        int max = 100000000;


        jedisPool = redisUtility.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            for (int i=0; i < 10000000; i++) {
                // Create a PacketMetaData Object
                PacketMetaData metadata = new PacketMetaData();
                metadata.setRefID(UUID.randomUUID().toString());
                metadata.setAge(ThreadLocalRandom.current().nextInt(min, max + 1));

                // Key is created by appending the Table Name and state separated by |
                key = "PACKET_METADATA_HINDI_ENROL";

                /*
                 * zadd method adds saves the data as a Sorted Set.
                 * Score is mandatory for a sorted set and Redis uses the score to sort multiple
                 * values for the same key. In this example, I've used the Double representation
                 * of Effective Date in yyyyMMdd format as the score. This ensures that when a
                 * query is execute with the effective date that falls between two entries in the
                 * database, Redis returns the correct record. For more details, read
                 * @see https://redis.io/commands/zadd,
                 * @see https://github.com/xetorthio/jedis/blob/master/src/main/java/redis/clients/jedis/Jedis.java
                 */
                returnCode = jedis.zadd(key, Double.valueOf(metadata.getAge()), metadata.getRefID() );
                logger.info("Data Inserted/Updated Successfully for "+ metadata.getRefID());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        if(returnCode == 1) {
            returnMsg = "Data Inserted";
        } else if (returnCode == 0) {
            returnMsg = "Data Updated";
        } else {
            returnMsg = "failure";
        }
        return returnMsg;
    }
}
