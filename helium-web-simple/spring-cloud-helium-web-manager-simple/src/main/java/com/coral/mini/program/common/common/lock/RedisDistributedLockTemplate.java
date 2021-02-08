package com.coral.mini.program.common.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 */
@Slf4j
@Component
public class RedisDistributedLockTemplate implements DistributedLockTemplate {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public Object execute(String lockId, Integer timeout, Callback callback) {

        RedisReentrantLock distributedReentrantLock = null;
        boolean getLock=false;
        try {
            ;
            distributedReentrantLock = new RedisReentrantLock(redisTemplate,lockId);
            if(distributedReentrantLock.tryLock(new Long(timeout), TimeUnit.MILLISECONDS)){
                getLock=true;
                return callback.onGetLock();
            }else{
                return callback.onTimeout();
            }
        }catch(InterruptedException ex){
            log.error(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            if(getLock) {
                distributedReentrantLock.unlock();
            }
        }
        return null;
    }
}
