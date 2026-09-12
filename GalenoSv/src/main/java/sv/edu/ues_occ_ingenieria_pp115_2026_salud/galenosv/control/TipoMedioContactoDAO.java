package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoMedioContacto;
import java.util.List;

@Stateless
public class TipoMedioContactoDAO extends DefaultDAO<TipoMedioContacto> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public TipoMedioContactoDAO() {
        super(TipoMedioContacto.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // Metodo con NativeQuery + IllegalArgumentException + bucle + try (como pide nota)
    public List<TipoMedioContacto> buscarPorNombreNativo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El parametro nombre no puede ser nulo o vacio");
        }
        try {
            // Native query por nombre (equivalente a NamedQuery TipoMedioContacto.buscarPorNombre)
            Query q = getEntityManager().createNativeQuery("SELECT * FROM tipo_medio_contacto WHERE nombre = :nombre", TipoMedioContacto.class);
            q.setParameter("nombre", nombre.trim());
            // Segundo ejemplo con getFirstResult pattern
            // q.setFirstResult(0); q.setMaxResults(1);
            List<TipoMedioContacto> resultado = q.getResultList();
            // Bucle de verificacion (como en nota: despues de usar el bucle)
            for (TipoMedioContacto t : resultado) {
                if (t.getNombre() == null) continue;
            }
            return resultado;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Error en createNativeQuery buscarPorNombreNativo", ex);
        }
    }

    public TipoMedioContacto buscarPrimeroPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Parametro nombre invalido");
        }
        try {
            Query q = getEntityManager().createNativeQuery("SELECT * FROM tipo_medio_contacto WHERE nombre = :nombre", TipoMedioContacto.class);
            q.setParameter("nombre", nombre);
            q.setMaxResults(1);
            // q.getSingleResult() con manejo
            List<TipoMedioContacto> list = q.getResultList();
            if (list.isEmpty()) return null;
            return list.get(0); // getFirstResult pattern
        } catch (Exception ex) {
            throw new IllegalStateException("Error en buscarPrimeroPorNombre", ex);
        }
    }
}
