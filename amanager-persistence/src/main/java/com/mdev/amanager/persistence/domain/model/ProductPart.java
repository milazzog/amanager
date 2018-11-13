package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "PRODUCT_PART")
@NamedQueries({
        @NamedQuery(name = "product.part.find.by.product", query = "from ProductPart pp join pp.productPartRegistry ppr where ppr.product = :product")
})
public class ProductPart implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "RAW_PRODUCT", foreignKey = @ForeignKey(name = "FK_PRODUCT_PART_RAW_PRODUCT"))
    private RawProduct rawProduct;

    @Column(name = "QUANTITY", precision = 8, scale = 2, nullable = false)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PRODUCT_PART_REGISTRY", foreignKey = @ForeignKey(name = "FK_PRODUCT_PART_PRODUCT_PART_REGISTRY"))
    private ProductPartRegistry productPartRegistry;

    @Override
    public Long getId() {
        return id;
    }

    public RawProduct getRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(RawProduct rawProduct) {
        this.rawProduct = rawProduct;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public ProductPartRegistry getProductPartRegistry() {
        return productPartRegistry;
    }

    public void setProductPartRegistry(ProductPartRegistry productPartRegistry) {
        this.productPartRegistry = productPartRegistry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPart)) return false;

        ProductPart that = (ProductPart) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (rawProduct != null ? !rawProduct.equals(that.rawProduct) : that.rawProduct != null) return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rawProduct != null ? rawProduct.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
