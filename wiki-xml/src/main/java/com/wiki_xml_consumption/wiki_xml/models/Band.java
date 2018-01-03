package com.wiki_xml_consumption.wiki_xml.models;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Band {
	@Id
	private BigInteger id;
	
	@Indexed
	private String name;
	private List<String> genre;
	private List<String> labels;
	
	private List<String> associatedActs;
	
	@DBRef
	private List<Artist> members;
	
	
}
