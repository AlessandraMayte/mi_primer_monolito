package pe.edu.utp.mi_primer_monolito.model;

import jakarta.persistence.*;
import java.time.LocalDate;
// ¡NUEVA IMPORTACIÓN!
import com.fasterxml.jackson.annotation.JsonBackReference; // Importar para evitar recursión en JSON

@Entity
@Table(name = "gatos")
public class Gato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "raza", length = 100)
    private String raza;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // --- ¡NUEVO CAMPO PARA LA RELACIÓN CON PROPIETARIO! ---
    @ManyToOne(fetch = FetchType.LAZY) // Muchos gatos a Un propietario
    @JoinColumn(name = "id_propietario") // Columna de clave foránea en la tabla 'gatos'
    @JsonBackReference // Para evitar problemas de serialización JSON en relaciones bidireccionales
    private Propietario propietario;

    // --- Constructores ---
    public Gato() {
    }

    // Constructor con campos (excepto ID generado y propietario)
    public Gato(String nombre, String raza, Integer edad, String color, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Constructor con todos los campos (excepto ID generado)
    public Gato(String nombre, String raza, Integer edad, String color, LocalDate fechaNacimiento, Propietario propietario) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
        this.fechaNacimiento = fechaNacimiento;
        this.propietario = propietario;
    }


    // --- Getters y Setters (actualizados para incluir propietario) ---
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    // --- Método toString (ajustado para evitar recursión) ---
    @Override
    public String toString() {
        return "Gato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", color='" + color + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", propietarioId=" + (propietario != null ? propietario.getId() : "null") + // Solo ID del propietario
                '}';
    }
}