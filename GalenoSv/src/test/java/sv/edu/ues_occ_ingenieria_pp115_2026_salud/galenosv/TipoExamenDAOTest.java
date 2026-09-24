package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.TipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoExamen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoExamenDAOTest {

    @Mock
    EntityManager em;
    @Mock
    TypedQuery<TipoExamen> query;

    TipoExamenDAO dao;

    @BeforeEach
    void setUp() {
        dao = new TipoExamenDAO() {
            @Override
            public EntityManager getEntityManager() {
                return em;
            }
        };
    }

    @Test
    void buscaCoincidenciasParaElAutocompletado() {
        TipoExamen radiografia = new TipoExamen();
        radiografia.setNombre("Radiografia panoramica");
        when(em.createNamedQuery("TipoExamen.findByNombreLike", TipoExamen.class)).thenReturn(query);
        when(query.setParameter("nombre", "%RAD%")).thenReturn(query);
        when(query.setFirstResult(0)).thenReturn(query);
        when(query.setMaxResults(30)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(radiografia));

        List<TipoExamen> resultado = dao.findByNombreLike(" rad ", 0, 30);

        assertEquals(List.of(radiografia), resultado);
        verify(query).setParameter("nombre", "%RAD%");
        verify(query).setMaxResults(30);
    }

    @Test
    void rechazaFiltrosMuyCortosYRangosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByNombreLike(null, 0, 30));
        assertThrows(IllegalArgumentException.class, () -> dao.findByNombreLike("rx", 0, 30));
        assertThrows(IllegalArgumentException.class, () -> dao.findByNombreLike("radio", -1, 30));
        assertThrows(IllegalArgumentException.class, () -> dao.findByNombreLike("radio", 0, 0));
    }
}
