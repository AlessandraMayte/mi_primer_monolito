package pe.edu.utp.mi_primer_monolito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.mi_primer_monolito.model.Propietario;
import pe.edu.utp.mi_primer_monolito.repository.PropietarioRepository;
import pe.edu.utp.mi_primer_monolito.service.impl.PropietarioServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropietarioServiceTest {

    @Mock
    private PropietarioRepository propietarioRepository;

    @InjectMocks
    private PropietarioServiceImpl propietarioService;

    private Propietario propietario1;
    private Propietario propietario2;

    @BeforeEach
    void setUp() {
        propietario1 = new Propietario("Juan", "Perez", "987654321", "Calle A 123");
        propietario1.setId(1L);

        propietario2 = new Propietario("Maria", "Gonzales", "912345678", "Avenida B 456");
        propietario2.setId(2L);
    }

    @Test
    void testObtenerTodosLosPropietarios() {
        when(propietarioRepository.findAll()).thenReturn(Arrays.asList(propietario1, propietario2));

        List<Propietario> propietarios = propietarioService.obtenerTodosLosPropietarios();

        assertNotNull(propietarios);
        assertEquals(2, propietarios.size());
        assertEquals("Juan", propietarios.get(0).getNombre());
        assertEquals("Maria", propietarios.get(1).getNombre());

        verify(propietarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPropietarioPorIdExistente() {
        when(propietarioRepository.findById(1L)).thenReturn(Optional.of(propietario1));

        Optional<Propietario> resultado = propietarioService.obtenerPropietarioPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(propietario1.getNombre(), resultado.get().getNombre());

        verify(propietarioRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPropietarioPorIdNoExistente() {
        when(propietarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Propietario> resultado = propietarioService.obtenerPropietarioPorId(99L);

        assertFalse(resultado.isPresent());

        verify(propietarioRepository, times(1)).findById(99L);
    }

    @Test
    void testGuardarPropietario() {
        Propietario nuevoPropietario = new Propietario("Luisa", "Diaz", "955443322", "Jr. C 789");
        when(propietarioRepository.save(any(Propietario.class))).thenReturn(propietario1); // Simula el retorno con un ID

        Propietario propietarioGuardado = propietarioService.guardarPropietario(nuevoPropietario);

        assertNotNull(propietarioGuardado);
        assertEquals(propietario1.getNombre(), propietarioGuardado.getNombre());

        verify(propietarioRepository, times(1)).save(any(Propietario.class));
    }

    @Test
    void testEliminarPropietario() {
        // Simular que el propietario existe antes de intentar eliminarlo
        when(propietarioRepository.findById(1L)).thenReturn(Optional.of(propietario1));
        doNothing().when(propietarioRepository).deleteById(1L);

        propietarioService.eliminarPropietario(1L);

        verify(propietarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testActualizarPropietarioExistente() {
        Propietario propietarioActualizadoDetalles = new Propietario();
        propietarioActualizadoDetalles.setTelefono("900111222");
        propietarioActualizadoDetalles.setDireccion("Nueva Dirección 555");

        when(propietarioRepository.findById(1L)).thenReturn(Optional.of(propietario1));
        when(propietarioRepository.save(any(Propietario.class))).thenReturn(propietarioActualizadoDetalles); // Devuelve los detalles actualizados

        Propietario resultado = propietarioService.actualizarPropietario(1L, propietarioActualizadoDetalles);

        assertNotNull(resultado);
        assertEquals("900111222", resultado.getTelefono());
        assertEquals("Nueva Dirección 555", resultado.getDireccion());
        assertEquals(propietario1.getNombre(), resultado.getNombre()); // Nombre no debería haber cambiado

        verify(propietarioRepository, times(1)).findById(1L);
        verify(propietarioRepository, times(1)).save(any(Propietario.class));
    }

    @Test
    void testActualizarPropietarioNoExistenteLanzaExcepcion() {
        Propietario propietarioActualizadoDetalles = new Propietario();
        propietarioActualizadoDetalles.setNombre("Nombre inexistente");

        when(propietarioRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            propietarioService.actualizarPropietario(99L, propietarioActualizadoDetalles);
        });

        assertEquals("Propietario con ID 99 no encontrado para actualizar.", thrown.getMessage());

        verify(propietarioRepository, times(1)).findById(99L);
        verify(propietarioRepository, never()).save(any(Propietario.class));
    }
}