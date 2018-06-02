package com.school.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.Tuple;

import com.alibaba.fastjson.JSON;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.utils.SerializeUtil;

/**
 * redis操作类
 */
@Component
public class RedisClientTemplate {

	private static Logger logger = Logger.getLogger(RedisClientTemplate.class);

	@Value("${redis.cache.life.switch}")
	private Boolean redisCacheLifeSwitch;

	@Value("${redis.cache.life.time}")
	private int redisCacheLifeTime;

	@Resource
	private RedisDataSource redisDataSource;

	public void disconnect() {
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		shardedJedis.disconnect();
	}

	
	public ShardedJedisPipeline pipeline() {
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        return shardedJedis.pipelined();
	}

	   
	/**
	 * 设置单个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			if (shardedJedis.exists(key)) {
				shardedJedis.del(key);
			}
			result = shardedJedis.set(key, value);
			if (redisCacheLifeSwitch) {
				Long originExpireTime = this.ttl(key);
				if(originExpireTime == null || originExpireTime < 0 || (originExpireTime > redisCacheLifeTime)){
					shardedJedis.expire(key, redisCacheLifeTime);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	/**
	 * 设置or覆盖集合的值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public String hset(byte[] key, byte[] field, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			shardedJedis.hset(key, field, value);
			if (redisCacheLifeSwitch) {
				Long originExpireTime = this.ttl(key);
				if(originExpireTime == null || originExpireTime < 0 || (originExpireTime > redisCacheLifeTime)){
					shardedJedis.expire(key, redisCacheLifeTime);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	public Object hget(byte[] key, byte[] field) {
		Object result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			byte[] value = shardedJedis.hget(key, field);
			if (value != null) {
				return SerializeUtil.unserialize(value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	public String hdel(byte[] key, byte[] field) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			shardedJedis.hdel(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	/**
	 * 获取单个值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	/**
	 * 删除缓存
	 */
	public boolean delete(String key) {
		return delete(key.getBytes());
	}

