package pe.edu.utp.mi_primer_monolito.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.mi_primer_monolito.model.Propietario;
import pe.edu.utp.mi_primer_monolito.repository.PropietarioRepository; // Asegúrate de que el paquete 'Repository' sea correcto
import pe.edu.utp.mi_primer_monolito.service.PropietarioService;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioRepository propietarioRepository;

    @Autowired // Inyecta PropietarioRepository automáticamente
    public PropietarioServiceImpl(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> obtenerTodosLosPropietarios() {
        return propietarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Propietario> obtenerPropietarioPorId(Long id) {
        return propietarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Propietario guardarPropietario(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Override
    @Transactional
    public void eliminarPropietario(Long id) {
        propietarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Propietario actualizarPropietario(Long id, Propietario propietarioDetalles) {
        Optional<Propietario> propietarioExistenteOptional = propietarioRepository.findById(id);

        if (propietarioExistenteOptional.isPresent()) {
            Propietario propietarioExistente = propietarioExistenteOptional.get();

            // Actualizar solo los campos que vienen en propietarioDetalles
            if (propietarioDetalles.getNombre() != null) {
                propietarioExistente.setNombre(propietarioDetalles.getNombre());
            }
            if (propietarioDetalles.getApellido() != null) {
                propietarioExistente.setApellido(propietarioDetalles.getApellido());
            }
            if (propietarioDetalles.getTelefono() != null) {
                propietarioExistente.setTelefono(propietarioDetalles.getTelefono());
            }
            if (propietarioDetalles.getDireccion() != null) {
                propietarioExistente.setDireccion(propietarioDetalles.getDireccion());
            }
            // Por ahora, no manejaremos la actualización directa de la lista de gatos desde aquí
            // Si necesitas agregar o quitar gatos, eso se haría a través de métodos específicos en el servicio de gato o propietario.

            return propietarioRepository.save(propietarioExistente);
        } else {
            throw new RuntimeException("Propietario con ID " + id + " no encontrado para actualizar.");
        }
    }

    @Override
    public List<Propietario> obtenerSoloPropietarios() {
        return List.of();
    }
}