package code.uica.code;


import code.uica.code.service.EntityService4UICA;

public interface EntityUICAFactory {
	EntityService4UICA getService(Object entity);
}
