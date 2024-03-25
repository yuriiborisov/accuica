package code.impl.uica.service;

import code.impl.uica.entity.UICAHolder;
import code.impl.uica.mapper.HolderMapper4Uica;
import code.impl.uica.repository.UICARepository;
import code.uica.code.Holder4UICA;
import code.uica.code.exception.UICAException;
import code.uica.code.service.HolderService4UICA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolderService4UICAImpl implements HolderService4UICA {
	private final UICARepository uicaRepository;
	private final HolderMapper4Uica mapper;

	public HolderService4UICAImpl(HolderMapper4Uica mapper, UICARepository uicaRepository) {
		this.uicaRepository = uicaRepository;
		this.mapper = mapper;
		}

	@Override
	public Holder4UICA getById(String id) {
		UICAHolder holder4UICA = uicaRepository.findById(id).orElseThrow(() ->  new UICAException("Holder uica with id: '" + id + "' not found!"));
		return mapper.map(holder4UICA);
	}

	@Override
	public List<Holder4UICA> getAll() {
		List<UICAHolder> holder4UICAList = uicaRepository.findAll();
		return holder4UICAList.stream().map(mapper::map).collect(Collectors.toList());
	}

	@Override
	public Holder4UICA create(Holder4UICA holder4UICA) {
		return null;
	}

	@Override
	public Holder4UICA update(Holder4UICA holder4UICA) {
		return null;
	}
}
