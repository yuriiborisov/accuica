package code.accessor.core.controller;

import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.service.RoleService4Access;
import code.accessor.core.code.exception.BaseException;
import code.accessor.core.code.dto.request.Role4AccessRequest;
import code.accessor.core.code.dto.PrivilegeEntityMatrix;
import code.accessor.impl.dto.request.Role4AccessRequestV2;
import code.accessor.impl.service.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static code.accessor.core.config.Consts.APP;


@RestController
@RequestMapping(APP + "/role")
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {

	private final RoleService4Access roleService;

	public RoleController(final RoleServiceImpl roleService) {
		this.roleService = roleService;
	}

	@Operation(
			summary = "Create Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PostMapping
	ResponseEntity<?> create(@RequestBody Role4AccessRequest role) throws BaseException {
		Role4AccessResponse result = roleService.create(role);
		return ResponseEntity.ok().body(result);
	}
//
//	@Operation(
//			summary = "Update Role.",
//			description = "Return Role. ",
//			tags = {"ROLE"}
//	)
//	@PutMapping
//	ResponseEntity<?> update(@RequestBody RoleRequest role) throws BaseException{
//		RoleResponse result = roleService.update(role);
//		return ResponseEntity.ok().body(result);
//	}
//
	@Operation(
			summary = "Get all Roles.",
			description = "Get all roles in system. ",
			tags = {"ROLE"}
	)
	@GetMapping("/get-all")
	ResponseEntity<?> getAll(){
		List<Role4AccessResponse> result = roleService.getAll();
		return  ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/{id}")
	ResponseEntity<?> get(@PathVariable String id) throws BaseException {
		Role4AccessResponse result = roleService.getById(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Update Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PutMapping
	ResponseEntity<?> updateGrantAccess(@RequestBody Role4AccessRequestV2 requestV2 ) throws BaseException{
		if(requestV2 == null){
			ResponseEntity.status(HttpStatus.CONFLICT);
		}
		roleService.update(requestV2);
		List<PrivilegeEntityMatrix> result = roleService.getMatrix(requestV2.getId());
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Update Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PutMapping("/info")
	ResponseEntity<?> updateInfo(@RequestBody Role4AccessRequestV2 request ) throws BaseException{
		if(request == null){
			ResponseEntity.status(HttpStatus.CONFLICT);
		}
		Role4AccessResponse result = roleService.update(request);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/matrix-t/{id}")
	ResponseEntity<?> getMatrixT(@PathVariable String id) throws BaseException {
		List<PrivilegeEntityMatrix> result = roleService.getMatrix(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/entities/{id}")
	ResponseEntity<?> getEntities(@PathVariable String id) throws BaseException {
		List result = List.of("IMPACT",
				"CLASSIFIERS",
				"WORKPACKAGE",
				"EMPLOYEE",
				"ATTACHMENT",
				"REPORT",
				"TASK_STATE",
				"RESOLUTION",
				"CHANGE_FORM",
				"DOCUMENT_TYPE",
				"REVIEW_GROUP",
				"TASK_FORM",
				"GBS",
				"REQUEST_FORM",
				"ORGANIZATION",
				"REVISION_STATUS",
				"NOTIFICATION",
				"DEPARTMENT",
				"DISCIPLINE",
				"MODULE_AREA",
				"UICA",
				"PROJECT",
				"LOCATION",
				"QYERY_TYPE",
				"SYSTEM_UNIT",
				"ROLE"
		).stream().sorted().collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}


	@Operation(
			summary = "Delete Role.",
			description = "Delete role by id. ",
			tags = {"ROLE"}
	)
	@DeleteMapping("/{id}")
	ResponseEntity delete(@PathVariable String id) throws BaseException{
		try {
			roleService.delete(id);
			return ResponseEntity.ok().build();
		}catch (Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
		}

	}
}
