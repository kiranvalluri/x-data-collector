package com.adroit.datacollector.actors;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adroit.datacollector.extension.DataCollectorExtension;
import com.adroit.datacollector.vo.ExchangeConfig;
import com.adroit.datacollector.vo.ServiceConfiguration;
import com.adroit.datacollector.vo.TickerMessageEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Scheduler;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.util.ByteString;

@Component
@Scope("prototype")
public class DataCollector extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	Scheduler scheduler = null;
	ActorMaterializer materializer = null;
	
	@Autowired
	private DataCollectorExtension dataCollectorExtension;
	
//	@Autowired
//	MessagePublisher messagePublisher;
	
	@Override
	public void preStart() throws Exception {
		scheduler = getContext().getSystem().scheduler();
		materializer = ActorMaterializer.create(getContext().getSystem());
	}

	// Receives Exchange Configuration from Supervisor to schedule the data collection
	// Once the scheduler is started, it posts the service configuration to fetch the response.
	// This method handles both the messages.
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, s -> {
			log.info("Data Collecting is Started: {}", s);
		}).match(ServiceConfiguration.class, config -> {
			log.info("Received message from Data Collector to fetch Ticker data: {}", config);
			
			// TODO Based on the type of the service call either handleRestApi or handleWebHookApi
			handleRestApi(config);

		}).matchAny(o -> {
			log.info("received other message");

		}).build();
	}

	// Call the REST service and post the response to Redis
	private void handleRestApi(ServiceConfiguration config) {
		try {
			log.info("Calling Ticker API for the exchange: "+config);
			
			CompletableFuture<HttpResponse> response = (CompletableFuture<HttpResponse>) Http
					.get(getContext().getSystem())
					.singleRequest(HttpRequest.create(config.getUrl()), materializer);
			HttpResponse httpResponse = response.get();
			HttpEntity.Strict result = httpResponse.entity().toStrict(1000, materializer).toCompletableFuture().get();

			ByteString byteString = result.getData();
			log.info(byteString.utf8String());
			ActorRef messagePublishActor = getContext().getSystem().actorOf(dataCollectorExtension.props("messagePublishActor"));
			TickerMessageEnvelope envelope = new TickerMessageEnvelope();
			
			envelope.setMessage(byteString.utf8String());
			envelope.setExchangeName(config.getExchangeName());
			envelope.setSourceProduct(config.getProductSource());
			envelope.setTargetProduct(config.getProductTarget());
			
			ObjectMapper mapper = new ObjectMapper();			
			
			messagePublishActor.tell(mapper.writeValueAsString(envelope), sender());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Handles the web hooks
	private void handleWebHookApi(ExchangeConfig config){
		// TODO implementation is pending
	}
}
