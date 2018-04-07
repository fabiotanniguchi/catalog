package br.unicamp.sindo.catalog.product;

import br.unicamp.sindo.catalog.configuration.Mapper;
import br.unicamp.sindo.catalog.utils.repository.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    private String name;
    private String description;
    private Double price;
    private Long stock;
    private String brand;
    private String tags;
    private String additionalInfo;
    private Status status;
    private Boolean highlight;
    private UUID categoryId;

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
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setStock(dto.getStock());
        e.setBrand(dto.getBrand());
        e.setHighlight(dto.getHighlight());
        e.setCategoryId(dto.getCategoryId());
        e.setStatus(dto.getStatus());

        if (dto.getTags() != null) {
            String tags = "";
            for (String piece : dto.getTags()) {
                tags = tags + piece + " ";
            }
            e.setTags(tags);
        }

        return e;
    }

    public static ProductEntity updateUUIDDTO(Product update, Product dto) {
        String additionalInfo = null;
        try {
            additionalInfo = Mapper.getInstance().writeValueAsString(dto.getAdditionalInfo());
        } catch (JsonProcessingException e1) {
            //TODO register occurrence
        }
        ProductEntity e = fromDTO(update);

        if (dto.getName() != null)
            e.setName(dto.getName());
        if (additionalInfo != null)
            e.setAdditionalInfo(additionalInfo);
        if (dto.getDescription() != null)
            e.setDescription(dto.getDescription());
        if (dto.getPrice() != null)
            e.setPrice(dto.getPrice());
        if (dto.getStock() != null)
            e.setStock(dto.getStock());
        if (dto.getBrand() != null)
            e.setBrand(dto.getBrand());
        if (dto.getHighlight() != null)
            e.setHighlight(dto.getHighlight());
        if (dto.getCategoryId() != null)
            e.setCategoryId(dto.getCategoryId());
        if (dto.getStatus() != null)
            e.setStatus(dto.getStatus());
        if (dto.getTags() != null) {
            String tagsConcated = "";
            for (String tag : dto.getTags()) {
                tagsConcated = tagsConcated + tag + " ";
            }
            e.setTags(tagsConcated);
        }

        return e;
    }

    @SuppressWarnings("unchecked")
    public Product assemble() {
        Map<String, String> additionalInfo = null;
        try {
            additionalInfo = Mapper.getInstance().readValue(this.additionalInfo, HashMap.class);
        } catch (IOException e) {
            //TODO register occurrence
        }

        List<String> tagsList = new ArrayList<>();
        if (tags != null) {
            String[] pieces = tags.split(" ");
            for (String piece : pieces) {
                tagsList.add(piece);
            }
        }

        return Product.builder()
                .id(id)
                .name(name)
                .additionalInfo(additionalInfo)
                .description(description)
                .price(price)
                .stock(stock)
                .brand(brand)
                .highlight(highlight)
                .categoryId(categoryId)
                .status(status)
                .tags(tagsList)
                .createdAt(createdAt.getTime())
                .updatedAt(updatedAt.getTime())
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

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "stock", nullable = false)
    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "highlight", nullable = false)
    public Boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    @Column(name = "category")
    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    @PrePersist
    @PreUpdate
    private void flush() {
        if (status == null)
            this.status = Status.ACTIVE;
        if (isNotBlank(name))
            this.name = name.toUpperCase();
    }
}
