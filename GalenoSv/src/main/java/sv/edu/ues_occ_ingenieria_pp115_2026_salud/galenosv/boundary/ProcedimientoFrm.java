package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ProcedimientoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Procedimiento;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ProcedimientoFrm extends AbstractModel<Procedimiento> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ProcedimientoDAO dao;

    public ProcedimientoFrm() {
        this.nombreBean = "Procedimiento";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Procedimiento> getDao() {
        return dao;
    }

    @Override
    protected Procedimiento nuevoRegistro() { 
        Procedimiento r = new Procedimiento();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Procedimiento buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Procedimiento e : dao.findAll()) {
                if (e.getIdProcedimiento() != null && e.getIdProcedimiento().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Procedimiento r) {
        if (r != null && r.getIdProcedimiento() != null) {
            return r.getIdProcedimiento().toString();
        }
        return null;
    }

    @Override
    protected Procedimiento getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdProcedimiento()!=null && x.getIdProcedimiento().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
