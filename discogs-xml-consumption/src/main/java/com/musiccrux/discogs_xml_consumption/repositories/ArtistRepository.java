package com.musiccrux.discogs_xml_consumption.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musiccrux.discogs_xml_consumption.models.Artist;

public interface ArtistRepository  extends MongoRepository<Artist, String>{
	
}
