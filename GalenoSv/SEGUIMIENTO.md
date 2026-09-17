# Seguimiento GalenoSv

## Contexto

`ProyectoFinaldeProgramacion` se usa como proyecto base para adaptar patrones hacia `GalenoSv`.
`GalenoSv` es el proyecto real de trabajo y representa una clinica odontologica.

## Clase miercoles 9 de septiembre

Se aplico el hilo de la clase sobre:

- PrimeFaces como libreria global de Open Liberty para evitar empacarlo dentro del WAR.
- Cambio de la dependencia de PrimeFaces a `provided`.
- Creacion de configuracion `configDropins/defaults/primefaces-lib.xml`.
- Uso de `cantidadRegistros` en el modelo en vez de dejar `rows` quemado en el XHTML.
- Seleccion de filas con `rowSelect`, `selectionMode="single"` y `selection`.
- Uso de `getRowKey` y `getRowData` en `LazyDataModel`.
- Uso de `p:toggleSwitch`, `p:inputTextarea`, `p:outputLabel`, `p:tooltip` y `p:commandButton`.
- Creacion de un composite component para botones superiores.
- Internacionalizacion con `faces-config.xml`, `messages.properties` y cambio de idioma en sesion.

## Archivos modificados o creados

- `pom.xml`: PrimeFaces `15.0.17` con classifier `jakarta` y scope `provided`.
- `src/main/liberty/config/configDropins/defaults/primefaces-lib.xml`: declara `library id="global"` para `primefaces-15.0.17-jakarta.jar`.
- `src/main/java/.../boundary/AbstractModel.java`: agrega `cantidadRegistros` y ajustes de seleccion.
- `src/main/java/.../boundary/AbstractModel.java`: se formateo a un estilo mas legible sin cambiar el patron ni la funcionalidad.
- `src/main/java/.../boundary/*Frm.java`: se formatearon los 20 modelos futuros para las paginas CRUD.
- `src/main/java/.../boundary/jsf/*Model.java`: se formatearon los modelos demo/actuales.
- `src/main/java/.../control/DefaultDAO.java`: se formateo la clase base DAO sin cambiar funcionalidad.
- `src/main/webapp/resources/crud/botonesTop.xhtml`: composite para botones `Nuevo` y `Cancelar`.
- `src/main/webapp/paginas/TipoMedioContacto.xhtml`: vista CRUD base de `TipoMedioContacto`.
- `src/main/webapp/WEB-INF/faces-config.xml`: registra locales y `resource-bundle` con variable `msg`.
- `src/main/resources/messages.properties`: textos en espanol.
- `src/main/resources/messages_en.properties`: textos en ingles.
- `src/main/resources/messages_fr.properties`: textos en frances.
- `src/main/java/.../boundary/IdiomaBean.java`: mantiene idioma/pais en sesion.

## Configuracion externa aplicada

Para que `mvn liberty:dev` encuentre PrimeFaces en runtime, se copio este jar en el Liberty local:

`/home/duran/wlp/lib/primefaces-15.0.17-jakarta.jar`

Tambien se copio la configuracion al servidor local:

`/home/duran/wlp/usr/servers/GalenoSv/configDropins/defaults/primefaces-lib.xml`

El archivo fuente de esa configuracion queda versionado dentro del proyecto en:

`src/main/liberty/config/configDropins/defaults/primefaces-lib.xml`

## Ajustes por revision del instructor

- `pom.xml`: Jakarta EE cambia de `11.0.0-M1` a `11.0.0`.
- `pom.xml`: compilacion configurada para Java 21.
- Configuracion Liberty: `openliberty.xml` se renombra a `primefaces-lib.xml`.
- Entidades: campos de fecha/hora migrados de `java.util.Date` con `@Temporal` a `java.time.OffsetDateTime`.
- DAO: `DefaultDAO` implementa `Serializable`, por lo que los DAO concretos quedan serializables por herencia.

## Pagina ExamenTipoExamen

