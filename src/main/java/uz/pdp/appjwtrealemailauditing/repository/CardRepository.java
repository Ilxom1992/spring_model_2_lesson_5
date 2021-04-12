package uz.pdp.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjwtrealemailauditing.entity.Card;
import uz.pdp.appjwtrealemailauditing.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsById(UUID id);

    Card getCardById(UUID id);

    boolean existsByIdAndUserId(UUID id, UUID user_id);

    @Query(value = "select balance from card where id = ?1", nativeQuery = true)
    Double getMoney(UUID cardId);

    @Query(value = "select * from card where user_id = ?1", nativeQuery = true)
    Card getCardByUserId(UUID user_id);

    @Query(value = "update card set balance = ?1 where id = ?2 returning balance", nativeQuery = true)
    void editMoney(Double money, UUID cardId);
}
