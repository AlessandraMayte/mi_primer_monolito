package pe.edu.utp.mi_primer_monolito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.mi_primer_monolito.model.Gato; // Importamos nuestra entidad Gato

@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface GatoRepository extends JpaRepository<Gato, Long> {
    // Spring Data JPA automáticamente generará la implementación para:
    // - save(Gato gato): Guarda o actualiza un gato
    // - findById(Long id): Busca un gato por su ID
    // - findAll(): Devuelve todos los gatos
    // - deleteById(Long id): Elimina un gato por su ID
    // Y muchos más...

    // Puedes añadir métodos personalizados aquí si necesitas consultas específicas, por ejemplo:
    // List<Gato> findByRaza(String raza);
    // List<Gato> findByEdadGreaterThan(Integer edad);
}