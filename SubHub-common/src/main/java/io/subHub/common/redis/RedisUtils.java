

package io.subHub.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils
 *
 * @author By
 */
@Component
public class RedisUtils {
    @Autowired
    private  RedisTemplate<String, Object> redisTemplate;

    /**The expiration time is 5 days and hours, in seconds*/
    public final static long FIVE_DAYS_EXPIRE = 60 * 60 * 24 * 5L;
    /**The default expiration time is 24 hours, in seconds*/
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**The expiration time is 12 hours, in seconds*/
    public final static long HOUR_TWELVE_EXPIRE = 60 * 60 * 12L;
    /**The expiration time is 6 hours, in seconds*/
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**The expiration time is 1 hour, in seconds*/
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**The expiration time is 30 minute, in seconds*/
    public final static long MIN_THIRTY_EXPIRE = 60 * 30;
    /**The expiration time is 10 minute, in seconds*/
    public final static long MIN_TEN_EXPIRE = 60 * 10;
    /**The expiration time is 5 minute, in seconds*/
    public final static long MIN_FIVE_EXPIRE = 60 * 5;

    /**Do not set expiration time*/
    public final static long NOT_EXPIRE = -1L;

    public void set(String key, Object value, long expire){
        redisTemplate.opsForValue().set(key, value);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public void set(String key, Object value){
        set(key, value, NOT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
        return value;
    }

    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key){
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public Set<String> getHashKeys(String hashKey) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.keys(hashKey);
    }

    public void hMSet(String key, Map<String, Object> map){
        hMSet(key, map, NOT_EXPIRE);
    }

    public void hMSet(String key, Map<String, Object> map, long expire){
        redisTemplate.opsForHash().putAll(key, map);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, NOT_EXPIRE);
    }

    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public void expire(String key, long expire){
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hDel(String key, Object... fields){
        redisTemplate.opsForHash().delete(key, fields);
    }

    public void leftPush(String key, Object value){
        leftPush(key, value, NOT_EXPIRE);
    }

    public void leftPush(String key, Object value, long expire){
        redisTemplate.opsForList().leftPush(key, value);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }
    public Long incrementCounter(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long incrementCounter(String key,long expire){
        long incr = redisTemplate.opsForValue().increment(key);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
        return incr;
    }

    public Long hIncrementCounter(String key,String field) {
        return redisTemplate.opsForHash().increment(key,field,1);
    }

    public Long hIncrementCounter(String key, String field,long expire){
        long incr = redisTemplate.opsForHash().increment(key,field,1);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
        return incr;
    }


    public Object rightPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }
}
