package br.unicamp.sindo.catalog.category;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.unicamp.sindo.catalog.utils.MD5;
import br.unicamp.sindo.catalog.utils.web.VersionableDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Category extends VersionableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;
	private String name;
	private String description;
	private UUID parentId;
	private Map<String, String> additionalInfo;
	private Status status;
	
	@JsonIgnore
	public String version(){
		return MD5.hash(this.toString());
	}
	
}
