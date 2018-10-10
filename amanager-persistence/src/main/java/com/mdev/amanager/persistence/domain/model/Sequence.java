package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;

/**
 * Created by gmilazzo on 06/10/2018.
 */
@Entity
@Table(name = "SEQUENCE", uniqueConstraints = @UniqueConstraint(name = "SEQUENCE_NAME_UNIQUE", columnNames = "NAME"))
@NamedQueries({
        @NamedQuery(name = "sequence.find.by.name", query = "from Sequence s where s.name = :name")
})
public class Sequence implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 64, nullable = false)
    private String name;

    @Column(name = "VALUE", nullable = false)
    private Long value;

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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sequence)) return false;

        Sequence sequence = (Sequence) o;

        if (id != null ? !id.equals(sequence.id) : sequence.id != null) return false;
        if (name != null ? !name.equals(sequence.name) : sequence.name != null) return false;
        return value != null ? value.equals(sequence.value) : sequence.value == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
