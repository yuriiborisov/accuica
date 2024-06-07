package code.impl.uica.service;

import code.impl.accessor.entity.PrivilegeEntity;
import code.impl.uica.mapper.PrivilegeMapper4Uica;
import code.impl.uica.repository.PrivilegeRepository4UICA;
import code.uica.code.Privilege4UICA;
import code.uica.code.exception.UICAException;
import code.uica.code.service.PrivilegeService4UICA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivilegeService4UICAImpl implements PrivilegeService4UICA {
	private final PrivilegeRepository4UICA privilegeRepository;
	private final PrivilegeMapper4Uica mapper;

	public PrivilegeService4UICAImpl(PrivilegeMapper4Uica mapper, PrivilegeRepository4UICA privilegeRepository) {
		this.privilegeRepository = privilegeRepository;
		this.mapper = mapper;
		}

	@Override
	public Privilege4UICA getById(final String id) {
		PrivilegeEntity privilege = privilegeRepository.findById(id).orElseThrow(() ->  new UICAException("Privilege with id: '" + id + "' not found!"));
			return mapper.map(privilege);
	}

	@Override
	public List<Privilege4UICA> getAll() {
		return privilegeRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
	}

	@Override
	public Privilege4UICA getGodPrivilege() {
		return null;
	}

}
