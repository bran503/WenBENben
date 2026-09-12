package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenTipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenTipoExamen;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ExamenTipoExamenFrm extends AbstractModel<ExamenTipoExamen> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ExamenTipoExamenDAO dao;

    public ExamenTipoExamenFrm() {
        this.nombreBean = "ExamenTipoExamen";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ExamenTipoExamen> getDao() {
        return dao;
    }

    @Override
    protected ExamenTipoExamen nuevoRegistro() { 
        ExamenTipoExamen r = new ExamenTipoExamen();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ExamenTipoExamen buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ExamenTipoExamen e : dao.findAll()) {
                if (e.getIdExamenTipoExamen() != null && e.getIdExamenTipoExamen().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ExamenTipoExamen r) {
        if (r != null && r.getIdExamenTipoExamen() != null) {
            return r.getIdExamenTipoExamen().toString();
        }
        return null;
    }

    @Override
    protected ExamenTipoExamen getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdExamenTipoExamen()!=null && x.getIdExamenTipoExamen().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
