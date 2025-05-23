package pe.edu.utp.mi_primer_monolito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.mi_primer_monolito.model.Propietario; // Importamos nuestra entidad Propietario

@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    // Similar a GatoRepository, Spring Data JPA nos dará automáticamente:
    // - save(Propietario propietario): Guarda o actualiza un propietario
    // - findById(Long id): Busca un propietario por su ID
    // - findAll(): Devuelve todos los propietarios
    // - deleteById(Long id): Elimina un propietario por su ID
    // Y muchos más métodos predefinidos.

    // Aquí también podrías añadir métodos de consulta personalizados si los necesitas, por ejemplo:
    // List<Propietario> findByApellido(String apellido);
    // Propietario findByTelefono(String telefono);
}