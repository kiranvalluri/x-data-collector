package com.adroit.datacollector.actors;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adroit.datacollector.extension.DataCollectorExtension;
import com.adroit.datacollector.vo.AppUtil;
import com.adroit.datacollector.vo.ExchangeConfig;
import com.adroit.datacollector.vo.ExchangeConfiguration;
import com.adroit.datacollector.vo.ServiceConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Scheduler;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

@Component
@Scope("prototype")
public class DataCollectorRepositoryActor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	Scheduler scheduler = null;

	@Autowired
	private DataCollectorExtension dataCollectorExtension;

	@Override
	public void preStart() throws Exception {
		scheduler = getContext().getSystem().scheduler();
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, s -> {			

			if ("Start".equalsIgnoreCase(s)) {
				log.info("Data Collecting is Started: {}", s);
				try {

					InputStream configurationStream = this.getClass().getResourceAsStream("/exchangeConfiguration.json");

					ObjectMapper objectMapper = new ObjectMapper();
					ExchangeConfiguration exchangeConfiguration = objectMapper.readValue(configurationStream,
							ExchangeConfiguration.class);					
					if (null != exchangeConfiguration && exchangeConfiguration.getExchange() != null
							&& exchangeConfiguration.getExchange().size() > 0) {

						for (ExchangeConfig config : exchangeConfiguration.getExchange()) {
							if (null != config && AppUtil.isValidString(config.getExchangeName())
									&& null != config.getService()) {
								for (ServiceConfiguration service : config.getService()) {
									if (AppUtil.isValidString(service.getExchangeName())
											&& AppUtil.isValidString(service.getName())
											&& AppUtil.isValidString(service.getApiType())
											&& AppUtil.isValidString(service.getUrl())
											&& AppUtil.isValidString(service.getProductSource())
											&& AppUtil.isValidString(service.getProductTarget())
											&& AppUtil.isValidString(service.getServiceType())
											&& service.getTimeToRefresh() > 0) {

//										ActorRef actorRef = getContext().getSystem().actorOf(dataCollectorExtension
//												.props("dataCollector", "dataCollector" + service.getName()));
										ActorRef actorRef = getContext().getSystem().actorOf(dataCollectorExtension
												.props("dataCollector"));

										scheduler.schedule(Duration.Zero(),
												Duration.create(service.getTimeToRefresh(), TimeUnit.SECONDS), actorRef,
												service, getContext().getSystem().dispatcher(), null);
									}else{
										log.info("Invalid Service Configuration:"+service);
									}
								}
							}
						}
					}else{
						log.info("Configuration is not loaded properly:"+exchangeConfiguration);
					}
				} catch (Exception exception) {
					 exception.printStackTrace();
					log.info("Exception raised while loading the configuration." + exception.getMessage());
				}
			}
		}).matchAny(o -> {
			log.info("received other message in data collector repository");
		}).build();
	}
}
