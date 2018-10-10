package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmilazzo on 02/10/2018.
 */
@Entity
@Table(name = "MUNICIPALITY", uniqueConstraints = @UniqueConstraint(name = "MUNICIPALITY_BELFIORE_CODE_UNIQUE", columnNames = "BELFIORE_CODE"))
@NamedQueries({
        @NamedQuery(name = "municipality.find.all.provinces", query = "from Municipality m where m.chiefTown is true"),
        @NamedQuery(name = "municipality.find.all.municipalies", query = "from Municipality m where m.chiefTown is false"),
        @NamedQuery(name = "municipality.find.by.belfiore.code", query = "from Municipality m where m.belfioreCode = :belfioreCode"),
        @NamedQuery(name = "municipality.find.by.name", query = "from Municipality m where m.name = :name"),
        @NamedQuery(name = "municipality.find.by.name.pattern", query = "from Municipality m where m.name like :name"),
        @NamedQuery(name = "municipality.find.by.province", query = "from Municipality m where m.province = :province"),
        @NamedQuery(name = "municipality.find.by.zip", query = "from Municipality m where m.zip = :zip"),
        @NamedQuery(name = "municipality.find.by.zip.pattern", query = "from Municipality m where m.zip like :zip")
})
public class Municipality implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "BELFIORE_CODE", length = 4, nullable = false)
    private String belfioreCode;

    @Column(name = "NAME", length = 256, nullable = false)
    private String name;

    @Column(name = "CHIEF_TOWN", nullable = false)
    private boolean chiefTown;

    @Column(name = "PROVINCE", length = 2, nullable = false)
    private String province;

    @Column(name = "ZIP", length = 5, nullable = false)
    private String zip;

    @Override
    public Long getId() {
        return id;
    }

    public String getBelfioreCode() {
        return belfioreCode;
    }

    public void setBelfioreCode(String belfioreCode) {
        this.belfioreCode = belfioreCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChiefTown() {
        return chiefTown;
    }

    public void setChiefTown(boolean chiefTown) {
        this.chiefTown = chiefTown;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Municipality)) return false;

        Municipality that = (Municipality) o;

        if (chiefTown != that.chiefTown) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (belfioreCode != null ? !belfioreCode.equals(that.belfioreCode) : that.belfioreCode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        return zip != null ? zip.equals(that.zip) : that.zip == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (belfioreCode != null ? belfioreCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (chiefTown ? 1 : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }
}
