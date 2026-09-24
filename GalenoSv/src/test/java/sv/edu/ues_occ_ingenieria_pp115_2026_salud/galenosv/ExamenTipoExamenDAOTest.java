package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenTipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenTipoExamen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamenTipoExamenDAOTest {

    @Mock
    EntityManager em;
    @Mock
    TypedQuery<ExamenTipoExamen> relationQuery;
    @Mock
    TypedQuery<Long> countQuery;

    ExamenTipoExamenDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ExamenTipoExamenDAO() {
            @Override
            public EntityManager getEntityManager() {
                return em;
            }
        };
    }

    @Test
    void paginaLosTiposAsociadosAUnExamen() {
        UUID idExamen = UUID.randomUUID();
        ExamenTipoExamen relacion = new ExamenTipoExamen();
        when(em.createNamedQuery("ExamenTipoExamen.findByIdExamen", ExamenTipoExamen.class)).thenReturn(relationQuery);
        when(relationQuery.setParameter("idExamen", idExamen)).thenReturn(relationQuery);
        when(relationQuery.setFirstResult(5)).thenReturn(relationQuery);
        when(relationQuery.setMaxResults(10)).thenReturn(relationQuery);
        when(relationQuery.getResultList()).thenReturn(List.of(relacion));

        assertEquals(List.of(relacion), dao.findByIdExamen(idExamen, 5, 10));
        verify(relationQuery).setFirstResult(5);
        verify(relationQuery).setMaxResults(10);
    }

    @Test
    void cuentaLosTiposAsociadosAUnExamen() {
        UUID idExamen = UUID.randomUUID();
        when(em.createNamedQuery("ExamenTipoExamen.countByIdExamen", Long.class)).thenReturn(countQuery);
        when(countQuery.setParameter("idExamen", idExamen)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(3L);

        assertEquals(3, dao.countByIdExamen(idExamen));
    }

    @Test
    void validaLosParametrosDeBusquedaYConteo() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByIdExamen(null, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> dao.findByIdExamen(UUID.randomUUID(), -1, 10));
        assertThrows(IllegalArgumentException.class, () -> dao.findByIdExamen(UUID.randomUUID(), 0, 0));
        assertThrows(IllegalArgumentException.class, () -> dao.countByIdExamen(null));
    }
}
