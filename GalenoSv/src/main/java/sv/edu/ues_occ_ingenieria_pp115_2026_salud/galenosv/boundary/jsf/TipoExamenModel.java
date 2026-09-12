package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.AbstractModel;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.TipoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.TipoExamen;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named("tipoExamenModel")
@ViewScoped
public class TipoExamenModel extends AbstractModel<TipoExamen> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    FacesContext facesContext;

    @Inject
    TipoExamenDAO dao;

    public TipoExamenModel() {
        this.nombreBean = "TipoExamen";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<TipoExamen> getDao() {
        return dao;
    }

    @Override
    protected TipoExamen nuevoRegistro() {
        TipoExamen r = new TipoExamen();
        r.setIdTipoExamen(UUID.randomUUID());
        r.setActivo(true);
        return r;
    }

    @Override
    protected TipoExamen buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (TipoExamen e : dao.findAll()) {
                if (e.getIdTipoExamen() != null && e.getIdTipoExamen().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(TipoExamen r) {
        if (r != null && r.getIdTipoExamen() != null) {
            return r.getIdTipoExamen().toString();
        }
        return null;
    }

    @Override
    protected TipoExamen getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdTipoExamen()!=null && x.getIdTipoExamen().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
