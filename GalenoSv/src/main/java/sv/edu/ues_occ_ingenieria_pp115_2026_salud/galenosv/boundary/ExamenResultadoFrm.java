package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenResultadoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenResultado;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ExamenResultadoFrm extends AbstractModel<ExamenResultado> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ExamenResultadoDAO dao;

    public ExamenResultadoFrm() {
        this.nombreBean = "ExamenResultado";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<ExamenResultado> getDao() {
        return dao;
    }

    @Override
    protected ExamenResultado nuevoRegistro() { 
        ExamenResultado r = new ExamenResultado();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected ExamenResultado buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (ExamenResultado e : dao.findAll()) {
                if (e.getIdExamenResultado() != null && e.getIdExamenResultado().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(ExamenResultado r) {
        if (r != null && r.getIdExamenResultado() != null) {
            return r.getIdExamenResultado().toString();
        }
        return null;
    }

    @Override
    protected ExamenResultado getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdExamenResultado()!=null && x.getIdExamenResultado().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
