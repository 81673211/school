package com.school.cache.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.school.cache.RedisDataSource;

@Repository
public class RedisDataSourceImpl implements RedisDataSource {

	private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

	@Resource
	private ShardedJedisPool shardedJedisPool;

	@Override
	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		} catch (Exception e) {
			log.error("getRedisClent error", e);
		}
		return null;
	}

	@Override
	public void returnResource(ShardedJedis shardedJedis) {
		try {
			shardedJedisPool.returnBrokenResource(shardedJedis);
		} catch (Exception e) {
			log.error("returnResource error", e);
		}
	}

	@Override
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
		try {
			if (broken) {
				shardedJedisPool.returnBrokenResource(shardedJedis);
			} else {
				shardedJedisPool.returnResource(shardedJedis);
			}
		} catch (Exception e) {
			log.error("returnResource error", e);
		}
	}
}
