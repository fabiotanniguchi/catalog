package br.unicamp.sindo.catalog.utils.web;

import java.util.UUID;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HeaderBuilder {

	private MultiValueMap<String, String> headers;
	
	private HeaderBuilder(){
		headers = new LinkedMultiValueMap<>();
	}
	
	public static HeaderBuilder init(){
		return new HeaderBuilder();
	}
	
	public HeaderBuilder page(int page){
		headers.add("Page-Number", String.valueOf(page));
		return this;
	}
	
	public HeaderBuilder previousPage(int page){
		headers.add("Previous-Page", String.valueOf(page));
		return this;
	}
	
	public HeaderBuilder nextPage(int page){
		headers.add("Next-Page", String.valueOf(page));
		return this;
	}
	
	public HeaderBuilder eTag(String eTag){
		headers.add("ETag", eTag);
		return this;
	}
	
	public HeaderBuilder location(UUID id){
		//TODO improve
		headers.add("Location", String.valueOf(id));
		return this;
	}
	
	public MultiValueMap<String, String> assemble(){
		return headers;
	}
	
}
