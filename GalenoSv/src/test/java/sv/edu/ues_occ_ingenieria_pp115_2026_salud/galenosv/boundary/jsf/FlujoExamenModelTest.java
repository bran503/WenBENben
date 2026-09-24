package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.jsf;

import jakarta.faces.context.FacesContext;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.ESTADO_CRUD;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenTipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.TipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Examen;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenTipoExamen;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoExamen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlujoExamenModelTest {

    @Mock
    FacesContext facesContext;
    @Mock
    ExamenDAO examenDAO;
    @Mock
    ExamenTipoExamenDAO relacionDAO;
    @Mock
    TipoExamenDAO tipoExamenDAO;

    ExamenTipoExamenModel detalle;

    @BeforeEach
    void setUp() {
        detalle = new ExamenTipoExamenModel();
        detalle.facesContext = facesContext;
        detalle.dao = relacionDAO;
        detalle.examenDAO = examenDAO;
        detalle.tipoExamenDAO = tipoExamenDAO;
    }

    @Test
    void unNuevoExamenSincronizaSuIdentificadorConElDetalle() {
        ExamenModel examenModel = new ExamenModel();
        examenModel.examenTipoExamenModel = detalle;

        examenModel.btnNuevoHandler(null);

        assertNotNull(examenModel.getRegistro().getIdExamen());
        assertEquals(examenModel.getRegistro().getIdExamen(), detalle.getIdExamen());
        assertEquals(ESTADO_CRUD.CREAR, examenModel.getEstado());

        examenModel.btnCancelarHandler(null);

        assertNull(detalle.getIdExamen());
    }

    @Test
    void guardaLaRelacionEntreExamenYTipoSeleccionado() {
        UUID idExamen = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        Examen examen = new Examen();
        examen.setIdExamen(idExamen);
        TipoExamen tipo = new TipoExamen();
        tipo.setIdTipoExamen(idTipo);
        ExamenTipoExamen relacion = new ExamenTipoExamen();
        detalle.setRegistro(relacion);
        detalle.setIdExamenSeleccionado(idExamen.toString());
        detalle.setTipoExamenSeleccionado(tipo);
        when(examenDAO.find(idExamen)).thenReturn(examen);

        detalle.btnGuardarHandler(null);

        verify(relacionDAO).crear(relacion);
        assertSame(examen, relacion.getIdExamen());
        assertSame(tipo, relacion.getIdTipoExamen());
        assertNull(detalle.getRegistro());
        assertEquals(ESTADO_CRUD.NADA, detalle.getEstado());
    }

    @Test
    void usaLaBusquedaEspecializadaEnElAutocompletado() {
        TipoExamen tipo = new TipoExamen();
        when(tipoExamenDAO.findByNombreLike("radio", 0, 30)).thenReturn(List.of(tipo));

        assertEquals(List.of(tipo), detalle.buscarTipoPorNombre("radio"));
        verify(tipoExamenDAO).findByNombreLike("radio", 0, 30);
    }

    @Test
    void exponeElNombreYElIdDelTipoSeleccionado() {
        UUID id = UUID.randomUUID();
        TipoExamen tipo = new TipoExamen();
        tipo.setIdTipoExamen(id);
        tipo.setNombre("Radiografia panoramica");

        detalle.setTipoExamenSeleccionado(tipo);

        assertEquals(id.toString(), detalle.getIdTipoExamenSeleccionado());
        assertEquals("Radiografia panoramica", detalle.getNombreTipoExamenSeleccionado());
    }
}
