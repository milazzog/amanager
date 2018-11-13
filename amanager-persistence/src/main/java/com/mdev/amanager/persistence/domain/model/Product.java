package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "PRODUCT")
@NamedQueries({
        @NamedQuery(name = "product.find.by.name", query = "from Product p where p.name = :name"),
        @NamedQuery(name = "product.find.by.name.pattern", query = "from Product p where p.name like :name"),
        @NamedQuery(name = "product.find.by.name.and.type", query = "from Product p where p.name = :name and p.type = :type"),
        @NamedQuery(name = "product.find.by.name.pattern.and.type", query = "from Product p where p.name like :name and p.type = :type")
})
public class Product implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 32, nullable = false)
    private ProductType type;

    @Column(name = "NAME", length = 64, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_PART_REGISTRY", foreignKey = @ForeignKey(name = "FK_PRODUCT_PRODUCT_PART_REGISTRY"))
    private ProductPartRegistry productPartRegistryRegistry;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private Set<Price> prices = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Column(name = "REMOVED_AT")
    private Date removedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private Set<ProductPartRegistry> productPartRegistryRegistries = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPartRegistry getProductPartRegistryRegistry() {
        return productPartRegistryRegistry;
    }

    public void setProductPartRegistryRegistry(ProductPartRegistry productPartRegistryRegistry) {
        this.productPartRegistryRegistry = productPartRegistryRegistry;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
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

    public Set<ProductPartRegistry> getProductPartRegistryRegistries() {
        return productPartRegistryRegistries;
    }

    public void setProductPartRegistryRegistries(Set<ProductPartRegistry> productPartRegistryRegistries) {
        this.productPartRegistryRegistries = productPartRegistryRegistries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (type != product.type) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (addedAt != null ? !addedAt.equals(product.addedAt) : product.addedAt != null) return false;
        return removedAt != null ? removedAt.equals(product.removedAt) : product.removedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (addedAt != null ? addedAt.hashCode() : 0);
        result = 31 * result + (removedAt != null ? removedAt.hashCode() : 0);
        return result;
    }
}
