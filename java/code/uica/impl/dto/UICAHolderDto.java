package code.uica.impl.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import code.uica.code.Config4UICA;
import code.uica.code.Holder4UICA;

@Data
public class UICAHolderDto implements Holder4UICA {

	@Schema(description = "form id")
	private String formId;

	@Schema(description = "Description")
	private String description;

	@Schema(description = "UICAConfig")
	private Config4UICA config;
}
