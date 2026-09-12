package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.RolDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Rol;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class RolFrm extends AbstractModel<Rol> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    RolDAO dao;

    public RolFrm() {
        this.nombreBean = "Rol";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Rol> getDao() {
        return dao;
    }

    @Override
    protected Rol nuevoRegistro() { 
        Rol r = new Rol();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Rol buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Rol e : dao.findAll()) {
                if (e.getIdRol() != null && e.getIdRol().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Rol r) {
        if (r != null && r.getIdRol() != null) {
            return r.getIdRol().toString();
        }
        return null;
    }

    @Override
    protected Rol getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdRol()!=null && x.getIdRol().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
