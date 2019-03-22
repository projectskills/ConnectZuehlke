package ch.zuehlke.fullstack.ConnectZuehlke.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByCode(String code);
}
