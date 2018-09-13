package com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleadoTParcial;
import com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad.EmpleadoProyecto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Empleado.listar", query = "FROM Empleado"),
        @NamedQuery(name = "Empleado.buscarPorEmail", query = "from Empleado e where e.email = :email"),
        @NamedQuery(name = "Empleado.eliminarByID", query = "delete from Empleado where id = :id")

})
public abstract class Empleado implements Serializable {

    /****************************
     ********* ATRIBUTOS ********
     ****************************/


    private final static Logger log = LoggerFactory.getLogger(Empleado.class);

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
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


    @ManyToOne(fetch = FetchType.EAGER)
    protected Departamento departamento = new Departamento();


    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    protected Collection<EmpleadoProyecto> proyectos = new ArrayList<>();


    @Version
    protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/


    public Empleado() {
    }

    public Empleado(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.email = nombre.toLowerCase().concat("@gmail.com");
    }


    public Empleado(Long id, String nombre, String password, String email, long version) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.version = version;
    }

    public Empleado(String nombre, String password, String email) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
    }

    public Empleado(Long id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.email = nombre.toLowerCase().concat("@gmail.com");
    }

    /**
     * Copia el empleado con:
     * - Departamento vacio
     * - Lista de proyectos vacia
     *
     * @param e Empleado
     */
    public Empleado(Empleado e) {
        this.id = e.id;
        this.nombre = e.nombre;
        this.password = e.password;
        this.email = e.email;
        this.version = e.version;
    }




    /****************************
     ********** METODOS *********
     ****************************/

    public abstract double calcularNominaMes();

    public abstract TEmpleadoCompleto crearTransferCompleto();

    public abstract TEmpleado crearTransferSimple();

    public static Empleado crearEmpleado(TEmpleado te){
        Empleado e = null;
        if(te instanceof TEmpleadoTCompleto){
            e = new EmpleadoTCompleto(te.getId(), te.getNombre(), te.getPassword(),
                    ((TEmpleadoTCompleto) te).getAntiguedad(), ((TEmpleadoTCompleto) te).getSueldoBase());
        }else if(te instanceof TEmpleadoTParcial){
            e = new EmpleadoTParcial(te.getId(), te.getNombre(), te.getPassword(),
                    ((TEmpleadoTParcial) te).getHorasJornada(), ((TEmpleadoTParcial) te).getPrecioHora());
        }

        return e;
    }



    public void agregarProyecto(EmpleadoProyecto ep) {
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
                ", dept='" + ((departamento == null) ? "null" : departamento.getSiglas()) + '\'' +
                ", proySize=" + ((proyectos == null) ? "0" : proyectos.size()) +
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
                Objects.equals(getDepartamento(), empleado.getDepartamento()) &&
                Objects.equals(proyectos, empleado.proyectos);
    }


    @Override
    public int hashCode() {

        return Objects.hash(getId(), getNombre(), getEmail(), getPassword(),
                getDepartamento(), proyectos, getVersion());
    }
}
