package com.adroit.datacollector.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Scope("prototype")
public class MessagePublishActor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	@Autowired
	JedisPool jedisPool;

	@Override
	public void preStart() throws Exception {

	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, s -> {

			log.info("received ticker message from data collector: "+s);
			Jedis redisClient = jedisPool.getResource();

			redisClient.publish("pubsub:ticker", s);
			
			jedisPool.returnResource(redisClient);

		}).matchAny(o -> {
			log.info("received other message");

		}).build();
	}

}
