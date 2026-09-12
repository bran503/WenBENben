package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ConsultaDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Consulta;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ConsultaFrm extends AbstractModel<Consulta> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ConsultaDAO dao;

    public ConsultaFrm() {
        this.nombreBean = "Consulta";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Consulta> getDao() {
        return dao;
    }

    @Override
    protected Consulta nuevoRegistro() { 
        Consulta r = new Consulta();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Consulta buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Consulta e : dao.findAll()) {
                if (e.getIdConsulta() != null && e.getIdConsulta().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Consulta r) {
        if (r != null && r.getIdConsulta() != null) {
            return r.getIdConsulta().toString();
        }
        return null;
    }

    @Override
    protected Consulta getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdConsulta()!=null && x.getIdConsulta().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
