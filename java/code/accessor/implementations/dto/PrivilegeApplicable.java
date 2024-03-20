package code.accessor.implementations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PrivilegeApplicable {
    List<String> entities;
}
