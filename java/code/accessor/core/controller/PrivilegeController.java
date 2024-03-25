package code.accessor.core.controller;

import code.accessor.core.code.AccessorServiceConfiguration;
import code.accessor.core.code.Privilege4Access;
import code.accessor.core.code.exception.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static code.accessor.core.config.Consts.APP;


@RestController
@RequestMapping(APP + "/privilege")
@Tag(name = "Role", description = "Role management APIs")
public class PrivilegeController {

	private final AccessorServiceConfiguration configuration;
	public PrivilegeController(final AccessorServiceConfiguration configuration) {
		this.configuration = configuration;
	}

	@Operation(
			summary = "Get Role.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/{id}")
	ResponseEntity<?> get(@PathVariable String id) throws BaseException {
		Privilege4Access result = configuration.getPrivilegeService4Access().getById(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll() throws BaseException {
		List<Privilege4Access> result = configuration.getPrivilegeService4Access().getAll().stream().sorted((e0, e1) -> e0.getId().compareTo(e1.getId())).collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/get-all-str")
	ResponseEntity<?> getAllStr() throws BaseException {
		List<String> result = configuration.getPrivilegeService4Access().getAll().stream().map(e->e.getId()).collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}





//	@Operation(
//			summary = "Delete Role.",
//			description = "Delete role by id. ",
//			tags = {"ROLE"}
//	)
//	@DeleteMapping("/{id}")
//	void delete(@PathVariable String id) throws BaseException{
//		roleService.delete(id);
//	}
}
