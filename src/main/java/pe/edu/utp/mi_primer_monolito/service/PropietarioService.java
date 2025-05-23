package pe.edu.utp.mi_primer_monolito.service;

import pe.edu.utp.mi_primer_monolito.model.Propietario;
import java.util.List;
import java.util.Optional;

public interface PropietarioService {

    List<Propietario> obtenerTodosLosPropietarios(); // Obtiene todos los propietarios
    Optional<Propietario> obtenerPropietarioPorId(Long id); // Obtiene un propietario por su ID
    Propietario guardarPropietario(Propietario propietario); // Guarda un nuevo propietario o actualiza uno existente
    void eliminarPropietario(Long id); // Elimina un propietario por su ID
    Propietario actualizarPropietario(Long id, Propietario propietarioDetalles); // Actualiza la información de un propietario
    List<Propietario> obtenerSoloPropietarios();
}