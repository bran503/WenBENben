package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.PersonaRol;

@Stateless
public class PersonaRolDAO extends DefaultDAO<PersonaRol> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public PersonaRolDAO() {
        super(PersonaRol.class);
    }

    @Override
    public List<PersonaRol> findAll() {
        try {
            return getEntityManager()
                    .createQuery("SELECT p FROM PersonaRol p LEFT JOIN FETCH p.idPersona LEFT JOIN FETCH p.idRol LEFT JOIN FETCH p.idClinica", PersonaRol.class)
                    .getResultList();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pueden listar persona rol", ex);
        }
    }

    @Override
    public List<PersonaRol> findRange(int first, int pageSize) {
        if (first < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Parametros invalidos");
        }

        try {
            TypedQuery<PersonaRol> query = getEntityManager()
                    .createQuery("SELECT p FROM PersonaRol p LEFT JOIN FETCH p.idPersona LEFT JOIN FETCH p.idRol LEFT JOIN FETCH p.idClinica", PersonaRol.class);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pueden listar persona rol", ex);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
