package server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class GameSet{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	private long id;
		
	private String firstFilmName;
	private String firstFilmImageURL;
	private long rating;
	
	public final Long MIN_RATING = (long) 10;

	@ElementCollection
	private Set<String> usersWhoRatedGameSet;

	GameSet(){}
	
	public GameSet(String firstFilmName, String firstFilmImageURL, long rating) {
		super();
		this.firstFilmName = firstFilmName;
		this.firstFilmImageURL = firstFilmImageURL;
		this.rating = rating;
	}
	public void setFirstFilmName(String firstFilmName) {
		this.firstFilmName = firstFilmName;
	}
	public String getFirstFilmName() {
		return this.firstFilmName;
	}	
	public void setFirstFilmImageURL(String firstFilmImageURL) {
		this.firstFilmImageURL = firstFilmImageURL;
	}
	public String getFirstFilmImageURL() {
		return this.firstFilmImageURL;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}
	public long getRating() {
		return rating;
	}

	public void addUserWhoRatedGameSet(String name){
		if (usersWhoRatedGameSet == null) {
			usersWhoRatedGameSet = new HashSet<String>();
		}
		usersWhoRatedGameSet.add(name);
	}	
	public void removeUserWhoRatedGameSet(String name){
		if (usersWhoRatedGameSet != null)
			usersWhoRatedGameSet.remove(name);
	}	
	public Boolean userRatedGameSetAllready(String name){
		if (usersWhoRatedGameSet != null)
			return usersWhoRatedGameSet.contains(name);
		else 
			return false;
	}
	public void setUsersWhoRatedGameSet(Set<String> usersWhoRatedGameSet) {
		this.usersWhoRatedGameSet = usersWhoRatedGameSet;
	}
	public Collection<String> getUsersWhoRatedGameSet(){
		return usersWhoRatedGameSet;
	}
}
