package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoDocumento;

@Stateless
public class TipoDocumentoDAO extends DefaultDAO<TipoDocumento> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public TipoDocumentoDAO() { super(TipoDocumento.class); }

public List<TipoDocumento> buscarPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("nombre no puede ser nulo");
            String jpql = "SELECT t FROM TipoDocumento t WHERE t.nombre = :nombre";
            TypedQuery<TipoDocumento> q = getEntityManager().createQuery(jpql, TipoDocumento.class);
            q.setParameter("nombre", nombre.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) { throw ex; }
        catch (Exception ex) { throw new IllegalStateException("Error en buscarPorNombre", ex); }
    }

    @Override public EntityManager getEntityManager() { return em; }
}