- Se agrega `boundary/jsf/ExamenTipoExamenModel.java` para la pantalla activa JSF.
- Se agrego inicialmente `paginas/ExamenTipoExamen.xhtml` como pantalla independiente, pero luego se retiro del flujo activo para seguir el patron padre-detalle dentro de `Examen.xhtml`.
- La pantalla permite relacionar un `Examen` con un `TipoExamen` mediante selects.
- Se agrega enlace al menu general.
- Se elimina `boundary/ExamenTipoExamenFrm.java` para evitar duplicar el bean de una pagina ya migrada a `boundary/jsf`.

## Verificacion

- `mvn --no-transfer-progress clean compile -f GalenoSv/pom.xml`: exitoso.
- `mvn --no-transfer-progress package -DskipTests -f GalenoSv/pom.xml`: exitoso.
- WAR generado: `target/GalenoSv-1.0-SNAPSHOT.war`, tamano aproximado `140K`.
- Se verifico que el WAR no incluye `primefaces` dentro de `WEB-INF/lib`.
- El WAR si incluye:
  - `paginas/TipoMedioContacto.xhtml`
  - `paginas/Examen.xhtml`
  - `resources/crud/botonesTop.xhtml`
  - `WEB-INF/faces-config.xml`
  - `WEB-INF/classes/messages.properties`
  - `WEB-INF/classes/messages_en.properties`
  - `WEB-INF/classes/messages_fr.properties`

## Nota de pruebas

`mvn clean package` sin saltar pruebas falla en este entorno por Mockito/ByteBuddy con Java 21:
`Could not initialize inline Byte Buddy mock maker`.

No es un error de compilacion de `GalenoSv` ni de PrimeFaces; ocurre al ejecutar los tests porque Mockito intenta hacer self-attach del agente.

## Clase viernes 11 de septiembre

Se reviso la transcripcion `transcripciones/Viernes11Sep.pdf`. Se ignoro la parte de CouchDB porque no pertenece al proyecto.

Avance identificado:

- Uso de grid de PrimeFaces con `ui-g` y columnas de 12 espacios.
- Creacion de una plantilla general dentro de `WEB-INF/plantillas` para que no pueda pedirse directamente desde el navegador.
- Uso de `ui:composition`, `ui:insert` y `ui:define` para que las paginas reutilicen el layout.
- Selector de idioma movido a la plantilla, usando el `IdiomaBean` de sesion.
- `faces-config.xml` ya existia; se mantiene el `resource-bundle` con variable `msg`.
- Se empieza el mantenimiento de `Examen` y la relacion `ExamenTipoExamen`.
- Se agregan metodos especificos en `ExamenTipoExamenDAO`, porque el CRUD generico ya no alcanza cuando una entidad depende de otra.

Archivos creados o ajustados:

- `src/main/webapp/WEB-INF/plantillas/general.xhtml`: plantilla general con encabezado, menu, selector de idioma, area de contenido, mensajes y footer.
- `src/main/webapp/paginas/TipoMedioContacto.xhtml`: ahora usa la plantilla general mediante `ui:composition`.
- `src/main/java/.../entity/ExamenTipoExamen.java`: agrega `NamedQuery` para buscar y contar registros por `idExamen`.
- `src/main/java/.../control/ExamenDAO.java`: se alinea con `@LocalBean` y formato del patron.
- `src/main/java/.../control/ExamenTipoExamenDAO.java`: agrega `findByIdExamen(UUID, int, int)` y `countByIdExamen(UUID)`.
- `src/main/resources/messages*.properties`: textos del layout en los tres idiomas ya existentes.

Verificacion:

- `xmllint --noout` sobre `general.xhtml`, `TipoMedioContacto.xhtml` y `faces-config.xml`: exitoso.
- `mvn --no-transfer-progress clean compile -f GalenoSv/pom.xml`: exitoso.
- `mvn --no-transfer-progress package -DskipTests -f GalenoSv/pom.xml`: exitoso.
- El WAR incluye `WEB-INF/plantillas/general.xhtml`, `paginas/TipoMedioContacto.xhtml` y los bundles `messages*.properties`.

## Paginas JSF agregadas

Se agregaron tres paginas nuevas siguiendo el mismo patron de `TipoMedioContacto.xhtml` y usando directamente la plantilla `WEB-INF/plantillas/general.xhtml`:

- `src/main/webapp/paginas/TipoDocumento.xhtml`
- `src/main/webapp/paginas/TipoExamen.xhtml`
- `src/main/webapp/paginas/Examen.xhtml`

