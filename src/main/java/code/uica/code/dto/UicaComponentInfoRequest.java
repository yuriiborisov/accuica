package code.uica.code.dto;

import code.uica.code.Config4UICA;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UicaComponentInfoRequest {
    String formId;
    String componentId;
    String componentIdToRename;

}
