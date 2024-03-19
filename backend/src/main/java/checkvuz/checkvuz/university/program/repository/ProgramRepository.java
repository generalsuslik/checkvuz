package checkvuz.checkvuz.university.program.repository;

import checkvuz.checkvuz.university.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    Optional<Program> getProgramByTitle(String title);
}