Tambien se ajustaron los metodos `nuevoRegistro()` de los modelos usados por estas paginas para inicializar UUID y valores por defecto antes de guardar.

Se eliminaron las paginas antiguas de prueba `tipoExamen.xhtml` y `tipoExamen_real.xhtml` para evitar confusion con la pagina real `TipoExamen.xhtml`. La plantilla general ahora incluye enlaces de navegacion hacia las cuatro paginas actuales.

Tambien se eliminaron clases de prueba que ya no participan en el flujo actual:

- `src/main/java/.../boundary/AbstractModel2.java`
- `src/main/java/.../boundary/jsf/TipoExamenDemoModel.java`

Se elimino la carpeta `backup_original` porque solo contenia copias antiguas y no estaba referenciada por el proyecto.

Se migro el uso de backing beans de las paginas actuales al estilo `*Model`:

- `TipoDocumento.xhtml` usa `tipoDocumentoModel`.
- `Examen.xhtml` usa `examenModel`.
- Se crearon `TipoDocumentoModel` y `ExamenModel` en `boundary/jsf`.
- Se eliminaron `TipoDocumentoFrm`, `ExamenFrm`, `TipoExamenFrm` y `TipoMedioContactoFrm` para no tener dos beans equivalentes en las paginas actuales.

Se cambiaron los enlaces del menu a URLs explicitas `.jsf` y se configuro `jakarta.faces.FACELETS_REFRESH_PERIOD=0` para evitar ver XHTML viejos durante desarrollo.

## Clase miercoles 16 de septiembre

Se revisaron las transcripciones `transcripciones/trasncripcion16.pdf` y `transcripciones/trasncripcion16-parte2.pdf`.

Avance aplicado:

- `Examen.xhtml` ahora usa `p:tabView`.
- El primer tab mantiene el formulario principal de `Examen`.
- El segundo tab muestra los `TipoExamen` asociados al examen seleccionado.
- El tab de tipos queda deshabilitado hasta que el examen esta en modo `MODIFICAR`.
- `ExamenTipoExamenModel` cambio a `@Dependent` para vivir dentro de `ExamenModel`.
- `ExamenModel` inyecta `ExamenTipoExamenModel` y le pasa el `idExamen` seleccionado.
- `ExamenTipoExamenModel` carga la tabla secundaria usando `findByIdExamen` y `countByIdExamen`.
- `TipoExamen` agrega `NamedQuery TipoExamen.findByNombreLike`.
- `TipoExamenDAO` agrega `findByNombreLike(String, int, int)`.
- Se creo `TipoExamenConverter` con `@FacesConverter(managed = true)`.
- Se agrego dialog con `p:autoComplete` para seleccionar el tipo de examen.

Archivos creados o ajustados:

- `src/main/webapp/paginas/Examen.xhtml`
- `src/main/java/.../boundary/jsf/ExamenModel.java`
- `src/main/java/.../boundary/jsf/ExamenTipoExamenModel.java`
- `src/main/java/.../boundary/conversores/TipoExamenConverter.java`
- `src/main/java/.../control/TipoExamenDAO.java`
- `src/main/java/.../entity/TipoExamen.java`
- `src/main/resources/messages.properties`
- `src/main/resources/messages_en.properties`
- `src/main/resources/messages_fr.properties`

Verificacion:

- `mvn --no-transfer-progress compile -f GalenoSv/pom.xml`: exitoso.
- `mvn --no-transfer-progress clean package -DskipTests -f GalenoSv/pom.xml`: exitoso.
- El WAR incluye `paginas/Examen.xhtml`, `TipoExamenConverter`, `ExamenTipoExamenModel`, `TipoExamenDAO` y los bundles `messages*.properties`.

Nota de arquitectura:

- Se elimino la pagina independiente `paginas/ExamenTipoExamen.xhtml`.
- Se elimino el enlace de `ExamenTipoExamen` del menu principal.
- `ExamenTipoExamenModel` queda solo como modelo `@Dependent` usado desde `ExamenModel`.
- El flujo activo queda: `Examen` -> seleccionar registro -> tab `Tipos` -> agregar/modificar/eliminar tipos asociados.

