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
@Table(name = "persona", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByNombres", query = "SELECT p FROM Persona p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Persona.findByApellidos", query = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Persona.findByFechaCreacion", query = "SELECT p FROM Persona p WHERE p.fechaCreacion = :fechaCreacion")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Convert(converter = sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores.UUIDConverter.class)
    @Column(name = "id_persona", columnDefinition = "uuid")
    private UUID idPersona;
    @Size(max = 255)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 255)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "fecha_nacimiento")
    private OffsetDateTime fechaNacimiento;
    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<MedioContacto> medioContactoList;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<Documento> documentoList;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<PersonaRol> personaRolList;

    public Persona() {
    }

    public Persona(UUID idPersona) {
        this.idPersona = idPersona;
    }

    public UUID getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(UUID idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public OffsetDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(OffsetDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<MedioContacto> getMedioContactoList() {
        return medioContactoList;
    }

    public void setMedioContactoList(List<MedioContacto> medioContactoList) {
        this.medioContactoList = medioContactoList;
    }

    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    public List<PersonaRol> getPersonaRolList() {
        return personaRolList;
    }

    public void setPersonaRolList(List<PersonaRol> personaRolList) {
        this.personaRolList = personaRolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Persona[ idPersona=" + idPersona + " ]";
    }
    
}
