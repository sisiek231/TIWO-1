package cosplay;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by jereczem on 18.11.15.
 */
@Entity
@Table(name = "users", schema = "", catalog = "cosplay")
public class UsersEntity {
    private int idUsers;
    private String nick;
    private int age;
    private Collection<CosplayEntity> cosplaysByIdUsers;

    @Override
    public String toString() {
        return "UsersEntity{" +
                "idUsers=" + idUsers +
                ", nick='" + nick + '\'' +
                ", age=" + age +
                "\n, cosplaysByIdUsers=" + cosplaysByIdUsers +
                '}';
    }

    @Id
    @Column(name = "id_users", nullable = false, insertable = true, updatable = true)
    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    @Basic
    @Column(name = "nick", nullable = false, insertable = true, updatable = true, length = 45)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Basic
    @Column(name = "age", nullable = false, insertable = true, updatable = true)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUsers != that.idUsers) return false;
        if (age != that.age) return false;
        if (nick != null ? !nick.equals(that.nick) : that.nick != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsers;
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @OneToMany(mappedBy = "usersByUsersId", orphanRemoval=true, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE, CascadeType.REFRESH })
    public Collection<CosplayEntity> getCosplaysByIdUsers() {
        return cosplaysByIdUsers;
    }

    public void setCosplaysByIdUsers(Collection<CosplayEntity> cosplaysByIdUsers) {
        this.cosplaysByIdUsers = cosplaysByIdUsers;
    }
}
