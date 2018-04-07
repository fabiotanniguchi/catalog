package br.unicamp.sindo.catalog.utils.repository;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    protected UUID id;
    protected Date createdAt;
    protected Date updatedAt;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    private void generateTimestamp() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = new Date();
    }

}
