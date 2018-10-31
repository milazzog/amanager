package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "PRODUCT_PART")
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
    @JoinColumn(name = "PRODUCT", foreignKey = @ForeignKey(name = "FK_PRODUCT_PART_PRODUCT"))
    private Product product;

    @Override
    public Long getId() {
        return id;
    }
}
