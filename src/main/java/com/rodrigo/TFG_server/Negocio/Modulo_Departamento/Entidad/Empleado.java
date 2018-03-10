package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Empleado.listar", query = "FROM Empleado"),
        @NamedQuery(name = "Empleado.buscarPorEmail", query = "from Empleado e where e.email = :email")

})
@XmlRootElement(name = "Empleado")
public class Departamento implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column()
    @Id protected Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;


    @NotBlank
    @Column(nullable = false, unique = true)
    @Email
    protected String email;


    @Column(nullable = false)
    private String password;


    @Version protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/


    public Departamento(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.email = nombre.toLowerCase().concat("@gmail.com");
    }


    public Departamento(Long id, String nombre, String password, String email, long version) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.version = version;
    }

    public Departamento(String nombre, String password, String email) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;

    }

    public Departamento() {
    }

    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/

    @XmlElement(name = "id", required = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "nombre", required = true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "password", required = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @XmlElement(name = "version", required = true)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @XmlElement(name = "email", required = true)
    public String getEmail() { return email;}

    public void setEmail(String email) {this.email = email;}



    /****************************
     ****** OTHER METHODS *******
     ****************************/

    @Override
    public String toString() {
        return "Departamento{" +
                "  id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + email + '\'' +
                ", version=" + version +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento empleado = (Departamento) o;
        return getVersion() == empleado.getVersion() &&
                Objects.equals(getId(), empleado.getId()) &&
                Objects.equals(getNombre(), empleado.getNombre()) &&
                Objects.equals(getEmail(), empleado.getEmail()) &&
                Objects.equals(getPassword(), empleado.getPassword());
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento empleado = (Departamento) o;
        return  Objects.equals(getId(), empleado.getId()) &&
                Objects.equals(getNombre(), empleado.getNombre()) &&
                Objects.equals(getEmail(), empleado.getEmail()) &&
                Objects.equals(getPassword(), empleado.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getPassword(), getVersion());
    }
}
