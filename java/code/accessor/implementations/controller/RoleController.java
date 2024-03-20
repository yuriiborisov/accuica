package code.accessor.implementations.controller;

import code.accessor.core.Role4Access;
import code.accessor.core.exception.BaseException;
import code.accessor.implementations.dto.EntityPrivilegeMatrix;
import code.accessor.implementations.dto.PrivilegeEntityMatrix;
import code.accessor.implementations.dto.request.RoleRequest;
import code.accessor.implementations.dto.request.RoleRequestV2;
import code.accessor.implementations.dto.response.RoleResponse;
import code.accessor.implementations.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static code.accessor.implementations.controller.Consts.APP;


@RestController
@RequestMapping(APP + "/role")
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {

	private final RoleService roleService;

	public RoleController(final RoleService roleService) {
		this.roleService = roleService;
	}

	@Operation(
			summary = "Create Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PostMapping
	ResponseEntity<?> create(@RequestBody RoleRequest role) throws BaseException {
		RoleResponse result = roleService.create(role);
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
		List<RoleResponse> result = roleService.getAll();
		return  ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/{id}")
	ResponseEntity<?> get(@PathVariable String id) throws BaseException {
		Role4Access result = roleService.get(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Update Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PutMapping
	ResponseEntity<?> updateGrantAccess(@RequestBody RoleRequestV2 requestV2 ) throws BaseException{
		if(requestV2 == null){
			ResponseEntity.status(HttpStatus.CONFLICT);
		}
		roleService.update(requestV2);
		List<PrivilegeEntityMatrix> result = roleService.getMatrixT(requestV2.getId());
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Update Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PutMapping("/info")
	ResponseEntity<?> updateInfo(@RequestBody RoleRequest request ) throws BaseException{
		if(request == null){
			ResponseEntity.status(HttpStatus.CONFLICT);
		}
		RoleResponse result = roleService.update(request);
		return ResponseEntity.ok().body(result);
	}



	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/matrix/{id}")
	ResponseEntity<?> getMatrix(@PathVariable String id) throws BaseException {
		List<EntityPrivilegeMatrix> result = roleService.getMatrix(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/matrix-t/{id}")
	ResponseEntity<?> getMatrixT(@PathVariable String id) throws BaseException {
		List<PrivilegeEntityMatrix> result = roleService.getMatrixT(id);
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
