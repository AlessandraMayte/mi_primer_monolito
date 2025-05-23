package pe.edu.utp.mi_primer_monolito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Importante para devolver vistas
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping;
// No se necesita @RequestMapping a nivel de clase si solo tiene un endpoint a la raíz
// import org.springframework.web.bind.annotation.RequestMapping; // No necesario aquí

import pe.edu.utp.mi_primer_monolito.model.Propietario; // Necesario para la lista de propietarios
import pe.edu.utp.mi_primer_monolito.service.PropietarioService; // Necesario para obtener los propietarios

import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring MVC para servir vistas
public class IndexController {

    // Inyectamos el servicio de PropietarioService porque necesitamos obtener la lista de propietarios
    // y sus gatos asociados (gracias a la relación @OneToMany en Propietario)
    private final PropietarioService propietarioService;

    @Autowired // Constructor para inyectar el servicio
    public IndexController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    // Endpoint para la página principal
    // Mapea la raíz "/" y "/index" a este método
    @GetMapping({"/", "/index", "/clientes-felinos"}) // Puedes añadir "/clientes-felinos" para una URL más descriptiva
    public String mostrarPaginaPrincipal(Model model) {
        // 1. Obtener la lista de propietarios desde el servicio
        List<Propietario> propietarios = propietarioService.obtenerTodosLosPropietarios();

        // 2. Añadir la lista de propietarios al objeto Model.
        // El nombre "propietarios" aquí debe coincidir con el que usas en th:each="${propietarios}" en tu HTML.
        model.addAttribute("propietarios", propietarios);

        // 3. Devolver el nombre de la plantilla Thymeleaf.
        // Spring Boot buscará automáticamente este archivo en src/main/resources/templates/index.html.
        return "index";
    }
}