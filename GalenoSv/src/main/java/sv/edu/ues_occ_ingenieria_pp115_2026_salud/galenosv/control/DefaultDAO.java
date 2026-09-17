package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Centraliza las operaciones comunes de acceso a datos de GalenoSv.
 */
public abstract class DefaultDAO<T> implements GalenoDAOInterface<T>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<T> entityClass;

    public DefaultDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public T find(Object id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }
            return em.find(entityClass, id);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al buscar por ID", ex);
        }
    }

    public List<T> findAll() {
        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);

            return em.createQuery(cq).getResultList();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al findAll", ex);
        }
    }

    public List<T> findRange(int first, int pageSize) {
        if (first < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Parametros invalidos");
        }

        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);

            TypedQuery<T> q = em.createQuery(cq);
            q.setFirstResult(first);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al findRange", e);
        }
    }

    public Long count() {
        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(entityClass);
            cq.select(cb.count(root));

            return em.createQuery(cq).getSingleResult();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al count", ex);
        }
    }

    public void crear(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }

        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }
            em.persist(registro);
        } catch (Exception ex) {
            throw new RuntimeException("Error al crear", ex);
        }
    }

    public T modificar(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }

        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }
            return em.merge(registro);
        } catch (Exception ex) {
            throw new RuntimeException("Error al modificar", ex);
        }
    }

    public void eliminar(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }

            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception ex) {
            throw new RuntimeException("Error al eliminar", ex);
        }
    }

}
