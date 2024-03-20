package code.accessor.implementations.service;

public abstract class EntityAccessBaseService {

	private final AccessRepository $;

	protected EntityAccessBaseService(final AccessRepository $) {
		this.$ = $;
	}

}
