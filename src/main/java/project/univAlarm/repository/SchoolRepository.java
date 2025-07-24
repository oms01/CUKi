package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findAllByOrderByNameAsc();
}
