package org.application.crawler.configuration.crawler;

import org.apache.hadoop.fs.Path;
import org.apache.nutch.crawl.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by marcel on 25-12-15.
 */
@Configuration
public class CrawlerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CrawlerConfiguration.class);

    /*
      CLASS=org.apache.nutch.crawl.Injector
      CLASS=org.apache.nutch.crawl.Generator
      CLASS=org.apache.nutch.tools.FreeGenerator
      CLASS=org.apache.nutch.fetcher.Fetcher
      CLASS=org.apache.nutch.parse.ParseSegment
      CLASS=org.apache.nutch.crawl.CrawlDbReader
      CLASS=org.apache.nutch.crawl.CrawlDbMerger
      CLASS=org.apache.nutch.crawl.LinkDbReader
      CLASS=org.apache.nutch.segment.SegmentReader
      CLASS=org.apache.nutch.segment.SegmentMerger
      CLASS=org.apache.nutch.crawl.CrawlDb
      CLASS=org.apache.nutch.crawl.LinkDb
      CLASS=org.apache.nutch.crawl.LinkDbMerger
      CLASS=org.apache.nutch.tools.FileDumper
      CLASS=org.apache.nutch.tools.CommonCrawlDataDumper
      CLASS="org.apache.nutch.indexer.IndexingJob -D solr.server.url=$1"
      CLASS=org.apache.nutch.indexer.IndexingJob
      CLASS=org.apache.nutch.crawl.DeduplicationJob
      CLASS="org.apache.nutch.indexer.CleaningJob -D solr.server.url=$2 $1"
      CLASS=org.apache.nutch.indexer.CleaningJob
      CLASS=org.apache.nutch.parse.ParserChecker
      CLASS=org.apache.nutch.indexer.IndexingFiltersChecker
      CLASS=org.apache.nutch.util.domain.DomainStatistics
      CLASS=org.apache.nutch.util.ProtocolStatusStatistics
      CLASS=org.apache.nutch.util.CrawlCompletionStats
      CLASS=org.apache.nutch.scoring.webgraph.WebGraph
      CLASS=org.apache.nutch.scoring.webgraph.LinkRank
      CLASS=org.apache.nutch.scoring.webgraph.ScoreUpdater
      CLASS=org.apache.nutch.scoring.webgraph.NodeDumper
      CLASS=org.apache.nutch.plugin.PluginRepository
      CLASS=org.apache.nutch.service.NutchServer
      CLASS=org.apache.nutch.webui.NutchUiServer
      CLASS=org.apache.nutch.tools.warc.WARCExporter
     */

    @Bean
    org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        return configuration;
    }

    @Bean
    Path crawlDB() {
        Path path = new Path("crawler/db");
        return path;
    }

    @Bean
    Path urlDir() {
        Path path = new Path("crawler/urls");
        return path;
    }

    @Bean
    Injector injector() throws IOException {
        Injector injector = new Injector();
        injector.inject(crawlDB(), urlDir());
        return injector;
    }
}
