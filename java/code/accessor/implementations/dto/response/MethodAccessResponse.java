package code.accessor.implementations.dto.response;

import code.accessor.core.MethodAccess4Access;
import code.accessor.core.Privilege4Access;
import code.accessor.implementations.dto.MethodAccessBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MethodAccessResponse extends MethodAccessBaseDto implements MethodAccess4Access {
	@Schema(name ="Privileges", description = "Privileges list.")
	private List<Privilege4Access> privileges;

}
