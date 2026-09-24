package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Examen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDAOTest {

    @Mock
    EntityManager em;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    CriteriaQuery<Examen> entityCriteria;
    @Mock
    Root<Examen> entityRoot;
    @Mock
    TypedQuery<Examen> entityQuery;
    @Mock
    CriteriaQuery<Long> countCriteria;
    @Mock
    Expression<Long> countExpression;
    @Mock
    TypedQuery<Long> countQuery;

    DefaultDAO<Examen> dao;

    @BeforeEach
    void setUp() {
        dao = new DefaultDAO<>(Examen.class) {
            @Override
            public EntityManager getEntityManager() {
                return em;
            }
        };
    }

    @Test
    void buscaPorIdUsandoLaClaseDeLaEntidad() {
        UUID id = UUID.randomUUID();
        Examen esperado = new Examen();
        when(em.find(Examen.class, id)).thenReturn(esperado);

        assertSame(esperado, dao.find(id));
        verify(em).find(Examen.class, id);
    }

    @Test
    void rechazaIdentificadoresYRegistrosNulos() {
        assertThrows(IllegalArgumentException.class, () -> dao.find(null));
        assertThrows(IllegalArgumentException.class, () -> dao.crear(null));
        assertThrows(IllegalArgumentException.class, () -> dao.modificar(null));
        assertThrows(IllegalArgumentException.class, () -> dao.eliminar(null));
    }

    @Test
    void listaTodosLosRegistrosConCriteriaApi() {
        Examen examen = new Examen();
        prepararConsultaDeEntidades(List.of(examen));

        assertEquals(List.of(examen), dao.findAll());
    }

    @Test
    void paginaLosRegistrosConElRangoSolicitado() {
        Examen examen = new Examen();
        prepararConsultaDeEntidades(List.of(examen));
        when(entityQuery.setFirstResult(20)).thenReturn(entityQuery);
        when(entityQuery.setMaxResults(10)).thenReturn(entityQuery);

        assertEquals(List.of(examen), dao.findRange(20, 10));
        verify(entityQuery).setFirstResult(20);
        verify(entityQuery).setMaxResults(10);
    }

    @Test
    void rechazaRangosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> dao.findRange(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> dao.findRange(0, 0));
    }

    @Test
    void cuentaLosRegistros() {
        when(em.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Long.class)).thenReturn(countCriteria);
        when(countCriteria.from(Examen.class)).thenReturn(entityRoot);
        when(criteriaBuilder.count(entityRoot)).thenReturn(countExpression);
        when(countCriteria.select(countExpression)).thenReturn(countCriteria);
        when(em.createQuery(countCriteria)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(12L);

        assertEquals(12L, dao.count());
    }

    @Test
    void creaYModificaRegistros() {
        Examen examen = new Examen();
        Examen actualizado = new Examen();
        when(em.merge(examen)).thenReturn(actualizado);

        dao.crear(examen);

        verify(em).persist(examen);
        assertSame(actualizado, dao.modificar(examen));
    }

    @Test
    void eliminaDirectamenteUnaEntidadAdministrada() {
        Examen examen = new Examen();
        when(em.contains(examen)).thenReturn(true);

        dao.eliminar(examen);

        verify(em).remove(examen);
    }

    @Test
    void adjuntaAntesDeEliminarUnaEntidadSeparada() {
        Examen examen = new Examen();
        Examen administrado = new Examen();
        when(em.contains(examen)).thenReturn(false);
        when(em.merge(examen)).thenReturn(administrado);

        dao.eliminar(examen);

        verify(em).remove(administrado);
    }

    private void prepararConsultaDeEntidades(List<Examen> resultado) {
        when(em.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Examen.class)).thenReturn(entityCriteria);
        when(entityCriteria.from(Examen.class)).thenReturn(entityRoot);
        when(entityCriteria.select(entityRoot)).thenReturn(entityCriteria);
        when(em.createQuery(entityCriteria)).thenReturn(entityQuery);
        when(entityQuery.getResultList()).thenReturn(resultado);
    }
}
