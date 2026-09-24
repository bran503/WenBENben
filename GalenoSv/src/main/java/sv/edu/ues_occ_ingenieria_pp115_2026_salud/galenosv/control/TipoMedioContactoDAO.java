package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
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

    // Metodo de busqueda puntual con consulta nativa, como se practico en clase.
    public List<TipoMedioContacto> buscarPorNombreNativo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El parametro nombre no puede ser nulo o vacio");
        }
        try {
            Query q = getEntityManager()
                    .createNativeQuery("SELECT * FROM tipo_medio_contacto WHERE nombre = :nombre", TipoMedioContacto.class);
            q.setParameter("nombre", nombre.trim());
            return convertirResultado(q.getResultList());
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Error en buscarPorNombreNativo", ex);
        }
    }

    public TipoMedioContacto buscarPrimeroPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Parametro nombre invalido");
        }
        try {
            Query q = getEntityManager()
                    .createNativeQuery("SELECT * FROM tipo_medio_contacto WHERE nombre = :nombre", TipoMedioContacto.class);
            q.setParameter("nombre", nombre.trim());
            q.setMaxResults(1);
            List<TipoMedioContacto> list = convertirResultado(q.getResultList());
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } catch (Exception ex) {
            throw new IllegalStateException("Error en buscarPrimeroPorNombre", ex);
        }
    }

    private List<TipoMedioContacto> convertirResultado(List<?> datos) {
        List<TipoMedioContacto> resultado = new ArrayList<>();
        for (Object dato : datos) {
            resultado.add(TipoMedioContacto.class.cast(dato));
        }
        return resultado;
    }
}
