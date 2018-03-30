package br.unicamp.sindo.catalog.error;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String className, UUID id) {
		super(
				new StringBuilder("Could not find ")
				.append(className)
				.append(" with ID ")
				.append(id).toString());
	}

}
