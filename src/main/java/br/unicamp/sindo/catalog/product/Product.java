package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.Status;
import br.unicamp.sindo.catalog.utils.MD5;
import br.unicamp.sindo.catalog.utils.web.VersionableDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
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
    private Long createdAt;
    private Long updatedAt;
    private Status status;
    private Boolean highlight;
    private UUID categoryId;
    private String imageUrl;

    private Double weight;
    private Double length;
    private Double width;
    private Double height;

    private String groupId;



    @JsonIgnore
    public String version() {
        return MD5.hash(this.toString());
    }

}
