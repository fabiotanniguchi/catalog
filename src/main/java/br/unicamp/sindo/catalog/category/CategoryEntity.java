package br.unicamp.sindo.catalog.category;

import br.unicamp.sindo.catalog.Status;
import br.unicamp.sindo.catalog.configuration.Mapper;
import br.unicamp.sindo.catalog.utils.repository.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    private String name;
    private String description;
    private UUID parentId;
    private String additionalInfo;
    private Status status;
    private String groupId;

    public static CategoryEntity fromDTO(Category dto) {
        String additionalInfo = null;
        try {
            additionalInfo = Mapper.getInstance().writeValueAsString(dto.getAdditionalInfo());
        } catch (JsonProcessingException e1) {
            //TODO register occurrence
        }

        CategoryEntity e = new CategoryEntity();
        e.setDescription(dto.getDescription());
        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setParentId(dto.getParentId());
        e.setStatus(dto.getStatus());
        e.setAdditionalInfo(additionalInfo);
        e.setGroupId(dto.getGroupId());

        return e;
    }

    public static CategoryEntity updateUUIDDTO(Category update, Category dto) {
        String additionalInfo = null;
        try {
            additionalInfo = Mapper.getInstance().writeValueAsString(dto.getAdditionalInfo());
        } catch (JsonProcessingException e1) {
            //TODO register occurrence
        }
        CategoryEntity e = fromDTO(update);

        if (dto.getName() != null)
            e.setName(dto.getName());
        if (additionalInfo != null)
            e.setAdditionalInfo(additionalInfo);
        if (dto.getDescription() != null)
            e.setDescription(dto.getDescription());
        if (dto.getParentId() != null)
            e.setParentId(dto.getParentId());
        if (dto.getStatus() != null)
            e.setStatus(dto.getStatus());
        if (dto.getGroupId() != null)
            e.setGroupId(dto.getGroupId());

        return e;
    }

    @SuppressWarnings("unchecked")
    public Category assemble() {
        Map<String, String> additionalInfo = null;
        try {
            additionalInfo = Mapper.getInstance().readValue(this.additionalInfo, HashMap.class);
        } catch (IOException e) {
            //TODO register occurrence
        }
        return Category.builder()
                .additionalInfo(additionalInfo)
                .description(description)
                .id(id)
                .name(name)
                .parentId(parentId)
                .status(status)
                .createdAt(createdAt == null ? null : createdAt.getTime())
                .updatedAt(updatedAt == null ? null : updatedAt.getTime())
                .groupId(groupId)
                .build();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "parentId")
    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    @Column(name = "additional_info")
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

    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @PrePersist
    @PreUpdate
    private void flush() {
        if (status == null)
            this.status = Status.ACTIVE;
        //if (isNotBlank(name))
        //    this.name = name.toUpperCase();
    }
}