## Flujo paciente y consulta

Se agregaron paginas y modelos siguiendo la estructura real de la base de datos, sin agregar campos que no existen:

- `Clinica`: catalogo de clinicas.
- `Rol`: catalogo de roles.
- `Persona`: datos base de la persona.
- `PersonaRol`: relaciona una persona con un rol y una clinica.
- `Consulta`: registra la atencion/consulta de una persona segun su `PersonaRol`.

Interpretacion del ejemplo:

`Paciente Juan Perez, observacion: dolor en molar`

- `Juan Perez` se registra en `Persona`.
- El rol `Paciente` se registra en `Rol`.
- La clinica se registra en `Clinica`.
- La condicion de paciente se crea en `PersonaRol`.
- La observacion `dolor en molar` va en `Consulta.observaciones`.
- El estado activo de una consulta se interpreta por `fechaFin` vacia.

El examen concreto como `radiografia panoramica` no se guarda directamente en `Consulta`, porque la base lo modela mas adelante mediante `OrdenExamen` y `ExamenResultado`.

Archivos creados o ajustados:

- `src/main/java/.../boundary/jsf/ClinicaModel.java`
- `src/main/java/.../boundary/jsf/RolModel.java`
- `src/main/java/.../boundary/jsf/PersonaModel.java`
- `src/main/java/.../boundary/jsf/PersonaRolModel.java`
- `src/main/java/.../boundary/jsf/ConsultaModel.java`
- `src/main/webapp/paginas/Clinica.xhtml`
- `src/main/webapp/paginas/Rol.xhtml`
- `src/main/webapp/paginas/Persona.xhtml`
- `src/main/webapp/paginas/PersonaRol.xhtml`
- `src/main/webapp/paginas/Consulta.xhtml`
- `src/main/java/.../control/PersonaRolDAO.java`
- `src/main/java/.../control/ConsultaDAO.java`
- `src/main/webapp/WEB-INF/plantillas/general.xhtml`
- `src/main/resources/messages*.properties`

Verificacion:

- `xmllint --noout` sobre las paginas nuevas y `general.xhtml`: exitoso.
- `mvn --no-transfer-progress clean package -DskipTests -f GalenoSv/pom.xml`: exitoso.
- El WAR incluye las paginas nuevas y sus modelos.

## Ajuste visual de tablas

Se ajustaron las tablas activas para mostrar la paginacion arriba y abajo, como en los ejemplos vistos en clase cuando PrimeFaces muestra las flechas en ambos extremos.

Paginas ajustadas:

- `TipoMedioContacto.xhtml`
- `TipoDocumento.xhtml`
- `TipoExamen.xhtml`
- `Examen.xhtml`
- `Clinica.xhtml`
- `Rol.xhtml`
- `Persona.xhtml`
- `PersonaRol.xhtml`
- `Consulta.xhtml`

Nota:

- Solo se cambia `paginatorPosition` de `bottom` a `both`.
- No se modifica el flujo de base de datos, entidades, DAO ni modelos.

## Paginas restantes de la base de datos

Se completan los modelos JSF y paginas XHTML que faltaban para cubrir las entidades restantes de la base de datos, manteniendo el flujo real de relaciones:

- `Documento`: relaciona persona y tipo de documento.
- `MedioContacto`: relaciona persona y tipo de medio de contacto.
- `Procedimiento`: catalogo de procedimientos.
- `ProcedimientoPaso`: pasos de un procedimiento y rol responsable.
- `ProcedimientoPasoSecuencia`: secuencia entre pasos.
- `ProcedimientoPasoExamen`: examenes requeridos por un paso.
- `ConsultaProcedimiento`: procedimiento iniciado dentro de una consulta.
- `ConsultaProcedimientoPaso`: paso ejecutado dentro de un procedimiento de consulta.
- `OrdenExamen`: orden generada desde un paso de consulta.
- `ExamenResultado`: resultado registrado para una orden de examen.

Tambien se actualiza el menu PrimeFaces:

- `Personas`: persona, persona rol, documento y medio de contacto.
- `Procedimientos`: pasos, secuencias y examenes requeridos por paso.
- `Atencion`: consulta, consulta procedimiento, paso de consulta, orden de examen y resultado.

