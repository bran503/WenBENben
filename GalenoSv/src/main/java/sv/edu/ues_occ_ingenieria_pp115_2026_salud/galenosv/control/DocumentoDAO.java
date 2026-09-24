package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Documento;

@Stateless
public class DocumentoDAO extends DefaultDAO<Documento> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public DocumentoDAO() {
        super(Documento.class);
    }

    public List<Documento> buscarPorValor(String valor) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new IllegalArgumentException("valor no puede ser nulo");
            }
            String jpql = "SELECT d FROM Documento d WHERE d.valor = :valor";
            TypedQuery<Documento> q = getEntityManager().createQuery(jpql, Documento.class);
            q.setParameter("valor", valor.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Error en buscarPorValor", ex);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
