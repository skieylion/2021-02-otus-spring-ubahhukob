package spring.project.manager.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.project.manager.model.Player;

@Repository
public interface BattleFieldRepository extends KeyValueRepository<Player,String> {
}
