package pe.edu.utp.mi_primer_monolito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mi_primer_monolito.model.Propietario;
import pe.edu.utp.mi_primer_monolito.service.PropietarioService;
import java.util.List;

@RestController
@RequestMapping("/api/propietarios") // Define la ruta base para todos los endpoints de este controlador
public class PropietarioController {

    private final PropietarioService propietarioService;

    @Autowired
    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    // Endpoint para obtener todos los propietarios
    // GET http://localhost:8080/api/propietarios
    @GetMapping
    public ResponseEntity<List<Propietario>> obtenerTodosLosPropietarios() {
        List<Propietario> propietarios = propietarioService.obtenerTodosLosPropietarios();
        return ResponseEntity.ok(propietarios);
    }

    // Endpoint para obtener un propietario por su ID
    // GET http://localhost:8080/api/propietarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Propietario> obtenerPropietarioPorId(@PathVariable Long id) {
        return propietarioService.obtenerPropietarioPorId(id)
                .map(propietario -> ResponseEntity.ok(propietario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo propietario
    // POST http://localhost:8080/api/propietarios
    // Body: { "nombre": "Juan", "apellido": "Perez", "telefono": "987654321", "direccion": "Av. Siempre Viva 123" }
    @PostMapping
    public ResponseEntity<Propietario> crearPropietario(@RequestBody Propietario propietario) {
        Propietario nuevoPropietario = propietarioService.guardarPropietario(propietario);
        return new ResponseEntity<>(nuevoPropietario, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un propietario existente
    // PUT http://localhost:8080/api/propietarios/{id}
    // Body: { "telefono": "999888777" } (solo los campos a actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<Propietario> actualizarPropietario(@PathVariable Long id, @RequestBody Propietario propietarioDetalles) {
        try {
            Propietario propietarioActualizado = propietarioService.actualizarPropietario(id, propietarioDetalles);
            return ResponseEntity.ok(propietarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un propietario
    // DELETE http://localhost:8080/api/propietarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPropietario(@PathVariable Long id) {
        if (propietarioService.obtenerPropietarioPorId(id).isPresent()) {
            propietarioService.eliminarPropietario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}