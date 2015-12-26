package org.application.crawler;

import org.apache.hadoop.fs.Path;
import org.apache.nutch.crawl.CrawlDb;
import org.apache.nutch.crawl.Generator;
import org.apache.nutch.crawl.Injector;
import org.apache.nutch.fetcher.Fetcher;
import org.apache.nutch.parse.ParseSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
public class CrawlerInjectorStarter {
    private static final Logger log = LoggerFactory.getLogger(CrawlerInjectorStarter.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder(CrawlerInjectorStarter.class).web(false).run(args);
        configurableApplicationContext.start();

        Path crawlDB = (Path) configurableApplicationContext.getBean("crawlDB");
        Path urlDir = (Path) configurableApplicationContext.getBean("urlDir");
        Injector injector = configurableApplicationContext.getBean(Injector.class);
        injector.inject(crawlDB, urlDir);
    }
}
