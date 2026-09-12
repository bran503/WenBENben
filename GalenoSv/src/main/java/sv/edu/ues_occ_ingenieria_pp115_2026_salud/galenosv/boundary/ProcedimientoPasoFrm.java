package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ProcedimientoPasoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ProcedimientoPaso;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ProcedimientoPasoFrm extends AbstractModel<ProcedimientoPaso> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ProcedimientoPasoDAO dao;

    public ProcedimientoPasoFrm() {
        this.nombreBean = "ProcedimientoPaso";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ProcedimientoPaso> getDao() {
        return dao;
    }

    @Override
    protected ProcedimientoPaso nuevoRegistro() { 
        ProcedimientoPaso r = new ProcedimientoPaso();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ProcedimientoPaso buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ProcedimientoPaso e : dao.findAll()) {
                if (e.getIdProcedimientoPaso() != null && e.getIdProcedimientoPaso().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ProcedimientoPaso r) {
        if (r != null && r.getIdProcedimientoPaso() != null) {
            return r.getIdProcedimientoPaso().toString();
        }
        return null;
    }

    @Override
    protected ProcedimientoPaso getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdProcedimientoPaso()!=null && x.getIdProcedimientoPaso().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
