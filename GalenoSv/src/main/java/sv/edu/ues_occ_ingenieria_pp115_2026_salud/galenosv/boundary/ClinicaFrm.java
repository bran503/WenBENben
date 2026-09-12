package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ClinicaDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Clinica;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ClinicaFrm extends AbstractModel<Clinica> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    ClinicaDAO dao;

    public ClinicaFrm() {
        this.nombreBean = "Clinica";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Clinica> getDao() {
        return dao;
    }

    @Override
    protected Clinica nuevoRegistro() { 
        Clinica r = new Clinica();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Clinica buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Clinica e : dao.findAll()) {
                if (e.getIdClinica() != null && e.getIdClinica().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Clinica r) {
        if (r != null && r.getIdClinica() != null) {
            return r.getIdClinica().toString();
        }
        return null;
    }

    @Override
    protected Clinica getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdClinica()!=null && x.getIdClinica().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
