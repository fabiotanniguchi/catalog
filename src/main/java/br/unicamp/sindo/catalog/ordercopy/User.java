package br.unicamp.sindo.catalog.ordercopy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

	private String id;
	private String address;
	private String birthDate;
	private String cep;
	private String cpf;
	private String email;
	private String name;
	private String telephone;

	public User() {
	}
}
