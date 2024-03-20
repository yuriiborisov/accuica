package code.accessor.implementations.controller;

import code.accessor.core.Privilege4Access;
import code.accessor.core.exception.BaseException;
import code.accessor.implementations.service.PrivilegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static code.accessor.implementations.controller.Consts.APP;


@RestController
@RequestMapping(APP + "/entity")
@Tag(name = "Entity", description = "Role management APIs")
public class EntityController {

	private final PrivilegeService privilegeService;

	public EntityController(final PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	@Operation(
			summary = "Get entities",
			description = "Get role by id. ",
			tags = {"ENTITY"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll() throws BaseException {
		List<Privilege4Access> result = privilegeService.getAll();
		return ResponseEntity.ok().body(result);
	}
}
