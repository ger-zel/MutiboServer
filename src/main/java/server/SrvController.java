package server;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SrvController {
	
	@Autowired
	private GameSetRepository gameSets;

	private static Map<String, Long> users = new HashMap<String, Long>();
	
	public static void addUser(String username) {
		users.put(username, (long) 0);
	}
	
	@RequestMapping(value=SrvAPI.GAME_REPOSITORY_CAPACITY, method = RequestMethod.GET)
	public @ResponseBody Long getRepoCapacity() {
		
		return gameSets.count();
	}

	@RequestMapping(value=SrvAPI.LIKE_GAME_SET, method = RequestMethod.POST)
	public @ResponseBody GameSet likeGameSet(@PathVariable("id") long id, Principal p, HttpServletResponse response) {

		String name = p.getName();
		
		GameSet s = gameSets.findOne(id);
		
		if (s == null) {
			response.setStatus(404);
			return null;
		}
		
		if (s.userRatedGameSetAllready(name)) {
			response.setStatus(400);
			return null;
		}
		
		long rating = s.getRating() + 1;
		s.setRating(rating);
		s.addUserWhoRatedGameSet(name);
		
		gameSets.save(s);
		
		return s;
	}
	
	@RequestMapping(value=SrvAPI.UNLIKE_GAME_SET, method = RequestMethod.POST)
	public @ResponseBody GameSet unlikeGameSet(@PathVariable("id") long id, Principal p, HttpServletResponse response) {

		String name = p.getName();
		
		GameSet s = gameSets.findOne(id);
		
		if (s == null) {
			response.setStatus(404);
			return null;
		}
		
		if (s.userRatedGameSetAllready(name)) {
			response.setStatus(400);
			return null;
		}
		
		long rating = s.getRating() - 1;
		
		if (rating < s.MIN_RATING) {
			gameSets.delete(id);
			response.setStatus(200);
			return null;
		}
		
		s.setRating(rating);
		s.addUserWhoRatedGameSet(name);
		
		gameSets.save(s);
		
		return s;
	}

	@RequestMapping(value=SrvAPI.USER_POINTS, method = RequestMethod.POST)
	public @ResponseBody Long updateUserPoints(@RequestBody Long pointsDiff, Principal p){

		String name = p.getName();
		
		if (!users.containsKey(name)) {
			users.put(name, pointsDiff);
			return pointsDiff;
		}
		
		Long currentPoints = (Long) users.get(name) + pointsDiff;
		
		users.put(name, currentPoints);
			
		return (Long) users.get(name);
	}
	
	@RequestMapping(value=SrvAPI.USER_POINTS, method = RequestMethod.GET)
	public @ResponseBody Long getUserPoints(Principal p){

		String name = p.getName();
		
		if (!users.containsKey(name)) {
			return (long) 0;
		}
		
		if (gameSets.count() == 0) {
			
			GameSet gameSet = new GameSet("American Pie",
										  "Mean Girls", 
										  "Titanic",
										  "The Breakfast Club",
										  (long) 3,
										  "By Genre: Titanic is not a High school movie.",
										  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("The Hunger Games",
								  "Breakfast at Tiffanies", 
								  "Slumdog Millionaire",
								  "Django Unchained",
								  (long) 2,
								  "By Age: While the others movies are quite recent, Breakfast at tiffanies is a very old movie.",
								  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("Taken",
					  			  "Run", 
					  			  "Leon: The Professional",
					  			  "The Transporter",
					  			  (long) 3,
					  			  "By Director: Leon: The Professional is not a movie by Luc Besson.",
					  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("Mad Max",
		  			  			  "The Year of Living Dangerously", 
		  			  			  "Braveheart",
		  			  			  "Revolutionary Road",
		  			  			  (long) 4,
		  			  			  "By Actor: Mel Gibson does not appear in Revolutionary Road.",
		  			  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("Unbreakable",
		  			  			  "The Sixth Sense", 
		  			  			  "The Village",
		  			  			  "12 Monkeys",
		  			  			  (long) 4,
		  			  			  "By Director: 12 Monkeys is not a movie by M. Night Shyamalan.",
		  			  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("Titanic",
		  			  			  "Gladiator", 
		  			  			  "American Beauty",
		  			  			  "King's Speech",
		  			  			  (long) 1,
		  			  			  "By Oscar Win: All the rest, won best movie and best Actor.",
		  			  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("München",
					  			  "The Last King of Scotland", 
					  			  "Argo",
					  			  "The Town",
					  			  (long) 4,
					  			  "By Background: The Town is the only one not based on a real life event.",
					  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("The Matador",
					  			  "GoldenEye", 
					  			  "Moonraker",
					  			  "Dr. No",
					  			  (long) 1,
					  			  "By Character: The Matador is not a James Bond movie.",
					  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("The Pledge",
					  			  "The Wolf of Wall Street", 
					  			  "Mars Attacks!",
					  			  "One Flew Over the Cuckoo's Nest",
					  			  (long) 2,
					  			  "By Actor: The Wolf of Wall Street is the only one without Jack Nicholson.",
					  			  (long)0);
			gameSets.save(gameSet);
			
			gameSet = new GameSet("JFK",
					  			  "Sleepers", 
					  			  "Goodfellas",
					  			  "A Few Good Men",
					  			  (long) 3,
					  			  "By Actor: Kevin Bacon does not appear in Goodfellas.",
					  			  (long)0);
			gameSets.save(gameSet);
		}
			
		return (Long) users.get(name);
	}
	
	@RequestMapping(value=SrvAPI.LEADERS_LIST, method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> getLeadersList(){
		
		return users;
	}
}
