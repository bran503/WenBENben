package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ConsultaProcedimientoPasoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ConsultaProcedimientoPaso;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ConsultaProcedimientoPasoFrm extends AbstractModel<ConsultaProcedimientoPaso> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ConsultaProcedimientoPasoDAO dao;

    public ConsultaProcedimientoPasoFrm() {
        this.nombreBean = "ConsultaProcedimientoPaso";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ConsultaProcedimientoPaso> getDao() {
        return dao;
    }

    @Override
    protected ConsultaProcedimientoPaso nuevoRegistro() { 
        ConsultaProcedimientoPaso r = new ConsultaProcedimientoPaso();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ConsultaProcedimientoPaso buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ConsultaProcedimientoPaso e : dao.findAll()) {
                if (e.getIdConsultaProcedimientoPaso() != null && e.getIdConsultaProcedimientoPaso().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ConsultaProcedimientoPaso r) {
        if (r != null && r.getIdConsultaProcedimientoPaso() != null) {
            return r.getIdConsultaProcedimientoPaso().toString();
        }
        return null;
    }

    @Override
    protected ConsultaProcedimientoPaso getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdConsultaProcedimientoPaso()!=null && x.getIdConsultaProcedimientoPaso().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
