package com.wiki_xml_consumption.wiki_xml.mongo;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
public abstract class MongoConfig {

	//DB connection properties
	private String host, db;
	private int port;
	
	
	public MongoConfig() {
		
	}
	
	public MongoConfig(String host, String db, int port) {
		super();
		this.host = host;
		this.db = db;
		this.port = port;
	}

	/**
	 * Creates MongoDbFactory Common to the MongoDb connections
	 */
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(getMongoClient(), db);
	}

	/**
	 * Creates MongoClient
	 */
	private MongoClient getMongoClient() {
		return new MongoClient(host, port);
	}

	/**
	 * Factory method to create the MongoTemplate
	 */
	abstract public MongoTemplate getMongoTemplate();

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return db;
	}

	public void setDatabase(String database) {
		this.db = database;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
