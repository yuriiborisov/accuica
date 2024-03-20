package code.accessor.implementations.dto.request;

import code.accessor.implementations.dto.MethodAccessBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccessRequest extends MethodAccessBaseDto {
	@Schema(name ="Privileges ids", description = "Privileges ids.")
	private List<String> privilegeIds;
}
