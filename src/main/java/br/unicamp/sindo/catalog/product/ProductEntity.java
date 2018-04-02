package br.unicamp.sindo.catalog.product;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.unicamp.sindo.catalog.configuration.Mapper;
import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

@Entity
@Table(name="products")
public class ProductEntity extends BaseEntity {

	private String name;
	private String description;
	private String additionalInfo;
	private Status status;
	
	@SuppressWarnings("unchecked")
	public Product assemble(){
		Map<String, String> additionalInfo = null;
		try {
			additionalInfo = Mapper.getInstance().readValue(this.additionalInfo, HashMap.class);
		} catch (IOException e) {
			//TODO register occurrence
		}
		return Product.builder()
				//TODO complete builder
				.id(id)
				.name(name)
				.additionalInfo(additionalInfo)
				.build();
	}
	
	public static ProductEntity fromDTO(Product dto) {
		String additionalInfo = null;
		try {
			additionalInfo = Mapper.getInstance().writeValueAsString(dto.getAdditionalInfo());
		} catch (JsonProcessingException e1) {
			//TODO register occurrence
		}

		ProductEntity e = new ProductEntity();
		e.setId(dto.getId());
		e.setName(dto.getName());
		e.setAdditionalInfo(additionalInfo);
		
		return e;
	}
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="additional_info")
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@PrePersist
	@PreUpdate
	private void flush(){
		if(status == null)
			this.status = Status.ACTIVE;
		if(isNotBlank(name))
			this.name = name.toUpperCase();
	}
}
