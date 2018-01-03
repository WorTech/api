//package com.wiki_xml_consumption.wiki_xml.mongo;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories(
//		basePackages = {"com.wiki_xml_consuption.wiki_xml.repositories" }, 
//		mongoTemplateRef = "musiccruxMongoTemplate")
//@ConfigurationProperties(prefix = "musiccrux.mongodb")
//public class MusicCruxDBConnection extends MongoConfig{
//
//	@Primary
//	@Override
//	@Bean(name = "musiccruxMongoTemplate")
//	public MongoTemplate getMongoTemplate() {
//		return new MongoTemplate(mongoDbFactory());
//	}
//	
//}
