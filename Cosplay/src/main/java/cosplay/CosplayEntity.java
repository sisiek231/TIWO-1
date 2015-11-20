package cosplay;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by jereczem on 18.11.15.
 */
@Entity
@Table(name = "cosplay", schema = "", catalog = "cosplay")
@IdClass(CosplayEntityPK.class)
public class CosplayEntity {
    private int idCosplay;
    private Timestamp date;
    private String characterName;
    private boolean isFavourite;
    private UsersEntity usersByUsersId;
    private FranchiseEntity franchiseByFranchiseId;

    @Id
    @Column(name = "id_cosplay", nullable = false, insertable = true, updatable = true)
    public int getIdCosplay() {
        return idCosplay;
    }

    public void setIdCosplay(int idCosplay) {
        this.idCosplay = idCosplay;
    }

    @Basic
    @Column(name = "date", nullable = true, insertable = true, updatable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "character_name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Basic
    @Column(name = "is_favourite", nullable = false, insertable = true, updatable = true)
    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CosplayEntity that = (CosplayEntity) o;

        if (idCosplay != that.idCosplay) return false;
        if (isFavourite != that.isFavourite) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (characterName != null ? !characterName.equals(that.characterName) : that.characterName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCosplay;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (characterName != null ? characterName.hashCode() : 0);
        result = 31 * result + (isFavourite ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id_users", nullable = false)
    public UsersEntity getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(UsersEntity usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
    }

    @ManyToOne
    @JoinColumn(name = "franchise_id", referencedColumnName = "id_franchise", nullable = false)
    public FranchiseEntity getFranchiseByFranchiseId() {
        return franchiseByFranchiseId;
    }

    public void setFranchiseByFranchiseId(FranchiseEntity franchiseByFranchiseId) {
        this.franchiseByFranchiseId = franchiseByFranchiseId;
    }
}
