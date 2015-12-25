package org.application.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CrawlerStarter {
    private static final Logger log = LoggerFactory.getLogger(CrawlerStarter.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder(CrawlerStarter.class).web(false).run(args);
        configurableApplicationContext.start();
    }
}
