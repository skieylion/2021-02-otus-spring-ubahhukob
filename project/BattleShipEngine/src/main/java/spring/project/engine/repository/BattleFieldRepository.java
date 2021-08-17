package spring.project.engine.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.project.engine.model.PlayerDB;

@Repository
public interface BattleFieldRepository extends KeyValueRepository<PlayerDB,String> {

}
