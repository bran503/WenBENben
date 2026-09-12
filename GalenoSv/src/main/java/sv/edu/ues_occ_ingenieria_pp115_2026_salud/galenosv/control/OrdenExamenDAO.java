package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.OrdenExamen;

@Stateless
public class OrdenExamenDAO extends DefaultDAO<OrdenExamen> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public OrdenExamenDAO() {
        super(OrdenExamen.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
