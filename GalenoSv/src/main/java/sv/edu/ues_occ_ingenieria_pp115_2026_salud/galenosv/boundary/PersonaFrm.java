package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.PersonaDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Persona;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class PersonaFrm extends AbstractModel<Persona> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    PersonaDAO dao;

    public PersonaFrm() {
        this.nombreBean = "Persona";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Persona> getDao() {
        return dao;
    }

    @Override
    protected Persona nuevoRegistro() { 
        Persona r = new Persona();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Persona buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Persona e : dao.findAll()) {
                if (e.getIdPersona() != null && e.getIdPersona().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Persona r) {
        if (r != null && r.getIdPersona() != null) {
            return r.getIdPersona().toString();
        }
        return null;
    }

    @Override
    protected Persona getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdPersona()!=null && x.getIdPersona().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
