package com.wiki_xml_consumption.wiki_xml.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wiki_xml_consumption.wiki_xml.models.MusicCruxArtist;

public interface MusicCruxArtistRepository extends MongoRepository<MusicCruxArtist, String> {

}
