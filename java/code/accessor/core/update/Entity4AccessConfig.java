package code.accessor.core.update;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class Entity4AccessConfig {
    private String entity;
    private List<Method4AccessConfig> methods;
}
