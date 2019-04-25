package com.eduardosergio.TFG_server.negocio.passwordSynchronizerREST.entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "CredencialesREST.buscarPorUsuario", query = "select u from CredencialesREST u where u.user = :user")
})
public class CredencialesREST implements Serializable{

    

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column
    @Id
    protected long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String user;

    @NotBlank
    @Column(nullable = false)
    private String pass;

    @Version
    protected long version;

    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public CredencialesREST() {
    }

    public CredencialesREST(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    /****************************
     ****** OTHER METHODS *******
     ****************************/

    @Override
    public String toString() {
        return "Usuario{" +
                "  id=" + id +
                ", nombre='" + user +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredencialesREST)) return false;
        CredencialesREST d = (CredencialesREST) o;
        return getVersion() == d.getVersion() &&
                Objects.equals(getId(), d.getId()) &&
                Objects.equals(getUser(), d.getUser()) &&
                Objects.equals(getPass(), d.getPass());
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredencialesREST)) return false;
        CredencialesREST d = (CredencialesREST) o;
        return Objects.equals(getId(), d.getId()) &&
        		Objects.equals(getUser(), d.getUser()) &&
                Objects.equals(getPass(), d.getPass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getVersion());
    }


}


