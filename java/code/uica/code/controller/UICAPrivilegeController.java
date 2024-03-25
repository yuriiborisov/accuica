package code.uica.code.controller;

import code.common.PrivilegeApplicable;
import code.uica.code.Holder4UICA;
import code.uica.code.Privilege4UICA;
import code.uica.code.UICAService;
import code.uica.code.UICAServiceConfiguration;
import code.uica.code.service.HolderService4UICA;
import code.uica.code.service.PrivilegeService4UICA;
import code.uica.impl.dto.EntityUICAResponse;
import code.uica.impl.dto.UICARequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static code.accessor.core.config.Consts.APP;

@RestController
@RequestMapping(APP + "/uica/privilege")
public class UICAPrivilegeController {
	private final UICAService uicaService;
	private final PrivilegeService4UICA privilegeService4UICA;
	public UICAPrivilegeController(UICAServiceConfiguration configuration, PrivilegeService4UICA privilegeService4UICA) {
		this.uicaService = new UICAService(configuration);
		this.privilegeService4UICA = privilegeService4UICA;
	}

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@GetMapping("/get/{id}")
	ResponseEntity<Privilege4UICA> getPrivilegeById(@PathVariable("id") String id) {
		Privilege4UICA response = privilegeService4UICA.getById(id);
		return ResponseEntity.ok(response);
	}

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@GetMapping("/get-all-m")
	ResponseEntity<?> getAllMapped() {
		Map<String, List<Privilege4UICA>> result = new HashMap<>();
		privilegeService4UICA.getAll().forEach( p -> {
			PrivilegeApplicable applicable = p.getApplicable();
			if(applicable == null){
				List<Privilege4UICA> list = result.get("Other") == null ? new ArrayList<>() : result.get("Other");
				list.add(p);
				result.put("Other", list);
			}else{
				applicable.getEntities().forEach(entity -> {
					List<Privilege4UICA> list = result.get(entity) == null ? new ArrayList<>() : result.get(entity);
					list.add(p);
					result.put(entity, list);
				});
			}
		});
		return ResponseEntity.ok(result);
	}

	@Operation(
			summary = "Get UICA (Uni-graphic Interface Component Access) holder with requested code.entity.  ",
			description = "Return UICA holder and code.entity by id. ",
			tags = {"UICA"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll() {
		List<Privilege4UICA> response = privilegeService4UICA.getAll();
		return ResponseEntity.ok(response);
	}

}
