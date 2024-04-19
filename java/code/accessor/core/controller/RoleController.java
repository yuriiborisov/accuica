package code.accessor.core.controller;

import code.accessor.core.code.AccessorServiceConfiguration;
import code.accessor.core.code.dto.response.Role4AccessResponse;
import code.accessor.core.code.exception.BaseException;
import code.accessor.core.code.dto.PrivilegeEntityMatrix;
import code.impl.accessor.dto.request.Role4AccessRequest;
import code.impl.accessor.dto.request.Role4AccessRequestV2;
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

	private final AccessorServiceConfiguration configuration;
	public RoleController(final AccessorServiceConfiguration configuration) {
		this.configuration = configuration;
	}

	@Operation(
			summary = "Create Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PostMapping
	ResponseEntity<?> create(@RequestBody Role4AccessRequest role) throws BaseException {
		Role4AccessResponse result = configuration.getRoleService4Access().create(role);
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
//		RoleResponse result = configuration.getRoleService4Access().update(role);
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
		List<Role4AccessResponse> result = configuration.getRoleService4Access().getAll();
		return  ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/{id}")
	ResponseEntity<?> get(@PathVariable String id) throws BaseException {
		Role4AccessResponse result = configuration.getRoleService4Access().getById(id);
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
		configuration.getRoleService4Access().update(requestV2);
		List<PrivilegeEntityMatrix> result = configuration.getRoleService4Access().getMatrix(requestV2.getId());
		return ResponseEntity.ok().body(result);
	}

//	@Operation(
//			summary = "Update Role.",
//			description = "Return Role. ",
//			tags = {"ROLE"}
//	)
//	@PutMapping("")
//	ResponseEntity<?> updateRoleMatrix(@RequestBody Role4AccessRequestV2 request ) throws BaseException{
//		if(request == null){
//			ResponseEntity.status(HttpStatus.CONFLICT);
//		}
//		Role4AccessResponse result = configuration.getRoleService4Access().update(request);
//		return ResponseEntity.ok().body(result);
//	}

	@Operation(
			summary = "Update Role.",
			description = "Return Role. ",
			tags = {"ROLE"}
	)
	@PutMapping("/info")
	ResponseEntity<?> updateInfo(@RequestBody Role4AccessRequest request ) throws BaseException{
		if(request == null){
			ResponseEntity.status(HttpStatus.CONFLICT);
		}
		Role4AccessResponse result = configuration.getRoleService4Access().updateInfo(request);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/matrix-t/{id}")
	ResponseEntity<?> getMatrixT(@PathVariable String id) throws BaseException {
		List<PrivilegeEntityMatrix> result = configuration.getRoleService4Access().getMatrix(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(
			summary = "Get Role Matrix.",
			description = "Get role by id. ",
			tags = {"ROLE"}
	)
	@GetMapping("/matrix-p/{id}")
	ResponseEntity<?> getMatrixParents(@PathVariable String id) throws BaseException {
		List<PrivilegeEntityMatrix> result = configuration.getRoleService4Access().getMatrixOfParents(id);
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
			configuration.getRoleService4Access().delete(id);
			return ResponseEntity.ok().build();
		}catch (Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
		}

	}
}
