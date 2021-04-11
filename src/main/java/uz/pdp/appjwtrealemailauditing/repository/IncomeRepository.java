package uz.pdp.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjwtrealemailauditing.entity.Income;
import uz.pdp.appjwtrealemailauditing.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
    @Query(value = "select * from income join card c on c.id = income.card_id where c.user_id = :user_id",nativeQuery = true)
    List<Income> getAllByAmountIn(UUID user_id);
}
