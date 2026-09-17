package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoExamen;
import java.util.List;

@Stateless
public class TipoExamenDAO extends DefaultDAO<TipoExamen> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public TipoExamenDAO() {
        super(TipoExamen.class);
    }

    public List<TipoExamen> buscarPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("nombre no puede ser nulo");
            String jpql = "SELECT t FROM TipoExamen t WHERE t.nombre = :nombre";
            TypedQuery<TipoExamen> q = getEntityManager().createQuery(jpql, TipoExamen.class);
            q.setParameter("nombre", nombre.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) { throw ex; }
        catch (Exception ex) { throw new IllegalStateException("Error en buscarPorNombre", ex); }
    }

    public List<TipoExamen> findByNombreLike(String filtro, int first, int max) {
        if (filtro == null || filtro.trim().length() < 3 || first < 0 || max <= 0) {
            throw new IllegalArgumentException("Parametros invalidos para buscar tipo de examen");
        }

        try {
            TypedQuery<TipoExamen> q = getEntityManager()
                    .createNamedQuery("TipoExamen.findByNombreLike", TipoExamen.class);
            q.setParameter("nombre", "%" + filtro.trim().toUpperCase() + "%");
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Error en findByNombreLike", ex);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
