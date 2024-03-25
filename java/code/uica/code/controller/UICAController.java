package code.uica.code.controller;

import code.uica.code.Holder4UICA;
import code.uica.code.UICAServiceConfiguration;
import code.uica.code.service.HolderService4UICA;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import code.uica.code.UICAService;
import code.uica.impl.dto.EntityUICAResponse;
import code.uica.impl.dto.UICARequest;

import java.util.List;
import java.util.Map;

import static code.accessor.core.config.Consts.APP;

@RestController
@RequestMapping(APP + "/uica")
public class UICAController {
	private final UICAService uicaService;
	private final HolderService4UICA holderService4UICA;
	public UICAController(UICAServiceConfiguration configuration, HolderService4UICA holderService4UICA) {
		this.uicaService = new UICAService(configuration);
		this.holderService4UICA = holderService4UICA;
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

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@GetMapping("/get/{id}")
	ResponseEntity<Holder4UICA> getEntity(@PathVariable String id) {
		Holder4UICA response = holderService4UICA.getById(id);
		return ResponseEntity.ok(response);
	}

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@GetMapping("/get-all")
	ResponseEntity<List<Holder4UICA>> getAll() {

		List<Holder4UICA> response = holderService4UICA.getAll();
		return ResponseEntity.ok(response);
	}
}
