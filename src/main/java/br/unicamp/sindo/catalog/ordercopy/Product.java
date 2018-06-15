package br.unicamp.sindo.catalog.ordercopy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

	private String id;
	private String brand;
	private String description;
	private String imageUrl;
	private String name;
	private int quantity;
	private double price;

	public Product() {
	}
}