Nota de flujo:

- El proyecto tiene 20 modelos JSF activos.
- Las paginas visibles quedan en 19 porque `ExamenTipoExamen` no es pagina independiente: se mantiene como detalle interno dentro de `Examen.xhtml`, siguiendo el flujo padre-detalle visto en clase.
- Las fechas de creacion/inicio se asignan automaticamente desde los modelos con `OffsetDateTime.now()`.
- Las relaciones se manejan con combos hacia las tablas correspondientes; no se agregan campos que no existan en la base.

Verificacion:

- `xmllint --noout` sobre paginas XHTML y plantilla general: exitoso.
- `mvn --no-transfer-progress clean package -DskipTests -f GalenoSv/pom.xml`: exitoso.
- `mvn --no-transfer-progress test -f GalenoSv/pom.xml`: exitoso, 11 pruebas sin fallos.

## Auditoria integral del 17 de septiembre de 2026

Se reviso el proyecto completo contra el esquema PostgreSQL que esta ejecutandose en Docker.

Correcciones realizadas:

- El contrato generico `InventarioDAOInterface` se renombro a `GalenoDAOInterface` para quitar el nombre heredado del proyecto de inventario.
- Se retiraron de `DefaultDAO` metodos duplicados que no usaba ninguna capa del proyecto.
- `AbstractModel` ahora valida `nombre` solamente en entidades que realmente tienen ese campo. Esto permite reutilizar la clase con entidades operativas como `Documento`, `OrdenExamen` y `ExamenResultado`.
- `Persona` ahora permite registrar y mostrar `fechaNacimiento`. La pagina usa `LocalDate` y el modelo la convierte a `OffsetDateTime` con la zona `America/El_Salvador`, respetando el tipo definido en la entidad y la base.
- `PersonaRol` ahora muestra su fecha de creacion.
- `ProcedimientoPasoSecuencia` usa un selector de pasos para la referencia, en lugar de pedir al usuario que escriba un UUID.
- Los campos cortos de los formularios respetan las longitudes reales de PostgreSQL.
- `PersonaDAO` ya no muestra trazas para validaciones normales ni oculta fallos de base de datos devolviendo listas vacias.
- Se elimino el `index.html` de prueba que solo mostraba `PPI` y no participaba en la navegacion actual.
- Se agrego `.gitignore` para que Maven no incluya `target/` en el control de versiones.
- Se quitaron comentarios `TODO` generados automaticamente que ya no representaban tareas pendientes.

Correspondencia comprobada:

- 20 entidades Java y 20 clases declaradas en `persistence.xml`.
- 20 DAO concretos, todos con `@Stateless` y `@PersistenceContext(unitName = "clinica_ppi")`.
- 20 modelos JSF con alcance CDI.
- 19 paginas visibles; `ExamenTipoExamenModel` es el detalle interno de `Examen.xhtml`.
- Todas las paginas aparecen en el menu y todos los enlaces del menu tienen una pagina existente.
- No quedan usos de `java.util.Date`, `@Temporal`, APIs `javax`, elementos deprecados ni referencias al contrato de inventario.

Verificacion final:

- XML de todas las paginas, plantillas y archivos de configuracion: valido con `xmllint`.
- `mvn --no-transfer-progress clean package`: exitoso.
- 14 pruebas ejecutadas: 0 fallos y 0 errores.
- Las 19 paginas respondieron `HTTP 200` desde Open Liberty con PostgreSQL conectado.
- Se probaron los formularios en estado `Nuevo`, incluido el calendario de Persona, sin guardar datos de prueba.
- El registro de Open Liberty no mostro excepciones nuevas durante el recorrido de paginas.

## Organizacion de convertidores

Los convertidores se movieron al paquete `boundary.conversores`, siguiendo la organizacion del proyecto base:

- `boundary/conversores/TipoExamenConverter.java`: convierte entre el valor enviado por JSF y la entidad `TipoExamen`.
- `boundary/conversores/UUIDConverter.java`: conserva el mapeo de UUID entre Java y PostgreSQL.

Se eliminaron los paquetes vacios `Clases` y `boundary/jsf/converters`. Los modelos de las paginas permanecen en `boundary/jsf`.
