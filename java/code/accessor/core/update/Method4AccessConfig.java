package code.accessor.core.update;

import lombok.Data;

import java.util.List;

@Data
public class Method4AccessConfig{
    private String methodId;
    private List<Privilege4AccessConfig> privileges;
}
