package cosplay;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by jereczem on 18.11.15.
 */
@Entity
@Table(name = "franchise", schema = "", catalog = "cosplay")
public class FranchiseEntity {
    private int idFranchise;
    private String name;
    private String genre;
    private Collection<CosplayEntity> cosplaysByIdFranchise;

    @Id
    @Column(name = "id_franchise", nullable = false, insertable = true, updatable = true)
    public int getIdFranchise() {
        return idFranchise;
    }

    public void setIdFranchise(int idFranchise) {
        this.idFranchise = idFranchise;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "genre", nullable = false, insertable = true, updatable = true, length = 45)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FranchiseEntity that = (FranchiseEntity) o;

        if (idFranchise != that.idFranchise) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFranchise;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "franchiseByFranchiseId")
    public Collection<CosplayEntity> getCosplaysByIdFranchise() {
        return cosplaysByIdFranchise;
    }

    public void setCosplaysByIdFranchise(Collection<CosplayEntity> cosplaysByIdFranchise) {
        this.cosplaysByIdFranchise = cosplaysByIdFranchise;
    }
}
