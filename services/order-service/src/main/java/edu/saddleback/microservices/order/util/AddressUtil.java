package edu.saddleback.microservices.order.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class AddressUtil {

    private static final String REDIS_PASSWORD = System.getenv("REDIS_PASSWORD");
    private static RedisClient redisClient = RedisClient.create("redis://" + REDIS_PASSWORD + "@redis-master:6379/0");
    private static StatefulRedisConnection<String, String> connection = redisClient.connect();
    private static RedisCommands<String, String> cmd = connection.sync();

    public static String getAddress(String coin) {
        String key = "receive-addresses:" + coin;
        return cmd.spop(key);
    }
}