	/**
	 * 删除缓存
	 */
	public boolean delete(byte[] key) {
		boolean broken = false;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return false;
		}
		try {
			String keyStr = new String(key);
			if (shardedJedis.exists(key)) {
				shardedJedis.del(key);
			} else if (shardedJedis.exists(keyStr)) {
				shardedJedis.del(keyStr);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return false;
	}

	/**
	 * 获取单个对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(byte[] key) {
		Object bak = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return null;
		}
		boolean broken = false;
		try {
			if (shardedJedis.exists(key)) {
				byte[] result = shardedJedis.get(key);
				bak = SerializeUtil.unserialize(result);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return bak;
	}

	public boolean set(byte[] key, byte[] value) {
		boolean result = false;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (redisDataSource == null) {
			return result;
		}
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			if (shardedJedis.exists(key)) {
				shardedJedis.del(key);
			}
			shardedJedis.set(key, value);
			if (redisCacheLifeSwitch) {
				Long originExpireTime = this.ttl(key);
				if(originExpireTime == null || originExpireTime < 0 || (originExpireTime > redisCacheLifeTime)){
					shardedJedis.expire(key, redisCacheLifeTime);
				}
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	public Boolean exists(String key) {
		Boolean result = false;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.exists(key);
			if (!result) {
				result = shardedJedis.exists(key.getBytes());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	public String type(String key) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.type(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	/**
	 * 在某段时间后失效
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Long expire(String key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * 在某段时间后失效
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Long expire(byte[] key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}


	/**
	 * 在某个时间点失效
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Long expireAt(String key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.expireAt(key, unixTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * 在某个时间点失效
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public Long expireAt(byte[] key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.expireAt(key, unixTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	/**
	 * 获取指定key的剩余生命时间（单位：秒）
	 */
	public Long ttl(String key) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.ttl(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}

	public Long ttl(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.ttl(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	
	/**
	 * ZADD 将一个元素及其 score值加入到有序集 key当中
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(byte[] key, double score, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * ZREM 移除有序集 key中的一个或多个成员，不存在的成员将被忽略
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrem(byte[] key, byte[]... members) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zrem(key, members);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * ZCARD 返回有序集 key 的基数
	 * 
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zcard(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * ZCOUNT 返回有序集 key中， score值在min和 max之间(默认包括 score值等于min或 max)的成员的数量
	 * 
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public Long zcount(String key, double min, double max) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zcount(key, min, max);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZINCRBY 为有序集key的成员member的score值加上增量increment,以通过传递一个负数值 increment让score减去相应的值
	 * 
	 * @param key
	 * @param increment 
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double increment, String member) {
		Double result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zincrby(key, increment, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * ZRANGE 返回有序集key中，指定区间内的成员,其中成员的位置按 score 值递增(从小到大)来排序,具有相同 score 值的成员按字典序(lexicographical order)来排列
	 * 
	 * @param key
	 * @param start 
	 * @param stop
	 * @param WITHSCORES 可选参数
	 * @return
	 */
	public Set<Object> zrange(String key, long start, long end) {
		Set<Object> result = new LinkedHashSet<Object>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			Jedis jedis = shardedJedis.getShard(key);
			Set<byte[]> byteSet = jedis.zrange(key.getBytes(), start, end);
			for (byte[] bs : byteSet) {
				result.add(SerializeUtil.unserialize(bs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZREVRANGE 返回有序集key中，指定区间内的成员,其中成员的位置按 score 值递减(从大到小)来排序,具有相同 score 值的成员按字典序(lexicographical order)来排列
	 * 
	 * @param key
	 * @param start 
	 * @param stop
	 * @param WITHSCORES 可选参数
	 * @return
	 */
	public Set<Object> zrevrange(String key, long start , long end) {
		Set<Object> result = new LinkedHashSet<Object>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			Jedis jedis = shardedJedis.getShard(key);
			Set<byte[]> byteSet = jedis.zrevrange(key.getBytes(), start, end);
			for (byte[] bs : byteSet) {
				result.add(SerializeUtil.unserialize(bs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZRANK 返回有序集 key中成员 member的排名。其中有序集成员按 score值递增(从小到大)顺序排列
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zrank(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZREVRANK 返回有序集 key中成员 member的排名。其中有序集成员按 score值递减(从大到小)排序
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zrevrank(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZSCORE 返回有序集 key中，成员member的score值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(byte[] key, byte[] member) {
		Double result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			result = shardedJedis.zscore(key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;
	}
	
	/**
	 * ZRANGEBYSCORE 返回有序集key中，所有score值介于 min和 max之间(包括等于min或max)的成员。有序集成员按score值递增(从小到大)次序排列
	 * 
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public Set<Object> zrangeByScore(byte[] key, double min, double max) {
		Set<Object> result = new LinkedHashSet<Object>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			
			Set<byte[]> byteSet = shardedJedis.zrangeByScore(key, min, max);
			for (byte[] bs : byteSet) {
				result.add(SerializeUtil.unserialize(bs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZRANGEBYSCORE 返回有序集key中，所有score值介于 min和 max之间(包括等于min或max)的成员。有序集成员按score值递增(从小到大)次序排列
	 * 
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public Set<Object> zrangeByScoreWithLimit(byte[] key, double min, double max, int offset, int count) {
		Set<Object> result = new LinkedHashSet<Object>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			
			Set<byte[]> byteSet = shardedJedis.zrangeByScore(key, min, max, offset, count);
			for (byte[] bs : byteSet) {
				result.add(SerializeUtil.unserialize(bs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZRANGEBYSCORE WITHSCORES 返回有序集key中，所有score值介于 min和 max之间(包括等于min或max)的成员。有序集成员按score值递增(从小到大)次序排列
	 * WITHSCORES 参数决定结果集将有序集成员及其 score值一起返回
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public Map<Double, Object> zrangeByScoreWithScore(byte[] key, double min, double max) {
		Map<Double, Object> result = new LinkedHashMap<Double, Object>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			
			Set<Tuple> byteSet = shardedJedis.zrangeByScoreWithScores(key, min, max);
			for (Tuple bs : byteSet) {
				result.put(bs.getScore(), SerializeUtil.unserialize(bs.getBinaryElement()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	/**
	 * ZREMRANGEBYSCORE 移除有序集key中，所有 score值介于min和max之间(包括等 min或max)的成员
	 * 
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public void zremrangeByScore(byte[] key, double min, double max) {
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return;
		}
		boolean broken = false;
		try {
			shardedJedis.zremrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
	}
	
	/**
	 * ZREMRANGEBYSCORE 移除有序集key中，所有 score值介于min和max之间(包括等 min或max)的成员
	 * 
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public void zremrangeByRank(byte[] key, long start, long end) {
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return;
		}
		boolean broken = false;
		try {
			shardedJedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
	}
	
	
	/**
	 * 取出时间Set上某一个时间点上的对象Map
	 * @param key
	 * @param timestamp
	 * */
	public Map<String, Object> getSortSetMap(String key, Long timestamp) {
		Map<String, Object> map = new HashMap<String, Object>();
		double tiemstamp = timestamp.doubleValue();
		Set<Object> result = this.zrangeByScore(key.getBytes(), tiemstamp, tiemstamp);
		if (result!=null&&!result.isEmpty()) {
			if (result.size()!=1) {
				throw new FuBusinessException("","时间点上存在多个Map");
			} else {
				for (Object obj : result) {
					map = (Map<String, Object>) obj;
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取时间SortSet上传入时间点之前最早的一个对象Map
	 * @param key
	 * @param timestamp
	 * */
	public Map<Double, Object> getSortSetFirstMap(String key, Long timestamp) {
		Map<Double, Object> firstMap = new HashMap<Double, Object>();
		Map<Double, Object> queryMap = this.zrangeByScoreWithScore(key.getBytes(), 0, timestamp.doubleValue());
		if (queryMap==null && queryMap.isEmpty()) {
			return firstMap;
		}
		for (Entry<Double, Object> entry : queryMap.entrySet()) {
			firstMap.put(entry.getKey(), entry.getValue());
			break;
		}
		return firstMap;
	}
	
	/**
	 * 获取时间SortSet上早于传入时间点的对象Map集合
	 * @param key
	 * @param timestamp
	 * */
	public Map<Double, Object> getSortSetMapBefore(String key, Long timestamp) {
		return this.zrangeByScoreWithScore(key.getBytes(), 0, timestamp.doubleValue());
	}
	
	/**
	 * 将一个通知对象Map集合放入到时间Set上的某一个时间点
	 * @param key
	 * @param timestamp
	 * @param map
	 * */
	public void addSortSet(String key, Long timestamp, Map<String, Object> map) {
		Map<String, Object> oldMap = getSortSetMap(key,timestamp);
		//合并map
		map.putAll(oldMap);
		this.zremrangeByScore(key.getBytes(), timestamp.doubleValue(), timestamp.doubleValue());
		this.zadd(key.getBytes(), timestamp.doubleValue(), SerializeUtil.serialize(map));
	}
	
	/**
	 * 移除时间Set上某一个时间点上的对象Map
	 * @param key
	 * @param timestamp
	 * */
	public void removeSortSetMap(String key, Long timestamp) {
		this.zremrangeByScore(key.getBytes(), timestamp.doubleValue(), timestamp.doubleValue());
	}
	
	/**
	 * 将SortSet上的某一个时间点的通知对象Map集合覆盖写
	 * @param key
	 * @param timestamp
	 * @param map
	 * */
	public void updateSortSetMap(String key, Long timestamp, Map<String, Object> map) {
		removeSortSetMap(key,timestamp);
		if (map.size()>0) {
			this.zadd(key.getBytes(), timestamp.doubleValue(), SerializeUtil.serialize(map));
		}
	}
	
	/**
	 * 在SortSet某个节点的Map集合中的放入一个元素(lua脚本原子性提交)
	 * @param key
	 * @param timestamp
	 * @param sortMapKey
	 * @param sortMapValue
	 * */
	public synchronized void addSortSetMapElementUseLua(String key, Long timestamp, String sortMapKey, Object sortMapValue) {
		Map<String, Object> oldMap = getSortSetMap(key,timestamp);
		oldMap.put(sortMapKey, sortMapValue);
		
		String script = "redis.call('zremrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"  
		              + "return redis.call('zadd', KEYS[1], KEYS[2], ARGV[1])";
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		boolean broken = false;
		try {
			Jedis jedis = shardedJedis.getShard(key);
			Integer keyCount = new Integer(2);
			jedis.eval(script.getBytes(),keyCount.toString().getBytes(),key.getBytes(),timestamp.toString().getBytes(),SerializeUtil.serialize(oldMap));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
	}
	
	/**
	 * 将SortSet某个节点的Map集合中的某一个元素删除(lua脚本原子性提交)
	 * @param key
	 * @param timestamp
	 * @param sortMapKey
	 * */
	public synchronized void removeSortSetMapElementUseLua(String key, Long timestamp, String sortMapKey) {
		//找到目标可能所在的时间块
		Map<String, Object> sortMap = getSortSetMap(key,timestamp);
		boolean removeFlag = false;
		for (Entry<String, Object> entry : sortMap.entrySet()) {
			if (sortMapKey!=null&&sortMapKey.equals(entry.getKey())) {
				sortMap.remove(sortMapKey);
				removeFlag = true;
				break;
			}
		}
		if (removeFlag) {
			String script = "";
			ShardedJedis shardedJedis = redisDataSource.getRedisClient();
			boolean broken = false;
			try {
				Jedis jedis = shardedJedis.getShard(key);
				if (sortMap.size()>0) {
					script = "redis.call('zremrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"  
				           + "return redis.call('zadd', KEYS[1], KEYS[2], ARGV[1])";
					Integer keyCount = new Integer(2);
					jedis.eval(script.getBytes(),keyCount.toString().getBytes(),key.getBytes(),timestamp.toString().getBytes(),SerializeUtil.serialize(sortMap));
				} else {
					script = "return redis.call('zremrangebyscore', KEYS[1], KEYS[2], KEYS[2])";
					jedis.eval(script,2,key,timestamp.toString());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				broken = true;
			} finally {
				redisDataSource.returnResource(shardedJedis, broken);
			}
		}
	}
	
	/**
	 * 在SortSet某个节点的Map集合中的放入一个元素(lua脚本原子性提交,采用JSON序列化)
	 * @param key
	 * @param timestamp
	 * @param sortMapKey
	 * @param sortMapValue
	 * */
	public void addSortSetMapElementUseLuaJSONSerialize(String key, Long timestamp, String sortMapKey, Object sortMapValue) {
		String script = "local result = redis.call('zrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"
					  + "local new = ARGV[2];\n"
					  + "if #result ~= 0 then\n"
					  + "local data = cjson.decode(result[1]);\n"
					  + "data[ARGV[1]] = cjson.decode(new);\n"
					  + "redis.call('zremrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"  
					  + "return redis.call('zadd',KEYS[1], KEYS[2], cjson.encode(data));\n"
					  + "else\n"
					  + "local map = {};\n"
					  + "map[ARGV[1]] = cjson.decode(new);\n"
					  + "return redis.call('zadd',KEYS[1], KEYS[2], cjson.encode(map));\n"
					  + "end\n";
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		boolean broken = false;
		try {
			Jedis jedis = shardedJedis.getShard(key);
			Object obj = jedis.eval(script, 2, key,timestamp.toString(),sortMapKey,JSON.toJSONString(sortMapValue));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
	}
	
	/**
	 * 将SortSet某个节点的Map集合中的某一个元素删除(lua脚本原子性提交,采用JSON序列化)
	 * @param key
	 * @param timestamp
	 * @param sortMapKey
	 * */
	public void removeSortSetMapElementUseLuaJSONSerialize(String key, Long timestamp, String sortMapKey) {
		String script = "local result = redis.call('zrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"
					  + "local remKey = ARGV[1];\n"
					  + "if #result ~= 0 then\n"
					  + "local data = cjson.decode(result[1]);\n"
					  + " if data[remKey]== nil then\n"
					  + "  return '0';\n"
					  + " else\n"
					  + "  data[remKey] = nil;\n"
					  + "  if cjson.encode(data) == '{}' then\n"
					  + "    return redis.call('zremrangebyscore', KEYS[1], KEYS[2], KEYS[2])"
					  + "  else\n"
					  + "    redis.call('zremrangebyscore',KEYS[1],KEYS[2],KEYS[2]);\n"  
					  + "    return redis.call('zadd',KEYS[1], KEYS[2], cjson.encode(data));\n"
					  + "  end\n"
					  + " end\n"
					  + "end\n";
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		boolean broken = false;
		try {
			Jedis jedis = shardedJedis.getShard(key);
			Object obj = jedis.eval(script, 2, key,timestamp.toString(),sortMapKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
	}
	
	/**
	 * ZRANGEBYSCORE WITHSCORES 返回有序集key中，所有score值介于 min和 max之间(包括等于min或max)的成员。有序集成员按score值递增(从小到大)次序排列
	 * WITHSCORES 参数决定结果集将有序集成员及其 score值一起返回
	 * @param key
	 * @param min 
	 * @param max
	 * @return
	 */
	public Map<Double, String> zrangeByScoreWithScoreJSONSerialize(byte[] key, double min, double max) {
		Map<Double, String> result = new LinkedHashMap<Double, String>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		boolean broken = false;
		try {
			Set<Tuple> byteSet = shardedJedis.zrangeByScoreWithScores(key, min, max);
			for (Tuple bs : byteSet) {
				result.put(bs.getScore(), bs.getElement());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return result;		
	}
	
	//非map形式
	/**
	 * 在SortSet上放入一个元素
	 * @param key
	 * @param score
	 * @param member
	 * */
	public void addSortSetElement(String key, Double score, byte[] member) {
		this.zadd(key.getBytes(), score.doubleValue(), member);
	}
	
	/**
	 * 将SortSet上某一个元素删除
	 * @param key
	 * @param member
	 * */
	public void removeSortSetElement(String key, byte[] member) {
		this.zrem(key.getBytes(), member);
	}
	
	/**
	 * 将SortSet上某排名rank区间中的元素删除
	 * @param key
	 * @param start
	 * @param end
	 * */
	public void removeSortSetElementsByRank(String key, Long start, Long end) {
		this.zremrangeByRank(key.getBytes(), start, end);
	}
	
	/**
	 * 将SortSet上某分值区间中的元素删除
	 * @param key
	 * @param min
	 * @param max
	 * */
	public void removeSortSetElementsByScore(String key, Double min, Double max) {
		this.zremrangeByScore(key.getBytes(), min.doubleValue(), max.doubleValue());
	}
	
	/**
	 * 获取时间SortSet上早于传入时间点的元素集合，通过limit offset count筛选元素个数
	 * @param key
	 * @param score
	 * @param offset
	 * @param count
	 * */
	public Set<Object> getSortSetElementsByScore(String key, Double score, Integer offset, Integer count) {
		return this.zrangeByScoreWithLimit(key.getBytes(), 0, score.doubleValue(),offset.intValue(),count.intValue());
	}
	
	/**
	 * 获取时间SortSet上早于传入时间点的元素集合
	 * @param key
	 * @param start
	 * @param end
	 * */
	public Set<Object> getSortSetElementsByRank(String key, Long start, Long end) {
		return this.zrange(key, start, end);
	}
	
	/**
	 * 查找所有符合给定模式pattern的 key
	 * @param pattern 示例：KEYS * 匹配redis shards中所有 key 
	 * */
	public Set<String> getKeys(String pattern) {
		Set<String> keys = new HashSet<String>();
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return keys;
		}
		boolean broken = false;
		try {
			Collection<Jedis> col = shardedJedis.getAllShards();
			for (Iterator iterator = col.iterator(); iterator.hasNext();) {
				Jedis jedis = (Jedis) iterator.next();
				keys.addAll(jedis.keys(pattern));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			broken = true;
		} finally {
			redisDataSource.returnResource(shardedJedis, broken);
		}
		return keys;
	}
	
	   public Long lpush(String key, String []data) {
	       Long result = null;
	        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	        if (shardedJedis == null) {
	            return result;
	        }
	        boolean broken = false;
	        try {
	            result = shardedJedis.lpush(key, data);
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            broken = true;
	        } finally {
	            redisDataSource.returnResource(shardedJedis, broken);
	        }
	        return result;
	    }
	   
       public String rpop(String key) {
           String result = null;
            ShardedJedis shardedJedis = redisDataSource.getRedisClient();
            if (shardedJedis == null) {
                return result;
            }
            boolean broken = false;
            try {
                result = shardedJedis.rpop(key);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                broken = true;
            } finally {
                redisDataSource.returnResource(shardedJedis, broken);
            }
            return result;
        }
       
       public boolean setbit(String key, long offset,String value) {
           boolean result = false;
            ShardedJedis shardedJedis = redisDataSource.getRedisClient();
            if (shardedJedis == null) {
                return result;
            }
            boolean broken = false;
            try {
                result = shardedJedis.setbit(key, offset, value);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                broken = true;
            } finally {
                redisDataSource.returnResource(shardedJedis, broken);
            }
            return result;
        }
       
       public Long hlen(String key) {
           Long result = null;
            ShardedJedis shardedJedis = redisDataSource.getRedisClient();
            if (shardedJedis == null) {
                return result;
            }
            boolean broken = false;
            try {
                result = shardedJedis.hlen(key);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                broken = true;
            } finally {
                redisDataSource.returnResource(shardedJedis, broken);
            }
            return result;
        }
       
       
       public Response<Long> pipeLineHset(byte[] key, byte[] field, byte[] value, ShardedJedisPipeline pipeline){
           ShardedJedis shardedJedis = redisDataSource.getRedisClient();
           Response<Long> resulst = null;
           boolean broken = false;
           try {
               resulst =  pipeline.hset(key, field, value);
           } catch (Exception e) {
            logger.error(e.getMessage(), e);
            broken = true;
           }
           finally {
               redisDataSource.returnResource(shardedJedis, broken);
           }
        return resulst;
       }
       
       public List syncAndReturnAll(ShardedJedisPipeline pipeline){

           ShardedJedis shardedJedis = redisDataSource.getRedisClient();
           List resulst = null;
           boolean broken = false;
           try {
               resulst =  pipeline.syncAndReturnAll();
           } catch (Exception e) {
            logger.error(e.getMessage(), e);
            broken = true;
           }
           finally {
               redisDataSource.returnResource(shardedJedis, broken);
           }
        return resulst;
       
       }
}