package com.musiccrux.discogs_xml_consumption.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.musiccrux.discogs_xml_consumption.models.Artist;

public interface ArtistRepository extends MongoRepository<Artist, String> {
	public Artist findByName(String name);

	// This query checks if an Artist has any members.
	// @Query("{ 'members' : {$exists:true} }")
	// public List<Artist> findAllArtistsWithMembers();
}