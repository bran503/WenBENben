package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.PersonaRolDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.PersonaRol;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class PersonaRolFrm extends AbstractModel<PersonaRol> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    PersonaRolDAO dao;

    public PersonaRolFrm() {
        this.nombreBean = "PersonaRol";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<PersonaRol> getDao() {
        return dao;
    }

    @Override
    protected PersonaRol nuevoRegistro() { 
        PersonaRol r = new PersonaRol();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected PersonaRol buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (PersonaRol e : dao.findAll()) {
                if (e.getIdPersonaRol() != null && e.getIdPersonaRol().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(PersonaRol r) {
        if (r != null && r.getIdPersonaRol() != null) {
            return r.getIdPersonaRol().toString();
        }
        return null;
    }

    @Override
    protected PersonaRol getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdPersonaRol()!=null && x.getIdPersonaRol().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
