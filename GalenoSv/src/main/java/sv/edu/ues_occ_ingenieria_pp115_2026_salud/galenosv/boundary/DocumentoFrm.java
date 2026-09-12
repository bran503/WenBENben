package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DocumentoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.DefaultDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Documento;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class DocumentoFrm extends AbstractModel<Documento> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    DocumentoDAO dao;

    public DocumentoFrm() {
        this.nombreBean = "Documento";
    }


    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected DefaultDAO<Documento> getDao() {
        return dao;
    }

    @Override
    protected Documento nuevoRegistro() { 
        Documento r = new Documento();
        // id auto en btnGuardar si es null
        return r;
    }

    @Override
    protected Documento buscarRegistroPorId(Object id) {
        if (id != null && id instanceof UUID buscado && !this.modelo.getWrappedData().isEmpty()) {
            for (Documento e : dao.findAll()) {
                if (e.getIdDocumento() != null && e.getIdDocumento().equals(buscado)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    protected String getIdAsText(Documento r) {
        if (r != null && r.getIdDocumento() != null) {
            return r.getIdDocumento().toString();
        }
        return null;
    }

    @Override
    protected Documento getIdByText(String id) {
        if (id != null && this.modelo != null && !this.modelo.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.modelo.getWrappedData().stream()
                        .filter(x -> x.getIdDocumento()!=null && x.getIdDocumento().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
