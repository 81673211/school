package com.school.util.core;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * <b>Description:统一ID生成器.</b><br>
 * @author <b>fred</b>
 * <br><b>ClassName:IdGeneratorUtil</b>
 * <br><b>Date:</b> 8/22/17 10:13 AM
 */
@Component
public class IdGeneratorUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdGeneratorUtil.class);
    /**
     * ID自增步长.
     */
    private static final int DELTA = 1;

    private static final String ID_KEY_PREFIX = "ID:";//id在redis中Key的前缀
    private static final int MAX_SEQUENCE = 99;//一秒内最大序列值.
    private static final int SECONDS_EXPIRE = 10;//60秒过期时间

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private int extendValue;//一秒内扩展值，防止序列值不够用的情况

    private synchronized String generate(String module) {
        long timestamp = timeGen();
        String formatTimestamp = dateTimeFormat.format(new Timestamp(timestamp));
        String key = ID_KEY_PREFIX + formatTimestamp;
        Long sequence;
        if (redisTemplate.hasKey(key)) {
            sequence = redisTemplate.opsForValue().increment(key, DELTA);
            if (sequence > MAX_SEQUENCE) {
                LOGGER.warn("-------------------it's time to optimize the id generation " +
                            "strategy for high concurrence--------------");
                sequence = 1L;
                extendValue++;
                redisTemplate.opsForValue().set(key, sequence.toString(), SECONDS_EXPIRE, TimeUnit.SECONDS);
            }
        } else {
            sequence = 1L;
            extendValue = 0;
            redisTemplate.opsForValue().set(key, sequence.toString(), SECONDS_EXPIRE, TimeUnit.SECONDS);
        }
        return module + formatTimestamp + fillSequence(sequence) +
               random() + appendExtendValue(extendValue);
    }

    public String nextId(String module) {
        return generate(module);
    }

    private String appendExtendValue(int extendValue) {
        return extendValue != 0 ? String.valueOf(extendValue) : StringUtils.EMPTY;
    }

    private String fillSequence(Long sequence) {
        return sequence >= 10 ? String.valueOf(sequence) : "0" + sequence;
    }

    /**
     * 获得系统当前毫秒数.
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    private int random() {
        int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if (result == 0 && array[i] == 0) {
                result = 1;
                continue;
            }
            result = result * 10 + array[i];
        }
        return result;
    }

}
