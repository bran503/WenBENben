package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.MedioContactoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.MedioContacto;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class MedioContactoFrm extends AbstractModel<MedioContacto> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    MedioContactoDAO dao;

    public MedioContactoFrm() {
        this.nombreBean = "MedioContacto";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<MedioContacto> getDao() {
        return dao;
    }

    @Override
    protected MedioContacto nuevoRegistro() { 
        MedioContacto r = new MedioContacto();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected MedioContacto buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (MedioContacto e : dao.findAll()) {
                if (e.getIdMedioContacto() != null && e.getIdMedioContacto().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(MedioContacto r) {
        if (r != null && r.getIdMedioContacto() != null) {
            return r.getIdMedioContacto().toString();
        }
        return null;
    }

    @Override
    protected MedioContacto getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdMedioContacto()!=null && x.getIdMedioContacto().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
