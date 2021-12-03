package parameter.store.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import parameter.store.jpa.Parameter;
import parameter.store.jpa.ParameterRepository;

import java.util.List;

@RestController
public class ParameterController {

    private static Logger log = LoggerFactory.getLogger(ParameterController.class);
    @Autowired
    private ParameterRepository repository;

    @GetMapping("/")
    public String index() {
        return "OK";
    }

    @GetMapping("/parameter/{environment}/")
    public List<Parameter> getByEnvironment(@PathVariable String environment) {
        log.info("GetByEnvironment({})", environment);
        return repository.findByEnvironment(environment);
    }

    @GetMapping("/parameter/{environment}/{namePrefix}")
    public List<Parameter> getByEnvironmentAndPrefix(@PathVariable("environment") String environment, @PathVariable("namePrefix") String namePrefix) {
        log.info("GetByEnvironment({},{})", environment, namePrefix);
        return repository.findByEnvironmentAndNameStartingWith(environment, "/" + namePrefix);
    }

}
