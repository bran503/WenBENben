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
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import java.time.OffsetDateTime;

/**
 *
 * @author duran
 */
@Entity
@Table(name = "examen_resultado", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ExamenResultado.findAll", query = "SELECT e FROM ExamenResultado e"),
    @NamedQuery(name = "ExamenResultado.findByFechaCreacion", query = "SELECT e FROM ExamenResultado e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ExamenResultado.findByResultado", query = "SELECT e FROM ExamenResultado e WHERE e.resultado = :resultado"),
    @NamedQuery(name = "ExamenResultado.findByInterpretacion", query = "SELECT e FROM ExamenResultado e WHERE e.interpretacion = :interpretacion"),
    @NamedQuery(name = "ExamenResultado.findByRutaAtestado", query = "SELECT e FROM ExamenResultado e WHERE e.rutaAtestado = :rutaAtestado")})
public class ExamenResultado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Convert(converter = sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores.UUIDConverter.class)
    @Column(name = "id_examen_resultado", columnDefinition = "uuid")
    private UUID idExamenResultado;
    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;
    @Size(max = 2147483647)
    @Column(name = "resultado")
    private String resultado;
    @Size(max = 2147483647)
    @Column(name = "interpretacion")
    private String interpretacion;
    @Size(max = 2147483647)
    @Column(name = "ruta_atestado")
    private String rutaAtestado;
    @JoinColumn(name = "id_orden_examen", referencedColumnName = "id_orden_examen")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrdenExamen idOrdenExamen;

    public ExamenResultado() {
    }

    public ExamenResultado(UUID idExamenResultado) {
        this.idExamenResultado = idExamenResultado;
    }

    public UUID getIdExamenResultado() {
        return idExamenResultado;
    }

    public void setIdExamenResultado(UUID idExamenResultado) {
        this.idExamenResultado = idExamenResultado;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getInterpretacion() {
        return interpretacion;
    }

    public void setInterpretacion(String interpretacion) {
        this.interpretacion = interpretacion;
    }

    public String getRutaAtestado() {
        return rutaAtestado;
    }

    public void setRutaAtestado(String rutaAtestado) {
        this.rutaAtestado = rutaAtestado;
    }

    public OrdenExamen getIdOrdenExamen() {
        return idOrdenExamen;
    }

    public void setIdOrdenExamen(OrdenExamen idOrdenExamen) {
        this.idOrdenExamen = idOrdenExamen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExamenResultado != null ? idExamenResultado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExamenResultado)) {
            return false;
        }
        ExamenResultado other = (ExamenResultado) object;
        if ((this.idExamenResultado == null && other.idExamenResultado != null) || (this.idExamenResultado != null && !this.idExamenResultado.equals(other.idExamenResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenResultado[ idExamenResultado=" + idExamenResultado + " ]";
    }
    
}
