package code.uica.impl.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import code.uica.code.UICAService;
import code.uica.impl.dto.EntityUICAResponse;
import code.uica.impl.dto.UICARequest;

import java.util.Map;

@RestController
@RequestMapping("/uica")
public class UICAController {
	private final UICAService uicaService;
	public UICAController(final UICAService uicaService) {
		this.uicaService = uicaService;
	}

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@PostMapping("/get")
	ResponseEntity<EntityUICAResponse> getEntity(
			@RequestParam Map<String, String> params,
			@RequestBody final UICARequest request) {

		EntityUICAResponse response = uicaService.getPermissions(request, params);
		return ResponseEntity.ok(response);
	}
}
