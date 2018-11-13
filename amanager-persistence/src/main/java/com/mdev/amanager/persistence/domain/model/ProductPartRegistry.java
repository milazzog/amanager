package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmilazzo on 09/11/2018.
 */
@Entity
@Table(name = "PRODUCT_PART_REGISTRY")
public class ProductPartRegistry implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productPartRegistry")
    private Set<ProductPart> parts = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PRODUCT", foreignKey = @ForeignKey(name = "FK_PRODUCT_PART_REGISTRY_PRODUCT"))
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REMOVED_AT")
    private Date removedAt;

    @Override
    public Long getId() {
        return id;
    }

    public Set<ProductPart> getParts() {
        return parts;
    }

    public void setParts(Set<ProductPart> parts) {
        this.parts = parts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Date getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(Date removedAt) {
        this.removedAt = removedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPartRegistry)) return false;

        ProductPartRegistry that = (ProductPartRegistry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parts != null ? !parts.equals(that.parts) : that.parts != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (addedAt != null ? !addedAt.equals(that.addedAt) : that.addedAt != null) return false;
        return removedAt != null ? removedAt.equals(that.removedAt) : that.removedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parts != null ? parts.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (addedAt != null ? addedAt.hashCode() : 0);
        result = 31 * result + (removedAt != null ? removedAt.hashCode() : 0);
        return result;
    }
}
