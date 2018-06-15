package br.unicamp.sindo.catalog.ordercopy;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Payment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7838610628977483582L;
	
	private String creditCard;
	private String cvv;
	private String name;
	private String expirationDate;
}
