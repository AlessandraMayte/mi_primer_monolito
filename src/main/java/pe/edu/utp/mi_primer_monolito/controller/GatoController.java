package pe.edu.utp.mi_primer_monolito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todas las anotaciones de mapping
import pe.edu.utp.mi_primer_monolito.model.Gato;
import pe.edu.utp.mi_primer_monolito.service.GatoService;
import java.util.List;

@RestController // Combina @Controller y @ResponseBody (para devolver datos JSON/XML)
@RequestMapping("/api/gatos") // Define la ruta base para todos los endpoints de este controlador
public class GatoController {

    private final GatoService gatoService;

    @Autowired // Inyecta el servicio de gato
    public GatoController(GatoService gatoService) {
        this.gatoService = gatoService;
    }

    // Endpoint para obtener todos los gatos
    // GET http://localhost:8080/api/gatos
    @GetMapping
    public ResponseEntity<List<Gato>> obtenerTodosLosGatos() {
        List<Gato> gatos = gatoService.obtenerTodosLosGatos();
        return ResponseEntity.ok(gatos); // Retorna una respuesta 200 OK con la lista de gatos
    }

    // Endpoint para obtener un gato por su ID
    // GET http://localhost:8080/api/gatos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Gato> obtenerGatoPorId(@PathVariable Long id) {
        // Usa Optional para manejar el caso de que el gato no sea encontrado
        return gatoService.obtenerGatoPorId(id)
                .map(gato -> ResponseEntity.ok(gato)) // Si se encuentra, retorna 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no se encuentra, retorna 404 Not Found
    }

    // Endpoint para crear un nuevo gato
    // POST http://localhost:8080/api/gatos
    // Body: { "nombre": "Michi", "raza": "Siamés", "edad": 2, "color": "Crema", "fechaNacimiento": "2023-01-15" }
    @PostMapping
    public ResponseEntity<Gato> crearGato(@RequestBody Gato gato) {
        Gato nuevoGato = gatoService.guardarGato(gato);
        // Retorna 201 Created y el gato creado
        return new ResponseEntity<>(nuevoGato, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un gato existente
    // PUT http://localhost:8080/api/gatos/{id}
    // Body: { "nombre": "Bigotes", "edad": 3 } (solo los campos a actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<Gato> actualizarGato(@PathVariable Long id, @RequestBody Gato gatoDetalles) {
        try {
            Gato gatoActualizado = gatoService.actualizarGato(id, gatoDetalles);
            return ResponseEntity.ok(gatoActualizado); // Retorna 200 OK y el gato actualizado
        } catch (RuntimeException e) {
            // Si el gato no se encuentra, el servicio lanza una RuntimeException.
            // Aquí la capturamos y devolvemos 404.
            // Para un manejo de errores más robusto, se usarían excepciones personalizadas.
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un gato
    // DELETE http://localhost:8080/api/gatos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGato(@PathVariable Long id) {
        try {
            gatoService.eliminarGato(id); // Llama al servicio, que ahora manejará la existencia
            return ResponseEntity.noContent().build(); // Retorna 204 No Content si el servicio no lanza excepción
        } catch (RuntimeException e) {
            // Si el servicio lanza una RuntimeException (gato no encontrado), la capturamos
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }
}