package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ProcedimientoPasoSecuenciaDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ProcedimientoPasoSecuencia;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ProcedimientoPasoSecuenciaFrm extends AbstractModel<ProcedimientoPasoSecuencia> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ProcedimientoPasoSecuenciaDAO dao;

    public ProcedimientoPasoSecuenciaFrm() {
        this.nombreBean = "ProcedimientoPasoSecuencia";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ProcedimientoPasoSecuencia> getDao() {
        return dao;
    }

    @Override
    protected ProcedimientoPasoSecuencia nuevoRegistro() { 
        ProcedimientoPasoSecuencia r = new ProcedimientoPasoSecuencia();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ProcedimientoPasoSecuencia buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ProcedimientoPasoSecuencia e : dao.findAll()) {
                if (e.getIdProcedimientoPasoSecuencia() != null && e.getIdProcedimientoPasoSecuencia().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ProcedimientoPasoSecuencia r) {
        if (r != null && r.getIdProcedimientoPasoSecuencia() != null) {
            return r.getIdProcedimientoPasoSecuencia().toString();
        }
        return null;
    }

    @Override
    protected ProcedimientoPasoSecuencia getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdProcedimientoPasoSecuencia()!=null && x.getIdProcedimientoPasoSecuencia().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
