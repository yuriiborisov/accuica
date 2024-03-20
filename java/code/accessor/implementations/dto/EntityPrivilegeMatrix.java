package code.accessor.implementations.dto;

import code.accessor.core.Privilege4Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntityPrivilegeMatrix {
    private String entity;
    private List<String> privileges;

}
