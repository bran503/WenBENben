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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

/**
 *
 * @author duran
 */
@Entity
@Table(name = "examen_tipo_examen", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ExamenTipoExamen.findAll", query = "SELECT e FROM ExamenTipoExamen e"),
    @NamedQuery(name = "ExamenTipoExamen.findByIdExamen", query = "SELECT e FROM ExamenTipoExamen e WHERE e.idExamen.idExamen = :idExamen ORDER BY e.idTipoExamen.nombre"),
    @NamedQuery(name = "ExamenTipoExamen.countByIdExamen", query = "SELECT COUNT(e.idExamenTipoExamen) FROM ExamenTipoExamen e WHERE e.idExamen.idExamen = :idExamen"),
    @NamedQuery(name = "ExamenTipoExamen.findByFechaCreacion", query = "SELECT e FROM ExamenTipoExamen e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ExamenTipoExamen.findByObservaciones", query = "SELECT e FROM ExamenTipoExamen e WHERE e.observaciones = :observaciones")})
public class ExamenTipoExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Convert(converter = Clases.UUIDConverter.class)
    @Column(name = "id_examen_tipo_examen", columnDefinition = "uuid")
    private UUID idExamenTipoExamen;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_examen", referencedColumnName = "id_examen")
    @ManyToOne(fetch = FetchType.LAZY)
    private Examen idExamen;
    @JoinColumn(name = "id_tipo_examen", referencedColumnName = "id_tipo_examen")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoExamen idTipoExamen;

    public ExamenTipoExamen() {
    }

    public ExamenTipoExamen(UUID idExamenTipoExamen) {
        this.idExamenTipoExamen = idExamenTipoExamen;
    }

    public UUID getIdExamenTipoExamen() {
        return idExamenTipoExamen;
    }

    public void setIdExamenTipoExamen(UUID idExamenTipoExamen) {
        this.idExamenTipoExamen = idExamenTipoExamen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Examen getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Examen idExamen) {
        this.idExamen = idExamen;
    }

    public TipoExamen getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(TipoExamen idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExamenTipoExamen != null ? idExamenTipoExamen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamenTipoExamen)) {
            return false;
        }
        ExamenTipoExamen other = (ExamenTipoExamen) object;
        if ((this.idExamenTipoExamen == null && other.idExamenTipoExamen != null) || (this.idExamenTipoExamen != null && !this.idExamenTipoExamen.equals(other.idExamenTipoExamen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenTipoExamen[ idExamenTipoExamen=" + idExamenTipoExamen + " ]";
    }
    
}
