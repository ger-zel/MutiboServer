package logic;

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

	private Map<String, Long> users = new HashMap<String, Long>();
	
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
			
		return (Long) users.get(name);
	}
}
