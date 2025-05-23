package pe.edu.utp.mi_primer_monolito.model;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import java.util.List; // Para la lista de gatos

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "propietarios") // Define el nombre de la tabla en la base de datos
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 255)
    private String direccion;

    // Relación One-to-Many: Un propietario puede tener muchos gatos
    // "mappedBy" indica que la relación es bidireccional y el campo "propietario" en la entidad Gato
    // es el dueño de la relación.
    // cascade = CascadeType.ALL significa que las operaciones (persist, remove, refresh, merge)
    // realizadas en el propietario se propagarán a sus gatos.
    // orphanRemoval = true significa que si un gato se desvincula de un propietario, se eliminará de la base de datos.
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Gato> gatos; // Lista de gatos de este propietario

    // --- Constructores ---
    public Propietario() {
    }

    public Propietario(String nombre, String apellido, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // --- Getters y Setters ---
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Gato> getGatos() {
        return gatos;
    }

    public void setGatos(List<Gato> gatos) {
        this.gatos = gatos;
    }

    // --- Método toString (útil para depuración, pero ten cuidado con recursión en relaciones bidireccionales) ---
    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                // Evita imprimir la lista de gatos directamente para evitar StackOverflowError en relaciones bidireccionales
                // Solo si sabes que no causará recursión infinita
                // ", gatos=" + (gatos != null ? gatos.size() + " gatos" : "0 gatos") +
                '}';
    }
}