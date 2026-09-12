package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.UUID;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.AbstractModel;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Examen;

@Named("examenModel")
@ViewScoped
public class ExamenModel extends AbstractModel<Examen> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    FacesContext facesContext;

    @Inject
    ExamenDAO dao;

    public ExamenModel() {
        this.nombreBean = "Examen";
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Examen> getDao() {
        return dao;
    }

    @Override
    protected Examen nuevoRegistro() {
        Examen r = new Examen();
        r.setIdExamen(UUID.randomUUID());
        r.setActivo(true);
        return r;
    }

    @Override
    protected Examen buscarRegistroPorId(Object id) {
        if (id instanceof UUID buscado && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            for (Examen e : dao.findAll()) {
                if (e.getIdExamen() != null && e.getIdExamen().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Examen r) {
        if (r != null && r.getIdExamen() != null) {
            return r.getIdExamen().toString();
        }
        return null;
    }

    @Override
    protected Examen getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdExamen() != null && x.getIdExamen().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
