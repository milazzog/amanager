package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.ProductType;
import com.mdev.amanager.persistence.domain.enums.UM;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "RAW_PRODUCT", uniqueConstraints = @UniqueConstraint(name = "RAW_PRODUCT_NAME_UNIQUE", columnNames = "NAME"))
@NamedQueries({
        @NamedQuery(name = "raw.product.find.by.type", query = "from RawProduct rp where rp.type = :type"),
        @NamedQuery(name = "raw.product.find.by.um", query = "from RawProduct rp where rp.um = :um"),
        @NamedQuery(name = "raw.product.find.by.name", query = "from RawProduct rp where rp.name = :name"),
        @NamedQuery(name = "raw.product.find.by.name.pattern", query = "from RawProduct rp where rp.name like :name"),
        @NamedQuery(name = "raw.product.find.by.name.pattern.and.type", query = "from RawProduct rp where rp.name like :name and rp.type = :type"),
})
public class RawProduct implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 64, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 32, nullable = false)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "UM", length = 6, nullable = false)
    private UM um;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDED_AT", nullable = false)
    private Date addedAt;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public UM getUm() {
        return um;
    }

    public void setUm(UM um) {
        this.um = um;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawProduct)) return false;

        RawProduct that = (RawProduct) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != that.type) return false;
        if (um != that.um) return false;
        return addedAt != null ? addedAt.equals(that.addedAt) : that.addedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (um != null ? um.hashCode() : 0);
        result = 31 * result + (addedAt != null ? addedAt.hashCode() : 0);
        return result;
    }
}
