package cosplay;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jereczem on 18.11.15.
 */
public class CosplayEntityPK implements Serializable {
    private int idCosplay;

    @Column(name = "id_cosplay", nullable = false, insertable = true, updatable = true)
    @Id
    public int getIdCosplay() {
        return idCosplay;
    }

    public void setIdCosplay(int idCosplay) {
        this.idCosplay = idCosplay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CosplayEntityPK that = (CosplayEntityPK) o;

        if (idCosplay != that.idCosplay) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCosplay;
        return result;
    }
}
