package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by gmilazzo on 25/10/2018.
 */
@Entity
@Table(name = "EXPENSE_NOTE")
@NamedQueries({
        @NamedQuery(name = "raw.product.find.by.type", query = "from RawProduct rp where rp.type = :type")
})
public class ExpenseNote implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREDIT_NOTE", foreignKey = @ForeignKey(name = "FK_EXPENSE_NOTE_CREDIT_NOTE"))
    private CreditNote creditNote;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "expenseNote")
    private Set<RawProductRegistry> rawProductRegistries;

    @Override
    public Long getId() {
        return id;
    }
}
