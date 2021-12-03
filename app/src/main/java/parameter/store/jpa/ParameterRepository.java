package parameter.store.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    List<Parameter> findByEnvironment(String environment);

    List<Parameter> findByEnvironmentAndNameStartingWith(String environment, String name);

    Parameter findById(long id);
}
