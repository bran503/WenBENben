package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.OrdenExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.OrdenExamen;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class OrdenExamenFrm extends AbstractModel<OrdenExamen> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    OrdenExamenDAO dao;

    public OrdenExamenFrm() {
        this.nombreBean = "OrdenExamen";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<OrdenExamen> getDao() {
        return dao;
    }

    @Override
    protected OrdenExamen nuevoRegistro() { 
        OrdenExamen r = new OrdenExamen();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected OrdenExamen buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (OrdenExamen e : dao.findAll()) {
                if (e.getIdOrdenExamen() != null && e.getIdOrdenExamen().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(OrdenExamen r) {
        if (r != null && r.getIdOrdenExamen() != null) {
            return r.getIdOrdenExamen().toString();
        }
        return null;
    }

    @Override
    protected OrdenExamen getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdOrdenExamen()!=null && x.getIdOrdenExamen().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
