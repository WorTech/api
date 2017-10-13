package application.api.operation;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import application.api.model.artist.Artist;
import application.api.model.artist.Member;
import application.api.model.search.artist.ArtistSearchPage;
import application.api.operation.GenerateArtistModels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This class pushes up the list of artists (created in the GenerateArtistModels class)
 * to your local mongoDB instance. You can view the tables in the MonjaDB window.
 * 
 * We initiliaze our ArtistRepository (which extends off of MongoRepository).
 * Then we create an instance of our GenerateArtistModels class and store that in
 * a list of artists. After, we iterate through this list and push each seperate entity
 * to our mongoDB instance.
 * 
 */



@Component
@Order(value = 3)
public class GenerateMongoModel implements CommandLineRunner {

	@Autowired
	private ArtistRepository db;
	@Autowired
	private GenerateArtistModels generateArtistModels;
	List<Artist> artistList = new ArrayList<Artist>();
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("######################## Garbage");
		artistList = generateArtistModels.getArtistModels();
		try {
			//generateArtistModels.printList();
			for(Artist artist : artistList) {
				System.out.println(artist);
				db.save(artist);
			}
		}catch(Exception e){
			System.out.println("@@@@@@@@@@@@@@@@@@@"+e);
		}
	}
}