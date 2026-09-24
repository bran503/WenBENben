package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.jsf;

import jakarta.faces.context.FacesContext;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ConsultaDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ConsultaProcedimientoPasoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.ExamenResultadoDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.OrdenExamenDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.control.PersonaRolDAO;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.Consulta;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ConsultaProcedimientoPaso;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.ExamenResultado;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.OrdenExamen;
import sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.entity.PersonaRol;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlujoClinicoModelTest {

    @Mock
    FacesContext facesContext;
    @Mock
    ConsultaDAO consultaDAO;
    @Mock
    PersonaRolDAO personaRolDAO;
    @Mock
    OrdenExamenDAO ordenExamenDAO;
    @Mock
    ConsultaProcedimientoPasoDAO pasoDAO;
    @Mock
    ExamenResultadoDAO resultadoDAO;

    @Test
    void creaUnaConsultaParaElPacienteSeleccionado() {
        UUID idPersonaRol = UUID.randomUUID();
        PersonaRol paciente = new PersonaRol();
        Consulta consulta = new Consulta();
        ConsultaModel modelo = new ConsultaModel();
        modelo.facesContext = facesContext;
        modelo.dao = consultaDAO;
        modelo.personaRolDAO = personaRolDAO;
        modelo.setRegistro(consulta);
        modelo.setIdPersonaRolSeleccionado(idPersonaRol.toString());
        when(personaRolDAO.find(idPersonaRol)).thenReturn(paciente);

        modelo.btnGuardarHandler(null);

        assertSame(paciente, consulta.getIdPersonaRol());
        verify(consultaDAO).crear(consulta);
        assertNull(modelo.getRegistro());
    }

    @Test
    void creaUnaOrdenParaElPasoDeConsultaSeleccionado() {
        UUID idPaso = UUID.randomUUID();
        ConsultaProcedimientoPaso paso = new ConsultaProcedimientoPaso();
        OrdenExamen orden = new OrdenExamen();
        OrdenExamenModel modelo = new OrdenExamenModel();
        modelo.facesContext = facesContext;
        modelo.dao = ordenExamenDAO;
        modelo.consultaProcedimientoPasoDAO = pasoDAO;
        modelo.setRegistro(orden);
        modelo.setIdConsultaProcedimientoPasoSeleccionado(idPaso.toString());
        when(pasoDAO.find(idPaso)).thenReturn(paso);

        modelo.btnGuardarHandler(null);

        assertSame(paso, orden.getIdConsultaProcedimientoPaso());
        verify(ordenExamenDAO).crear(orden);
        assertNull(modelo.getRegistro());
    }

    @Test
    void registraElResultadoParaLaOrdenSeleccionada() {
        UUID idOrden = UUID.randomUUID();
        OrdenExamen orden = new OrdenExamen();
        ExamenResultado resultado = new ExamenResultado();
        ExamenResultadoModel modelo = new ExamenResultadoModel();
        modelo.facesContext = facesContext;
        modelo.dao = resultadoDAO;
        modelo.ordenExamenDAO = ordenExamenDAO;
        modelo.setRegistro(resultado);
        modelo.setIdOrdenExamenSeleccionada(idOrden.toString());
        when(ordenExamenDAO.find(idOrden)).thenReturn(orden);

        modelo.btnGuardarHandler(null);

        assertSame(orden, resultado.getIdOrdenExamen());
        verify(resultadoDAO).crear(resultado);
        assertNull(modelo.getRegistro());
    }
}
