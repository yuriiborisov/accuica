package code.impl.accessor.dto.request;

import code.accessor.core.code.dto.request.Method4AccessRequest;
import code.impl.accessor.dto.MethodAccessBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccessRequestImpl extends MethodAccessBaseDto implements Method4AccessRequest {
	@Schema(name ="Privileges ids", description = "Privileges ids.")
	private List<String> privilegeIds;
}
