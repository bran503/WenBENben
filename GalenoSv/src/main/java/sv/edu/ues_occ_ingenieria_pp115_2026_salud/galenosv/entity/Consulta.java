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
@Table(name = "consulta", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c"),
    @NamedQuery(name = "Consulta.findByFechaInicio", query = "SELECT c FROM Consulta c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Consulta.findByFechaFin", query = "SELECT c FROM Consulta c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "Consulta.findByReferenciaExterna", query = "SELECT c FROM Consulta c WHERE c.referenciaExterna = :referenciaExterna"),
    @NamedQuery(name = "Consulta.findByObservaciones", query = "SELECT c FROM Consulta c WHERE c.observaciones = :observaciones")})
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Convert(converter = sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores.UUIDConverter.class)
    @Column(name = "id_consulta", columnDefinition = "uuid")
    private UUID idConsulta;
    @Column(name = "fecha_inicio")
    private OffsetDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private OffsetDateTime fechaFin;
    @Size(max = 2147483647)
    @Column(name = "referencia_externa")
    private String referenciaExterna;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_persona_rol", referencedColumnName = "id_persona_rol")
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonaRol idPersonaRol;
    @OneToMany(mappedBy = "idConsulta", fetch = FetchType.LAZY)
    private List<ConsultaProcedimiento> consultaProcedimientoList;

    public Consulta() {
    }

    public Consulta(UUID idConsulta) {
        this.idConsulta = idConsulta;
    }

    public UUID getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(UUID idConsulta) {
        this.idConsulta = idConsulta;
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

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public PersonaRol getIdPersonaRol() {
        return idPersonaRol;
    }

    public void setIdPersonaRol(PersonaRol idPersonaRol) {
        this.idPersonaRol = idPersonaRol;
    }

    public List<ConsultaProcedimiento> getConsultaProcedimientoList() {
        return consultaProcedimientoList;
    }

    public void setConsultaProcedimientoList(List<ConsultaProcedimiento> consultaProcedimientoList) {
        this.consultaProcedimientoList = consultaProcedimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConsulta != null ? idConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.idConsulta == null && other.idConsulta != null) || (this.idConsulta != null && !this.idConsulta.equals(other.idConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Consulta[ idConsulta=" + idConsulta + " ]";
    }
    
}
