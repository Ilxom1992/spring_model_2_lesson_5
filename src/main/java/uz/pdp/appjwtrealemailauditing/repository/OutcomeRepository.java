package uz.pdp.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjwtrealemailauditing.entity.Income;
import uz.pdp.appjwtrealemailauditing.entity.Outcome;
import uz.pdp.appjwtrealemailauditing.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutcomeRepository extends JpaRepository<Outcome, UUID> {
    @Query(value = "select * from outcome join card c on c.id = outcome.card_id where c.user_id = :user_id",nativeQuery = true)
    List<Outcome> getAllByAmountOut(UUID user_id);
}
