package pe.edu.utp.mi_primer_monolito.service;

import pe.edu.utp.mi_primer_monolito.model.Gato;
import java.util.List;
import java.util.Optional;

public interface GatoService {

    List<Gato> obtenerTodosLosGatos(); // Obtiene todos los gatos
    Optional<Gato> obtenerGatoPorId(Long id); // Obtiene un gato por su ID
    Gato guardarGato(Gato gato); // Guarda un nuevo gato o actualiza uno existente
    void eliminarGato(Long id); // Elimina un gato por su ID
    Gato actualizarGato(Long id, Gato gatoDetalles); // Actualiza la información de un gato existente
}