package pe.edu.utp.mi_primer_monolito.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importamos para manejo de transacciones
import pe.edu.utp.mi_primer_monolito.model.Gato;
import pe.edu.utp.mi_primer_monolito.repository.GatoRepository; // Asegúrate de que el paquete 'Repository' sea correcto
import pe.edu.utp.mi_primer_monolito.service.GatoService;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class GatoServiceImpl implements GatoService {

    private final GatoRepository gatoRepository;

    @Autowired // Inyecta GatoRepository automáticamente
    public GatoServiceImpl(GatoRepository gatoRepository) {
        this.gatoRepository = gatoRepository;
    }

    @Override
    @Transactional(readOnly = true) // La transacción es de solo lectura, optimiza el rendimiento para consultas
    public List<Gato> obtenerTodosLosGatos() {
        return gatoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gato> obtenerGatoPorId(Long id) {
        return gatoRepository.findById(id);
    }

    @Override
    @Transactional // Las operaciones de guardar y actualizar modifican la base de datos
    public Gato guardarGato(Gato gato) {
        return gatoRepository.save(gato);
    }

    @Override
    @Transactional
    public void eliminarGato(Long id) {
        // La lógica correcta: verificar si existe ANTES de intentar eliminar
        Optional<Gato> gatoExistente = gatoRepository.findById(id);
        if (gatoExistente.isPresent()) {
            gatoRepository.deleteById(id); // Solo se elimina si se encontró
        } else {
            // Lanza la excepción si el gato no fue encontrado para eliminar
            throw new RuntimeException("Gato con ID " + id + " no encontrado para eliminar.");
        }
    }

    @Override
    @Transactional
    public Gato actualizarGato(Long id, Gato gatoDetalles) {
        // Primero, intentamos encontrar el gato existente
        Optional<Gato> gatoExistenteOptional = gatoRepository.findById(id);

        if (gatoExistenteOptional.isPresent()) {
            Gato gatoExistente = gatoExistenteOptional.get();

            // Actualizamos solo los campos que vienen en gatoDetalles
            if (gatoDetalles.getNombre() != null) {
                gatoExistente.setNombre(gatoDetalles.getNombre());
            }
            if (gatoDetalles.getRaza() != null) {
                gatoExistente.setRaza(gatoDetalles.getRaza());
            }
            if (gatoDetalles.getEdad() != null) {
                gatoExistente.setEdad(gatoDetalles.getEdad());
            }
            if (gatoDetalles.getColor() != null) {
                gatoExistente.setColor(gatoDetalles.getColor());
            }
            if (gatoDetalles.getFechaNacimiento() != null) {
                gatoExistente.setFechaNacimiento(gatoDetalles.getFechaNacimiento());
            }
            // Si el propietario se actualiza, debería manejarse con cuidado si viene de un DTO separado
            // Por ahora, no permitiremos la actualización directa del propietario desde aquí para simplificar
            // if (gatoDetalles.getPropietario() != null) {
            //     gatoExistente.setPropietario(gatoDetalles.getPropietario());
            // }

            return gatoRepository.save(gatoExistente); // Guarda el gato actualizado
        } else {
            // En un caso real, aquí podrías lanzar una excepción de "No encontrado"
            // Por ahora, podrías devolver null o lanzar una RuntimeException simple.
            // Para una gestión más robusta de errores, crearemos una excepción personalizada más adelante.
            throw new RuntimeException("Gato con ID " + id + " no encontrado para actualizar.");
        }
    }
}