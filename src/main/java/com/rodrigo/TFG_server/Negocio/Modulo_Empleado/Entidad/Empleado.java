package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import org.eclipse.persistence.oxm.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Empleado.listar", query = "FROM Empleado"),
        @NamedQuery(name = "Empleado.buscarPorEmail", query = "from Empleado e where e.email = :email")

})
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlClassExtractor(EmpleadoClassExtractor.class)
//@XmlRootElement
//@XmlTransient
@XmlDiscriminatorNode("@tipo")
//@XmlCustomizer(EmpleadoCustomizer.class)
//@XmlType/*(name = "Empleado")*/
@XmlSeeAlso({
        EmpleadoTCompleto.class,
        EmpleadoTParcial.class
})
public abstract class Empleado implements Serializable/*, CycleRecoverable*/ {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/



    private final static Logger log = LoggerFactory.getLogger(Empleado.class);

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    //@Column(name = "id")
    @Id
    protected Long id;

    @NotBlank
    @Column(nullable = false)
    protected String nombre;


    @NotBlank
    @Column(nullable = false, unique = true)
    @Email
    protected String email;

    @NotBlank
    @Column(nullable = false)
    protected String password;

    @NotNull
    @Column(nullable = false)
    protected Rol rol;


    @ManyToOne(fetch=FetchType.EAGER)/*(fetch = FetchType.EAGER, cascade = CascadeType.ALL)*/
    //@JoinColumn(nullable = false)
    @XmlInverseReference(mappedBy = "empleados")
    //@XmlTransient
    @NotNull
    protected Departamento departamento;
//    protected Departamento departamento = new Departamento();


//    protected List<EmpleadoProyecto> proyectos = null;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@XmlInverseReference(mappedBy = "empleado")
    protected Collection<EmpleadoProyecto> proyectos = new ArrayList<>();




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

    /** Copia el empleado con:
     * - Departamento vacio
     * - Lista de proyectos vacia
     *
     * @param e Empleado
     */
    public Empleado(Empleado e) {
        this.id = e.id;
        this.nombre = e.nombre;
        this.password = e.password;
        this.rol = e.rol;
        this.email = e.email;
        this.version = e.version;
//        this.departamento = new Departamento(e.departamento);
//        this.proyectos = e.proyectos.stream()
//                .map((ep) -> new EmpleadoProyecto(ep))
//                .collect(Collectors.toList());
    }


    /****************************
     ********** METODOS *********
     ****************************/

    public abstract double calcularNominaMes();


    public void agregarProyecto(EmpleadoProyecto ep){
        proyectos.add(ep);
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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


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


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


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


    public Collection<EmpleadoProyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Collection<EmpleadoProyecto> proyectos) {
        this.proyectos = proyectos;
    }

    /****************************
     ****** OTHER METHODS *******
     ****************************/



    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", dept='" + ((departamento==null)?"null": departamento.getSiglas()) + '\'' +
                ", proySize=" +((proyectos==null)?"0": proyectos.size()) +
                ", version=" + version +
                '}';
    }


//    @Override
//    public Object onCycleDetected(Context context) {
//        return null;
//    }

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
                getRol() == empleado.getRol() &&
                Objects.equals(getDepartamento(), empleado.getDepartamento()) &&
                Objects.equals(proyectos, empleado.proyectos);
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(getId(), empleado.getId()) &&
                Objects.equals(getNombre(), empleado.getNombre()) &&
                Objects.equals(getEmail(), empleado.getEmail()) &&
                Objects.equals(getPassword(), empleado.getPassword()) &&
                getRol() == empleado.getRol() &&
                Objects.equals(getDepartamento(), empleado.getDepartamento()) &&
                Objects.equals(proyectos, empleado.proyectos);
    }


    @Override
    public int hashCode() {

        return Objects.hash(getId(), getNombre(), getEmail(), getPassword(), getRol(),
                getDepartamento(), proyectos, getVersion());
    }
}
