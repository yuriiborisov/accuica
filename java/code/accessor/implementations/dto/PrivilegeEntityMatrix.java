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
public class PrivilegeEntityMatrix {
    private Privilege4Access privilege;
    private List<String> entities;

}
