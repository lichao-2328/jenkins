package Util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
	private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /** 保存数据并设置过期时间 */
    public void set(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    /** 获取数据 */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /** 删除键 */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /** 判断键是否存在 */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
