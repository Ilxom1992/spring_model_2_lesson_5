package uz.pdp.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjwtrealemailauditing.entity.Card;
import uz.pdp.appjwtrealemailauditing.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
boolean existsByNumber(Long number);
List<Card> findAllByUserId(UUID user_id);
Card findByNumber(Long number);
boolean existsByNumberAndUserId(Long number, UUID user_id);
Card findByNumberAndUserId(Long number, UUID user_id);
}
