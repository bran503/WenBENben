/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;
import java.util.List;

/**
 *
 * @author duran
 */
@Entity
@Table(name = "consulta_procedimiento_paso", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ConsultaProcedimientoPaso.findAll", query = "SELECT c FROM ConsultaProcedimientoPaso c"),
    @NamedQuery(name = "ConsultaProcedimientoPaso.findByFechaInicio", query = "SELECT c FROM ConsultaProcedimientoPaso c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ConsultaProcedimientoPaso.findByFechaFin", query = "SELECT c FROM ConsultaProcedimientoPaso c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "ConsultaProcedimientoPaso.findByEstado", query = "SELECT c FROM ConsultaProcedimientoPaso c WHERE c.estado = :estado")})
public class ConsultaProcedimientoPaso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Convert(converter = sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores.UUIDConverter.class)
    @Column(name = "id_consulta_procedimiento_paso", columnDefinition = "uuid")
    private UUID idConsultaProcedimientoPaso;
    @Column(name = "fecha_inicio")
    private OffsetDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private OffsetDateTime fechaFin;
    @Size(max = 20)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_consulta_procedimiento", referencedColumnName = "id_consulta_procedimiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConsultaProcedimiento idConsultaProcedimiento;
    @JoinColumn(name = "id_persona_rol", referencedColumnName = "id_persona_rol")
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonaRol idPersonaRol;
    @OneToMany(mappedBy = "idConsultaProcedimientoPaso", fetch = FetchType.LAZY)
    private List<OrdenExamen> ordenExamenList;

    public ConsultaProcedimientoPaso() {
    }

    public ConsultaProcedimientoPaso(UUID idConsultaProcedimientoPaso) {
        this.idConsultaProcedimientoPaso = idConsultaProcedimientoPaso;
    }

    public UUID getIdConsultaProcedimientoPaso() {
        return idConsultaProcedimientoPaso;
    }

    public void setIdConsultaProcedimientoPaso(UUID idConsultaProcedimientoPaso) {
        this.idConsultaProcedimientoPaso = idConsultaProcedimientoPaso;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ConsultaProcedimiento getIdConsultaProcedimiento() {
        return idConsultaProcedimiento;
    }

    public void setIdConsultaProcedimiento(ConsultaProcedimiento idConsultaProcedimiento) {
        this.idConsultaProcedimiento = idConsultaProcedimiento;
    }

    public PersonaRol getIdPersonaRol() {
        return idPersonaRol;
    }

    public void setIdPersonaRol(PersonaRol idPersonaRol) {
        this.idPersonaRol = idPersonaRol;
    }

    public List<OrdenExamen> getOrdenExamenList() {
        return ordenExamenList;
    }

    public void setOrdenExamenList(List<OrdenExamen> ordenExamenList) {
        this.ordenExamenList = ordenExamenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConsultaProcedimientoPaso != null ? idConsultaProcedimientoPaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConsultaProcedimientoPaso)) {
            return false;
        }
        ConsultaProcedimientoPaso other = (ConsultaProcedimientoPaso) object;
        if ((this.idConsultaProcedimientoPaso == null && other.idConsultaProcedimientoPaso != null) || (this.idConsultaProcedimientoPaso != null && !this.idConsultaProcedimientoPaso.equals(other.idConsultaProcedimientoPaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ConsultaProcedimientoPaso[ idConsultaProcedimientoPaso=" + idConsultaProcedimientoPaso + " ]";
    }
    
}
