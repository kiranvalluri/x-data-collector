package com.adroit.datacollector.extension;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.adroit.datacollector.actors.DataCollectorProducer;

import akka.actor.Extension;
import akka.actor.Props;

@Component
public class DataCollectorExtension implements Extension {

    private ApplicationContext applicationContext;

    /**
     * Used to initialize the Application context for the extension.
     */
    public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Create a Props for the specified actorBeanName using the
     * DataCollectorExtension class.
     */
    public Props props(String actorBeanName) {
    	Props pros = Props.create(DataCollectorProducer.class,applicationContext,actorBeanName);       
    	return (pros);
    }
    
    public Props props(String actorBeanClass,String actorBeanName) {
    	Class<?> cla = DataCollectorProducer.class;
    			try {
    				cla = Class.forName(actorBeanClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	Props pros = Props.create(cla,applicationContext,actorBeanClass);       
    	return (pros);
    }
}