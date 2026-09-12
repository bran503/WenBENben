package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import java.util.List;

public interface InventarioDAOInterface<T> {
    void crear(T registro) throws IllegalArgumentException, IllegalAccessException;
    List<T> findRange(int first, int max) throws IllegalArgumentException;
    Long count() throws IllegalArgumentException;
    void eliminar(T registro) throws IllegalArgumentException, IllegalAccessException;
}
