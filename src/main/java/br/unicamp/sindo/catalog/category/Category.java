package br.unicamp.sindo.catalog.category;

import br.unicamp.sindo.catalog.Status;
import br.unicamp.sindo.catalog.utils.MD5;
import br.unicamp.sindo.catalog.utils.web.VersionableDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
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
    private Long createdAt;
    private Long updatedAt;
    private String groupId;

    @JsonIgnore
    public String version() {
        return MD5.hash(this.toString());
    }

}
