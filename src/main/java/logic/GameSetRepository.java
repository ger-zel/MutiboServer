package logic;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = SrvAPI.GAME_SET_SVC_PATH)
public interface GameSetRepository extends CrudRepository<GameSet, Long>{
	
}
