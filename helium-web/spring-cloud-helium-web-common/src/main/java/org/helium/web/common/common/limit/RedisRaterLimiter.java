package org.helium.web.common.common.limit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangiegie@gmail.com https://gitee.com/boding1/pig-cloud
 */
@Component

public class RedisRaterLimiter {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final String BUCKET = "BUCKET:";
    private static final String BUCKET_COUNT = "BUCKET_COUNT:";
    private static final String BUCKET_MONITOR = "BUCKET_MONITOR:";

    private static final Logger log = LoggerFactory.getLogger(RedisRaterLimiter.class);

    public String acquireTokenFromBucket(String point, int limit, long timeout) {

        try {
            //UUID令牌
            String token = UUID.randomUUID().toString();
            //TODO 待添加限流
            return token;
        } catch (Exception e) {
            log.error("限流出错，请检查Redis运行状态\n" + e.toString());
        } finally {

        }
        return null;
    }
}

