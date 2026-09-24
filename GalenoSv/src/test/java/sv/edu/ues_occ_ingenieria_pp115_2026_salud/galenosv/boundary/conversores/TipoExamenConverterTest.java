package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.TipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoExamen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoExamenConverterTest {

    @Mock
    TipoExamenDAO dao;

    TipoExamenConverter converter;

    @BeforeEach
    void setUp() throws ReflectiveOperationException {
        converter = new TipoExamenConverter();
        Field field = TipoExamenConverter.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(converter, dao);
    }

    @Test
    void convierteElIdentificadorEnTipoExamen() {
        UUID id = UUID.randomUUID();
        TipoExamen esperado = new TipoExamen();
        when(dao.find(id)).thenReturn(esperado);

        assertSame(esperado, converter.getAsObject(null, null, "  " + id + "  "));
        verify(dao).find(id);
    }

    @Test
    void devuelveNuloCuandoElTextoEstaVacioONoEsUuid() {
        assertNull(converter.getAsObject(null, null, null));
        assertNull(converter.getAsObject(null, null, "   "));

        Logger logger = Logger.getLogger(TipoExamenConverter.class.getName());
        Level nivelAnterior = logger.getLevel();
        try {
            logger.setLevel(Level.OFF);
            assertNull(converter.getAsObject(null, null, "identificador-invalido"));
        } finally {
            logger.setLevel(nivelAnterior);
        }
    }

    @Test
    void convierteElTipoExamenEnSuIdentificador() {
        UUID id = UUID.randomUUID();
        TipoExamen tipo = new TipoExamen();
        tipo.setIdTipoExamen(id);

        assertEquals(id.toString(), converter.getAsString(null, null, tipo));
        assertEquals("", converter.getAsString(null, null, null));
        assertEquals("", converter.getAsString(null, null, new TipoExamen()));
    }
}
