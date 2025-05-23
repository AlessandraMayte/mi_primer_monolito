package pe.edu.utp.mi_primer_monolito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.mi_primer_monolito.model.Gato;
import pe.edu.utp.mi_primer_monolito.repository.GatoRepository; // Asegúrate de que el paquete 'Repository' sea correcto
import pe.edu.utp.mi_primer_monolito.service.impl.GatoServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // Para aserciones como assertEquals, assertTrue, etc.
import static org.mockito.Mockito.*; // Para when, verify, any, etc.

@ExtendWith(MockitoExtension.class) // Habilita la integración de Mockito con JUnit 5
public class GatoServiceTest {

    @Mock // Crea un objeto simulado (mock) de GatoRepository
    private GatoRepository gatoRepository;

    @InjectMocks // Inyecta los mocks creados (gatoRepository) en esta instancia de GatoServiceImpl
    private GatoServiceImpl gatoService;

    private Gato gato1;
    private Gato gato2;

    @BeforeEach // Se ejecuta antes de cada método de prueba
    void setUp() {
        gato1 = new Gato("Bigotes", "Mestizo", 3, "Negro", LocalDate.of(2022, 1, 15));
        gato1.setId(1L); // Asignamos un ID para simular un gato existente

        gato2 = new Gato("Luna", "Siamés", 2, "Blanco", LocalDate.of(2023, 3, 10));
        gato2.setId(2L); // Asignamos un ID para simular un gato existente
    }

    @Test
    void testObtenerTodosLosGatos() {
        // Simular el comportamiento del repositorio: cuando se llame a findAll(), que devuelva esta lista
        when(gatoRepository.findAll()).thenReturn(Arrays.asList(gato1, gato2));

        // Llamar al método del servicio que estamos probando
        List<Gato> gatos = gatoService.obtenerTodosLosGatos();

        // Verificar el resultado
        assertNotNull(gatos);
        assertEquals(2, gatos.size());
        assertEquals("Bigotes", gatos.get(0).getNombre());
        assertEquals("Luna", gatos.get(1).getNombre());

        // Verificar que el método findAll() del repositorio fue llamado exactamente una vez
        verify(gatoRepository, times(1)).findAll();
    }

    @Test
    void testObtenerGatoPorIdExistente() {
        // Simular el comportamiento: cuando se llame a findById(1L), que devuelva un Optional con gato1
        when(gatoRepository.findById(1L)).thenReturn(Optional.of(gato1));

        // Llamar al método del servicio
        Optional<Gato> resultado = gatoService.obtenerGatoPorId(1L);

        // Verificar el resultado
        assertTrue(resultado.isPresent());
        assertEquals(gato1.getNombre(), resultado.get().getNombre());

        verify(gatoRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerGatoPorIdNoExistente() {
        // Simular el comportamiento: cuando se llame a findById(cualquier ID), que devuelva un Optional vacío
        when(gatoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Optional<Gato> resultado = gatoService.obtenerGatoPorId(99L); // ID que no existe

        // Verificar el resultado
        assertFalse(resultado.isPresent());

        verify(gatoRepository, times(1)).findById(99L);
    }

    @Test
    void testGuardarGato() {
        Gato nuevoGato = new Gato("Pelusa", "Persa", 1, "Gris", LocalDate.of(2024, 2, 20));
        // Simular que save() devuelve el gato con un ID asignado (simula la BD)
        when(gatoRepository.save(any(Gato.class))).thenReturn(gato1); // Usamos gato1 como retorno simulado

        // Llamar al método del servicio
        Gato gatoGuardado = gatoService.guardarGato(nuevoGato);

        // Verificar el resultado
        assertNotNull(gatoGuardado);
        assertEquals(gato1.getNombre(), gatoGuardado.getNombre()); // O el nombre del nuevoGato si lo simulaste correctamente

        // Verificar que save() fue llamado con cualquier instancia de Gato
        verify(gatoRepository, times(1)).save(any(Gato.class));
    }

    @Test
    void testEliminarGatoExistente() {
        // Simular que el gato existe (ahora el servicio lo va a buscar)
        when(gatoRepository.findById(1L)).thenReturn(Optional.of(gato1));
        // No necesitamos que deleteById devuelva nada, solo verificar que se llama
        doNothing().when(gatoRepository).deleteById(1L);

        // Llamar al método del servicio
        gatoService.eliminarGato(1L);

        // Verificar que findById y deleteById fueron llamados
        verify(gatoRepository, times(1)).findById(1L); // Ahora verificamos esta llamada
        verify(gatoRepository, times(1)).deleteById(1L);
    }


    @Test
    void testActualizarGatoExistente() {
        Gato gatoActualizadoDetalles = new Gato();
        gatoActualizadoDetalles.setNombre("Bigotes Modificado");
        gatoActualizadoDetalles.setEdad(4);

        // Simular que findById devuelve el gato original (gato1)
        when(gatoRepository.findById(1L)).thenReturn(Optional.of(gato1));

        // Cuando se llame a save con CUALQUIER instancia de Gato,
        // queremos que devuelva el gatoExistente DESPUÉS DE LA ACTUALIZACIÓN
        when(gatoRepository.save(any(Gato.class))).thenAnswer(invocation -> {
            Gato gatoQueSeVaAGuardar = invocation.getArgument(0); // Captura el gato que el servicio intenta guardar
            // Para la simulación, podemos simplemente devolver ese mismo gato
            // o si queremos ser más fieles a una base de datos que asigna ID si no lo tiene,
            // podemos replicar el comportamiento.
            // Para este caso, el servicio ya actualizó las propiedades de gatoExistente,
            // y luego se lo pasa a save. Así que el objeto pasado a save es ya el actualizado.
            return gatoQueSeVaAGuardar;
        });

        // Llamar al método del servicio
        Gato resultado = gatoService.actualizarGato(1L, gatoActualizadoDetalles);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals("Bigotes Modificado", resultado.getNombre());
        assertEquals(4, resultado.getEdad());
        // Ahora, la raza no debería ser null porque el servicio la mantuvo si no se envió en detalles
        assertEquals("Mestizo", resultado.getRaza()); // Ahora esto debería pasar
        assertEquals(gato1.getColor(), resultado.getColor()); // El color tampoco debería cambiar

        // Verificar que findById y save fueron llamados
        verify(gatoRepository, times(1)).findById(1L);
        verify(gatoRepository, times(1)).save(any(Gato.class));
    }

    @Test
    void testEliminarGatoNoExistenteLanzaExcepcion() {
        // Simular que el gato NO existe
        when(gatoRepository.findById(99L)).thenReturn(Optional.empty());

        // Verificar que el método lanza una RuntimeException cuando el gato no existe
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            gatoService.eliminarGato(99L);
        });

        assertEquals("Gato con ID 99 no encontrado para eliminar.", thrown.getMessage());

        verify(gatoRepository, times(1)).findById(99L);
        verify(gatoRepository, never()).deleteById(anyLong()); // Asegurarse de que delete nunca se llama
    }
}