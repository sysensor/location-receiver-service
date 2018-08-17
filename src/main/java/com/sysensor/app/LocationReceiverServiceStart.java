package com.sysensor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class LocationReceiverServiceStart {
    public static Logger LOG = LoggerFactory.getLogger(LocationReceiverServiceStart.class.getClass());


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LocationReceiverServiceStart.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            LOG.info(beanName);
        }
    }
}
