package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ConsultaProcedimientoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ConsultaProcedimiento;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ConsultaProcedimientoFrm extends AbstractModel<ConsultaProcedimiento> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ConsultaProcedimientoDAO dao;

    public ConsultaProcedimientoFrm() {
        this.nombreBean = "ConsultaProcedimiento";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ConsultaProcedimiento> getDao() {
        return dao;
    }

    @Override
    protected ConsultaProcedimiento nuevoRegistro() { 
        ConsultaProcedimiento r = new ConsultaProcedimiento();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ConsultaProcedimiento buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ConsultaProcedimiento e : dao.findAll()) {
                if (e.getIdConsultaProcedimiento() != null && e.getIdConsultaProcedimiento().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ConsultaProcedimiento r) {
        if (r != null && r.getIdConsultaProcedimiento() != null) {
            return r.getIdConsultaProcedimiento().toString();
        }
        return null;
    }

    @Override
    protected ConsultaProcedimiento getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdConsultaProcedimiento()!=null && x.getIdConsultaProcedimiento().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
