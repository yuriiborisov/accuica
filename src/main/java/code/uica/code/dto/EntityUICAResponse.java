package code.uica.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * DTO for UICA - Uni-graphic Interface Component Access
 */
@Data
@AllArgsConstructor
public class EntityUICAResponse {

	@Schema(name = "entity", description = "Object whose fields access is regulated")
	private Object entity;

	@Schema(name = "uica", description = "Map with accesses of ui-components, where key is component Id")
	private Map<String, UICAItemResponse> uica;
}
