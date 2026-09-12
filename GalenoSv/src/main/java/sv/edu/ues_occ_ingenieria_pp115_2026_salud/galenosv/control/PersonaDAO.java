package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Persona;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PersonaDAO extends DefaultDAO<Persona> {

    @PersistenceContext(unitName = "clinica_ppi")
    private EntityManager em;

    public PersonaDAO() {
        super(Persona.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // Metodos propios JPQL - patron visto en otro proyecto: try + Logger + return List.of() sin throw
    public List<Persona> buscarPorNombre(String nombres) {
        try {
            if (nombres == null || nombres.trim().isEmpty()) throw new IllegalArgumentException("nombres no puede ser nulo o vacio");
            String jpql = "SELECT p FROM Persona p WHERE p.nombres = :nombres";
            TypedQuery<Persona> q = getEntityManager().createQuery(jpql, Persona.class);
            q.setParameter("nombres", nombres.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.WARNING, "Parametro invalido buscarPorNombre", ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, "Error en buscarPorNombre", ex);
            return Collections.emptyList();
        }
    }

    public List<Persona> buscarPorApellido(String apellidos) {
        try {
            if (apellidos == null || apellidos.trim().isEmpty()) throw new IllegalArgumentException("apellidos no puede ser nulo o vacio");
            String jpql = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos";
            TypedQuery<Persona> q = getEntityManager().createQuery(jpql, Persona.class);
            q.setParameter("apellidos", apellidos.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.WARNING, null, ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, "Error en buscarPorApellido", ex);
            return Collections.emptyList();
        }
    }

    public List<Persona> buscarPorDocumento(String valorDocumento) {
        try {
            if (valorDocumento == null || valorDocumento.trim().isEmpty()) throw new IllegalArgumentException("valorDocumento no puede ser nulo");
            String jpql = "SELECT DISTINCT p FROM Persona p JOIN p.documentoList d WHERE d.valor = :valor";
            TypedQuery<Persona> q = getEntityManager().createQuery(jpql, Persona.class);
            q.setParameter("valor", valorDocumento.trim());
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.WARNING, null, ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, "Error en buscarPorDocumento", ex);
            return Collections.emptyList();
        }
    }

    public List<Persona> buscarPorNombreYApellido(String nombres, String apellidos) {
        try {
            if (nombres == null || apellidos == null) throw new IllegalArgumentException("nombres y apellidos requeridos");
            String jpql = "SELECT p FROM Persona p WHERE p.nombres = :nombres AND p.apellidos = :apellidos";
            TypedQuery<Persona> q = getEntityManager().createQuery(jpql, Persona.class);
            q.setParameter("nombres", nombres);
            q.setParameter("apellidos", apellidos);
            return q.getResultList();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.WARNING, null, ex);
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, "Error en buscarPorNombreYApellido", ex);
            return Collections.emptyList();
        }
    }
}
