//package com.wiki_xml_consumption.wiki_xml.mongo;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories(
//		basePackages = {"com.wiki_xml_consuption.wiki_xml.repositories" }, 
//		mongoTemplateRef = "wikiMongoTemplate")
//@ConfigurationProperties(prefix = "wiki.mongodb")
//public class WikiDBConnection extends MongoConfig {
//	@Override
//	@Bean(name = "wikiMongoTemplate")
//	public MongoTemplate getMongoTemplate() {
//		return new MongoTemplate(mongoDbFactory());
//	}
//}
