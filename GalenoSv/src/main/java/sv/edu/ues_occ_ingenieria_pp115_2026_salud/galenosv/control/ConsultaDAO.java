package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Consulta;
import java.util.Date;
import java.util.List;

@Stateless
public class ConsultaDAO extends DefaultDAO<Consulta> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public ConsultaDAO() {
        super(Consulta.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Consulta> buscarPorConsultasActivas() {
        try {
            String jpql = "SELECT c FROM Consulta c WHERE c.fechaFin IS NULL OR c.fechaFin > :ahora";
            TypedQuery<Consulta> query = getEntityManager().createQuery(jpql, Consulta.class);
            query.setParameter("ahora", new Date());
            return query.getResultList();
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Error en buscarPorConsultasActivas", ex);
        }
    }

    public List<Consulta> buscarActivas() {
        try {
            String jpql = "SELECT c FROM Consulta c WHERE c.fechaFin IS NULL OR c.fechaFin > :ahora";
            TypedQuery<Consulta> query = getEntityManager().createQuery(jpql, Consulta.class);
            query.setParameter("ahora", new Date());
            return query.getResultList();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public List<Consulta> buscarPorPersonaRol(Object idPersonaRol) {
        if (idPersonaRol == null) throw new IllegalArgumentException("idPersonaRol no puede ser nulo");
        TypedQuery<Consulta> q = getEntityManager().createQuery(
            "SELECT c FROM Consulta c WHERE c.idPersonaRol.idPersonaRol = :id", Consulta.class);
        q.setParameter("id", idPersonaRol);
        return q.getResultList();
    }

    public List<Consulta> buscarActivasPorPersonaRol(Object idPersonaRol) {
        if (idPersonaRol == null) throw new IllegalArgumentException("idPersonaRol requerido");
        TypedQuery<Consulta> q = getEntityManager().createQuery(
            "SELECT c FROM Consulta c WHERE c.idPersonaRol.idPersonaRol = :id AND (c.fechaFin IS NULL OR c.fechaFin > :ahora)", Consulta.class);
        q.setParameter("id", idPersonaRol);
        q.setParameter("ahora", new Date());
        return q.getResultList();
    }
}
