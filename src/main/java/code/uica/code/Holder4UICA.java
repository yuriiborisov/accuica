package code.uica.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Holder4UICA {
	private String formId;

	@Schema(description = "Description")
	private String description;

	@Schema(description = "UICAConfig")
	private Config4UICA config;
}