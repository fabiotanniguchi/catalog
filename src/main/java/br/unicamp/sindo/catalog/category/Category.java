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

    @JsonIgnore
    public String version() {
        return MD5.hash(this.toString());
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getParentId() {
        return parentId;
    }

    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public Status getStatus() {
        return status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
