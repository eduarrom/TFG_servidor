package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Empleado.listar", query = "FROM Empleado"),
        @NamedQuery(name = "Empleado.buscarPorEmail", query = "from Empleado e where e.email = :email")

})
//@XmlRootElement(name = "Empleado")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlClassExtractor(EmpleadoClassExtractor.class)
/*@XmlSeeAlso({
        EmpleadoTParcial.class,
        EmpleadoTCompleto.class
})*/
public abstract class Empleado implements Serializable {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/


    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column/*(name = "id_empleado")*/
    @Id
    protected Long id;

    @NotBlank
    @Column(nullable = false)
    protected String nombre;


    @NotBlank
    @Column(nullable = false, unique = true)
    @Email
    protected String email;


    @Column(nullable = false)
    protected String password;

    @NotNull
    @Column(nullable = false)
    protected Rol rol;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @XmlInverseReference(mappedBy = "empleados")
    protected Departamento departamento;

    //@OneToMany(mappedBy = "proyecto")
    //protected List<EmpleadoProyecto> proyectos;

    @Version
    protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/


    public Empleado() {
    }


    public Empleado(String nombre, String password, Rol rol) {
        this.nombre = nombre;
        this.password = password;
        this.email = nombre.toLowerCase().concat("@gmail.com");
        this.rol = rol;
    }


    /**
     * Constructor
     * Rol por defecto = Rol.EMPLEADO
     */
    public Empleado(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.rol = Rol.EMPLEADO;
        this.email = nombre.toLowerCase().concat("@gmail.com");
    }

    public Empleado(Long id, String nombre, String password, String email, Rol rol, long version) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.email = email;
        this.version = version;
    }

    public Empleado(String nombre, String password, String email, Rol rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.email = email;

    }


    /****************************
     ********** METODOS *********
     ****************************/

    public abstract double calcularNominaMes();


    /****************************
     **** GETTERS AND SETTERS ***
     ****************************/

    //@XmlAttribute(name = "id", required = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    //@XmlElement(name = "nombre", required = true)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    //@XmlElement(name = "password", required = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }


    //@XmlElement(name = "version", required = true)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    //@XmlElement(name = "email", required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }


    /****************************
     ****** OTHER METHODS *******
     ****************************/

    @Override
    public String toString() {
        return "Empleado{" +
                "  id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", dept='" + departamento.getSiglas() + '\'' +
                ", version=" + version +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return getVersion() == empleado.getVersion() &&
                Objects.equals(getId(), empleado.getId()) &&
                Objects.equals(getNombre(), empleado.getNombre()) &&
                Objects.equals(getEmail(), empleado.getEmail()) &&
                Objects.equals(getPassword(), empleado.getPassword()) &&
                getRol() == empleado.getRol();
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(getId(), empleado.getId()) &&
                Objects.equals(getNombre(), empleado.getNombre()) &&
                Objects.equals(getEmail(), empleado.getEmail()) &&
                Objects.equals(getPassword(), empleado.getPassword()) &&
                Objects.equals(getRol(), empleado.getRol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getPassword(), getRol(), getVersion());
    }
}
