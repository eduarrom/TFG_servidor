package com.rodrigo.TFG_server.Negocio.Modulo_Recurso.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Recurso.listar", query = "FROM Empleado"),
})
@XmlRootElement(name = "Recurso")
public class Recurso implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column
    @Id protected Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;


    @OneToMany
    private List<ProyectoRecurso> proyectos;


    @Version protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/


    public Recurso(String nombre, String password) {
        this.nombre = nombre;
    }


    public Recurso(Long id, String nombre, String password, String email, long version) {
        this.id = id;
        this.nombre = nombre;
        this.version = version;
    }

    public Recurso(String nombre, String password, String email) {
        this.nombre = nombre;

    }

    public Recurso() {
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



    @XmlElement(name = "version", required = true)
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
        return "Departamento{" +
                "  id=" + id +
                ", nombre='" + nombre + '\'' +
                ", version=" + version +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recurso)) return false;
        Recurso recurso = (Recurso) o;
        return getVersion() == recurso.getVersion() &&
                Objects.equals(getId(), recurso.getId()) &&
                Objects.equals(getNombre(), recurso.getNombre());
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recurso)) return false;
        Recurso recurso = (Recurso) o;
        return  Objects.equals(getId(), recurso.getId()) &&
                Objects.equals(getNombre(), recurso.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getVersion());
    }
}

