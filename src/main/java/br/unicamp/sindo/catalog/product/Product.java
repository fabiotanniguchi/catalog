package br.unicamp.sindo.catalog.product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.unicamp.sindo.catalog.utils.MD5;
import br.unicamp.sindo.catalog.utils.web.VersionableDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Product extends VersionableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;
	private String name;
	private String description;
	private Double price;
	private Long stock;
	private String brand;
	private List<String> tags;
    private Map<String, String> additionalInfo;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private Boolean highlight;
    private UUID categoryId;



	@JsonIgnore
	public String version(){
		return MD5.hash(this.toString());
	}

	public UUID getId() {return this.id;
	}

	public Map<String, String> getAdditionalInfo() {return this.additionalInfo;
	}

	public String getName() {return this.name;}

	public String getDescription() {return this.description;
	}

	public Double getPrice() {return this.price;
	}

	public Long getStock() {return this.stock;
	}
	public void setStock(Long stock) {this.stock = stock;}

	public String getBrand() {return this.brand;
	}

	public Boolean getHighlight() {
		return this.highlight;
	}

	public void setHighlight(Boolean highlight) {
		this.highlight = highlight;
	}

	public UUID getCategoryId() {return this.categoryId;
	}
}
