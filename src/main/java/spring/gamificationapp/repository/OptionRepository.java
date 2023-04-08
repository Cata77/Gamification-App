package spring.gamificationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gamificationapp.model.Option;
@Repository
public interface OptionRepository extends JpaRepository<Option,Integer> {
}
