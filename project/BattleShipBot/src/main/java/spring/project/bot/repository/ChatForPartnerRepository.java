package spring.project.bot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.project.bot.model.ChatForPartner;

@Repository
public interface ChatForPartnerRepository extends KeyValueRepository<ChatForPartner, String> {
}
