package code.impl.accessor.dto.response;

import code.accessor.core.code.dto.response.Method4AccessResponse;
import code.accessor.core.code.Privilege4Access;
import code.impl.accessor.dto.MethodAccessBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccess4AccessImpl  extends MethodAccessBaseDto implements Method4AccessResponse {
	@Schema(name ="Privileges", description = "Privileges list.")
	private List<Privilege4Access> privileges;

}
