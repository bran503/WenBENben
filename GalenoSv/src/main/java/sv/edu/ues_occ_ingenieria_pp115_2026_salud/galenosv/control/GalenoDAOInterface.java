package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control;

import java.util.List;

public interface GalenoDAOInterface<T> {

    void crear(T registro);

    List<T> findRange(int first, int max);

    Long count();

    void eliminar(T registro);
}
