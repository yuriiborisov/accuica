package code.uica.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import code.uica.code.UICAState;
import code.uica.code.UICAStatus;


//UICA - Uni-graphic Interface Component Access
@Data
public class UICAItemResponse {

	@Schema(name ="access", description = "The ui-components are readable (hidden) by default.The EDIT status make components editable")
	private UICAState access;

	@Schema(name ="status", description = "OK - component access has been configurated, NOT_FOUND - component access has not been configurated (by default has access type HIDDEN)")
	private UICAStatus status;

	@Schema(name ="error", description = "Reason why status is ERROR")
	private String error;
}
