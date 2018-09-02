package com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Empleado;
import com.rodrigo.TFG_server.Negocio.Modulo_Empleado.Entidad.Transfers.TEmpleado;
//import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;


@Entity
@NamedQueries({
        @NamedQuery(name = "Departamento.listar", query = "FROM Departamento"),
        @NamedQuery(name = "Departamento.buscarPorSiglas", query = "from Departamento e where e.siglas = :siglas"),
        @NamedQuery(name = "Departamento.eliminarByID", query = "delete from Departamento where id = :id")

})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Departamento implements Serializable/*, CycleRecoverable */ {

    private final static Logger log = LoggerFactory.getLogger(Departamento.class);

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


    //@LazyCollection(LazyCollectionOption.FALSE)
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    //@XmlAnyElement(lax = true)
    /*@XmlElements({ //Con esto hace que se guarde la lista como -->
            @XmlElement(name = "EmpleadoTParcial", type = EmpleadoTParcial.class), //<empleados xsi:type="empleadoTParcial">
            @XmlElement(name = "EmpleadoTCompleto", type = EmpleadoTCompleto.class) //<empleados xsi:type="empleadoTCompleto">
    })*/
//    @XmlInverseReference(mappedBy = "departamento")
//    @XmlElementRef
    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)/*(mappedBy="departamento", fetch= FetchType.EAGER, cascade={CascadeType.PERSIST})*/
    private Collection<Empleado> empleados = new ArrayList();

    @Version
    protected long version;


    @Transient
    private double nominaMes;


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

    /**
     * Copia el Departamento con
     * - Listado de empleados vacio
     *
     * @param d
     */
    public Departamento(Departamento d) {
        this.id = d.id;
        this.nombre = d.nombre;
        this.siglas = d.siglas;
        this.version = d.version;
    }

    public Departamento(TDepartamento td) {
        this.id = td.getId();
        this.nombre = td.getNombre();
        this.siglas = td.getSiglas();
    }


    public Departamento(long id) {
        this.id = id;
    }


    /****************************
     ********** METODOS *********
     ****************************/


    public TDepartamento crearTransferSimple() {
        return new TDepartamento(id, nombre, siglas, nominaMes);
    }


    public TDepartamentoCompleto crearTransferCompleto() {

        HashMap<Long, TEmpleado> tEmpleados = new HashMap<>();

        empleados.stream().forEach((e) -> {
            tEmpleados.put(e.getId(), e.crearTransferSimple());
        });


        TDepartamentoCompleto tdc = new TDepartamentoCompleto(crearTransferSimple(), tEmpleados);

        System.out.println("tdc = [" + tdc + "]");

        return tdc;
    }


    public boolean agregarEmpleado(Empleado e) {
        e.setDepartamento(this);
        return this.empleados.add(e);
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


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }


    public Collection<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Collection<Empleado> empleados) {
        this.empleados = empleados;
    }


    public double getNominaMes() {
        return nominaMes;
    }

    public void setNominaMes(double nominaMes) {
        this.nominaMes = nominaMes;
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
                ", EmpleadosSize='" + ((empleados == null) ? "null" : empleados.size()) + '\'' +
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


    /*@Override
    public Object onCycleDetected(Context cycleRecoveryContext) {
        // Context provides access to the Marshaller being used:
        //log.info("JAXB Marshaller is: " + cycleRecoveryContext.getMarshaller());

        System.out.println(" -------- Departamento.onCycleDetected -------- ");
        Object obj;

        //Esta opción peta por no tener un DepartmantePointer como field
        //obj = new DepartmentPointer(this.id);
        //System.out.println("Enviando Departamento pointer por ciclo ");


        // Retorna un departamento vacio
        //Departamento --> empleados --> Departamento(vacio)
        //obj = new Departamento();


        // Retorna un departamento solo con el ID
        //Departamento --> empleados --> Departamento(Solo con el ID)
        //obj = new Departamento(this.id);


        // Retorna un departamento sin los empleados
        // Departamento --> empleados --> Departamento(Sin empleados)
        //obj = new Departamento(this);


        //Con null al detectar el ciclo envia un <departamento/>
        // Departamento --> empleados --> <departamento/>
        obj = null;

        return obj;

    }*/

}



