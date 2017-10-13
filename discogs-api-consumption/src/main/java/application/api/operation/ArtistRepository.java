package application.api.operation;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import application.api.model.artist.Artist;

public interface ArtistRepository extends MongoRepository<Artist, String> {
	public Artist findByName(String name);
	public void deleteAll();
}