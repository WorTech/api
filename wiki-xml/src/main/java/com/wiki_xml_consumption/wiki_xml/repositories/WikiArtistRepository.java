package com.wiki_xml_consumption.wiki_xml.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wiki_xml_consumption.wiki_xml.models.Artist;

public interface WikiArtistRepository extends MongoRepository<Artist, String> {

}
