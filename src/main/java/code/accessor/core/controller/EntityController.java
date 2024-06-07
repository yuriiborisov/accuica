package code.accessor.core.controller;

import code.accessor.core.code.AccessorServiceConfiguration;
import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.exception.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static code.accessor.core.config.Consts.APP;


@RestController
@RequestMapping(APP + "/entity")
@Tag(name = "Entity", description = "Role management APIs")
public class EntityController {

	private final AccessorServiceConfiguration configuration;
	public EntityController(final AccessorServiceConfiguration configuration) {
		this.configuration = configuration;
	}

	@Operation(
			summary = "Get entities",
			description = "Get role by id. ",
			tags = {"ENTITY"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll() throws BaseException {
		List<Privilege4Access> result = configuration.getPrivilegeService4Access().getAll();
		return ResponseEntity.ok().body(result);
	}
}
