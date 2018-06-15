package br.unicamp.sindo.catalog.ordercopy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Address {

	private String city;
	private String state;
	private String district;
	private String street;
	private String number;
	private String complement;
}
