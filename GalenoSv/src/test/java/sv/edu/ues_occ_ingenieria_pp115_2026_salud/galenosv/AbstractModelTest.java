package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv;

import jakarta.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.AbstractModel;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.ESTADO_CRUD;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractModelTest {

    @Mock
    DefaultDAO<EntidadConNombre> dao;
    @Mock
    FacesContext facesContext;

    ModeloPrueba modelo;

    @BeforeEach
    void setUp() {
        modelo = new ModeloPrueba(dao, facesContext);
    }

    @Test
    void validaNombreCuandoLaEntidadTieneEseCampo() {
        assertTrue(modelo.nombreVacio(new EntidadConNombre(null)));
        assertTrue(modelo.nombreVacio(new EntidadConNombre("   ")));
        assertFalse(modelo.nombreVacio(new EntidadConNombre("Examen")));
    }

    @Test
    void noExigeNombreAEntidadesQueNoTienenEseCampo() {
        ModeloSinNombre modeloSinNombre = new ModeloSinNombre();

        assertFalse(modeloSinNombre.nombreVacio(new EntidadSinNombre()));
    }

    @Test
    void cargaYCuentaRegistrosDeFormaDiferida() {
        EntidadConNombre registro = new EntidadConNombre("Radiografia");
        when(dao.findRange(10, 5)).thenReturn(List.of(registro));
        when(dao.count()).thenReturn(21L);

        modelo.inicializarRegistros();

        assertEquals(21, modelo.getModelo().count(Map.of()));
        assertEquals(List.of(registro), modelo.getModelo().load(10, 5, Map.of(), Map.of()));
    }

    @Test
    void iniciaYCancelaLaCreacionDeUnRegistro() {
        modelo.btnNuevoHandler(null);

        assertNotNull(modelo.getRegistro());
        assertEquals(ESTADO_CRUD.CREAR, modelo.getEstado());

        modelo.btnCancelarHandler(null);

        assertNull(modelo.getRegistro());
        assertEquals(ESTADO_CRUD.NADA, modelo.getEstado());
    }

    @Test
    void guardaUnRegistroValidoYRestableceElFormulario() {
        EntidadConNombre registro = new EntidadConNombre("Examen");
        modelo.setRegistro(registro);

        modelo.btnGuardarHandler(null);

        verify(dao).crear(registro);
        assertNull(modelo.getRegistro());
        assertEquals(ESTADO_CRUD.NADA, modelo.getEstado());
    }

    @Test
    void noGuardaUnRegistroSinNombre() {
        EntidadConNombre registro = new EntidadConNombre("  ");
        modelo.setRegistro(registro);

        modelo.btnGuardarHandler(null);

        assertSame(registro, modelo.getRegistro());
        verify(facesContext).addMessage(isNull(), any());
    }

    @Test
    void modificaYEliminaElRegistroSeleccionado() {
        EntidadConNombre registro = new EntidadConNombre("Examen actualizado");
        modelo.setRegistro(registro);
        modelo.setEstado(ESTADO_CRUD.MODIFICAR);

        modelo.btnModificarHandler(null);

        verify(dao).modificar(registro);
        assertNull(modelo.getRegistro());

        modelo.setRegistro(registro);
        modelo.setEstado(ESTADO_CRUD.MODIFICAR);
        modelo.btnEliminarHandler(null);

        verify(dao).eliminar(registro);
        assertNull(modelo.getRegistro());
        assertEquals(ESTADO_CRUD.NADA, modelo.getEstado());
    }

    public static class EntidadConNombre {

        private final String nombre;

        EntidadConNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    public static class EntidadSinNombre {
    }

    private static class ModeloPrueba extends AbstractModel<EntidadConNombre> {

        private final DefaultDAO<EntidadConNombre> dao;
        private final FacesContext facesContext;

        ModeloPrueba(DefaultDAO<EntidadConNombre> dao, FacesContext facesContext) {
            this.dao = dao;
            this.facesContext = facesContext;
        }

        boolean nombreVacio(EntidadConNombre registro) {
            return esNombreVacio(registro);
        }

        @Override
        protected FacesContext getFacesContext() {
            return facesContext;
        }

        @Override
        protected DefaultDAO<EntidadConNombre> getDao() {
            return dao;
        }

        @Override
        protected EntidadConNombre nuevoRegistro() {
            return new EntidadConNombre(null);
        }

        @Override
        protected EntidadConNombre buscarRegistroPorId(Object id) {
            return null;
        }

        @Override
        protected String getIdAsText(EntidadConNombre registro) {
            return registro == null ? null : registro.getNombre();
        }

        @Override
        protected EntidadConNombre getIdByText(String id) {
            return null;
        }
    }

    private static class ModeloSinNombre extends AbstractModel<EntidadSinNombre> {

        boolean nombreVacio(EntidadSinNombre registro) {
            return esNombreVacio(registro);
        }

        @Override
        protected FacesContext getFacesContext() {
            return null;
        }

        @Override
        protected DefaultDAO<EntidadSinNombre> getDao() {
            return null;
        }

        @Override
        protected EntidadSinNombre nuevoRegistro() {
            return new EntidadSinNombre();
        }

        @Override
        protected EntidadSinNombre buscarRegistroPorId(Object id) {
            return null;
        }

        @Override
        protected String getIdAsText(EntidadSinNombre registro) {
            return null;
        }

        @Override
        protected EntidadSinNombre getIdByText(String id) {
            return null;
        }
    }
}
