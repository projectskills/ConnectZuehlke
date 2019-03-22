package ch.zuehlke.fullstack.ConnectZuehlke.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<ProjectEntity, String> {
    Optional<ProjectEntity> findByCode(String code);
}
