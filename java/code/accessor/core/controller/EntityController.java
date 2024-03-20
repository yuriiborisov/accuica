package code.accessor.core.controller;

import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.exception.BaseException;
import code.accessor.impl.service.PrivilegeServiceImpl;
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

	private final PrivilegeServiceImpl privilegeServiceImpl;

	public EntityController(final PrivilegeServiceImpl privilegeServiceImpl) {
		this.privilegeServiceImpl = privilegeServiceImpl;
	}

	@Operation(
			summary = "Get entities",
			description = "Get role by id. ",
			tags = {"ENTITY"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll() throws BaseException {
		List<Privilege4Access> result = privilegeServiceImpl.getAll();
		return ResponseEntity.ok().body(result);
	}
}
