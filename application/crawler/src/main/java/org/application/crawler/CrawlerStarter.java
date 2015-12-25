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
public class CrawlerStarter {
    private static final Logger log = LoggerFactory.getLogger(CrawlerStarter.class);

    public static void main(String[] args) throws IOException {
        System.setProperty("socksProxyHost", "tor");
        System.setProperty("socksProxyPort", "9050");

        ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder(CrawlerStarter.class).web(false).run(args);
        configurableApplicationContext.start();

        Path crawlDB = (Path) configurableApplicationContext.getBean("crawlDB");
        Path segmentsDir = (Path) configurableApplicationContext.getBean("segmentsDir");
        Path urlDir = (Path) configurableApplicationContext.getBean("urlDir");

        Injector injector = configurableApplicationContext.getBean(Injector.class);
        Generator generator = configurableApplicationContext.getBean(Generator.class);
        Fetcher fetcher = configurableApplicationContext.getBean(Fetcher.class);
        ParseSegment parseSegment = configurableApplicationContext.getBean(ParseSegment.class);
        CrawlDb crawlDb = configurableApplicationContext.getBean(CrawlDb.class);

        // fetch
        long segmentTime = new Date().getTime();
        injector.inject(crawlDB, urlDir);

        boolean stop = false;

        int numberOfReduceTasks = 4;
        int numberOfPagesInrun = 100;
        int numberOfCores = 8;

        while(!stop) {
            Path[] paths = generator.generate(crawlDB, segmentsDir, numberOfReduceTasks, numberOfPagesInrun, segmentTime);
            if(paths!=null && paths.length>0) {
                for (Path path : paths) {
                    fetcher.fetch(path, numberOfCores); // numthreads
                    parseSegment.parse(path);
                }
                crawlDb.update(crawlDB, paths, true, true);
            } else {
                stop = true;
            }
        }

    }
}
