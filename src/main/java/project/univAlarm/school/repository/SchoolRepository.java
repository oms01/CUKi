package project.univAlarm.school.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.school.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByNameAndCampus(String name, String campus);
    List<School> findAllByOrderByNameAsc();
}
