package id.beecolony.routeanalyze.model.memory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "baseapplication")
// @PropertySource("classpath:/property/service.properties")
public class ServiceMemory {
    private Map<String, String> service = new HashMap<>();

    public Map<String, String> getService(){
        return service;
    }

    public void setService(Map<String, String> service) {
        this.service = service;
    }
}
