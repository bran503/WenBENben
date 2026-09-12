package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ProcedimientoPasoExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ProcedimientoPasoExamen;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ProcedimientoPasoExamenFrm extends AbstractModel<ProcedimientoPasoExamen> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ProcedimientoPasoExamenDAO dao;

    public ProcedimientoPasoExamenFrm() {
        this.nombreBean = "ProcedimientoPasoExamen";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ProcedimientoPasoExamen> getDao() {
        return dao;
    }

    @Override
    protected ProcedimientoPasoExamen nuevoRegistro() { 
        ProcedimientoPasoExamen r = new ProcedimientoPasoExamen();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ProcedimientoPasoExamen buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ProcedimientoPasoExamen e : dao.findAll()) {
                if (e.getIdProcedimientoPasoExamen() != null && e.getIdProcedimientoPasoExamen().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ProcedimientoPasoExamen r) {
        if (r != null && r.getIdProcedimientoPasoExamen() != null) {
            return r.getIdProcedimientoPasoExamen().toString();
        }
        return null;
    }

    @Override
    protected ProcedimientoPasoExamen getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdProcedimientoPasoExamen()!=null && x.getIdProcedimientoPasoExamen().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
