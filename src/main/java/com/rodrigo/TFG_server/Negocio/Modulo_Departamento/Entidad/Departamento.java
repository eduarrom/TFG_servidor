package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.sun.xml.bind.CycleRecoverable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Departamento.listar", query = "FROM Departamento"),
        @NamedQuery(name = "Departamento.buscarPorSiglas", query = "from Departamento e where e.siglas = :siglas")

})
@XmlRootElement(name = "Departamento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Departamento implements Serializable, CycleRecoverable {

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column/*(name = "id_depart")*/
    @Id
    protected long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;


    @NotBlank
    @Column(nullable = false, unique = true)
    private String siglas;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)/*(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)*/
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    //@XmlAnyElement(lax = true)
    private List<Empleado> empleados = new ArrayList<Empleado>();

    @Version
    protected long version;


    /****************************
     ******* CONSTRUCTORES ******
     ****************************/

    public Departamento() {
    }

    public Departamento(String nombre) {
        this.nombre = nombre;
        this.siglas = String.valueOf(
                Arrays.stream(nombre.split(" "))
                        .reduce("", (acum, pal) -> acum + String.valueOf(pal.charAt(0))));
    }


    public Departamento(Long id, String nombre, String siglas, long version) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
        this.version = version;

    }

    public Departamento(String nombre, String siglas) {
        this.nombre = nombre;
        this.siglas = siglas;
    }

    public Departamento(Departamento d) {
        this.id = d.id;
        this.nombre = d.nombre;
        this.siglas = d.siglas;
        this.version = d.version;
    }

    public Departamento(long id) {
        this.id = id;
    }


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


    //@XmlElement(name = "version", required = true)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    //@XmlElement(name = "siglas", required = true)
    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }


    //@XmlElement

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /****************************
     ****** OTHER METHODS *******
     ****************************/

    @Override
    public String toString() {
        return "Departamento{" +
                "  id=" + id +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", EmpleadosSize='" + empleados.size() + '\'' +
                ", version=" + version +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento depto = (Departamento) o;
        return getVersion() == depto.getVersion() &&
                Objects.equals(getId(), depto.getId()) &&
                Objects.equals(getNombre(), depto.getNombre()) &&
                Objects.equals(getSiglas(), depto.getSiglas());
    }

    public boolean equalsWithOutVersion(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento dpto = (Departamento) o;
        return Objects.equals(getId(), dpto.getId()) &&
                Objects.equals(getNombre(), dpto.getNombre()) &&
                Objects.equals(getSiglas(), dpto.getSiglas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getVersion());
    }


    @Override
    public Object onCycleDetected(Context cycleRecoveryContext) {
        // Context provides access to the Marshaller being used:
        //System.out.println("JAXB Marshaller is: " + cycleRecoveryContext.getMarshaller());

        System.out.println(" -------- Departamento.onCycleDetected -------- ");
        Object obj;

        //Esta opción peta por no tener un DepartmantePointer como field
        obj = new DepartmentPointer(this.id);
        System.out.println("Enviando Departamento pointer por ciclo ");


        // Retorna un departamento solo con el ID
        //Departamento --> empleados --> Departamento(Solo con el ID)
        //obj = new Departamento(this.id);


        // Retorna un departamento sin los empleados
        // Departamento --> empleados --> Departamento(Sin empleados)
        //obj = new Departamento(this);


        //Con null al detectar el ciclo envia un <departamento/>
        // Departamento --> empleados --> <departamento/>
        //obj = null;

        return obj;
    }

}



