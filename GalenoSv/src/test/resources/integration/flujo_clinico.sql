\set ON_ERROR_STOP on

BEGIN;

-- Datos identificables y repetibles para probar el flujo clinico completo.
INSERT INTO clinica (id_clinica, nombre, activo, tipo, comentarios)
VALUES ('10000000-0000-0000-0000-000000000001', 'Clinica GalenoSv - Prueba', true,
        'ODONTOLOGICA', 'Registro creado por la prueba de integracion')
ON CONFLICT (id_clinica) DO NOTHING;

INSERT INTO tipo_documento (id_tipo_documento, nombre, indicaciones, expresion_regular, activo)
VALUES ('11000000-0000-0000-0000-000000000001', 'DUI - Prueba',
        'Documento utilizado por la prueba de integracion', '^[0-9]{8}-[0-9]$', true)
ON CONFLICT (id_tipo_documento) DO NOTHING;

INSERT INTO tipo_medio_contacto
    (id_tipo_medio_contacto, nombre, indicaciones, expresion_regular, activo)
VALUES ('12000000-0000-0000-0000-000000000001', 'Telefono - Prueba',
        'Contacto utilizado por la prueba de integracion', '^[0-9]{4}-[0-9]{4}$', true)
ON CONFLICT (id_tipo_medio_contacto) DO NOTHING;

INSERT INTO persona (id_persona, nombres, apellidos, fecha_nacimiento, fecha_creacion)
VALUES
    ('20000000-0000-0000-0000-000000000001', 'Juan Integracion', 'Perez',
     '1990-05-15 00:00:00-06', '2026-09-24 09:00:00-06'),
    ('20000000-0000-0000-0000-000000000002', 'Ana Prueba', 'Lopez',
     '1985-03-10 00:00:00-06', '2026-09-24 09:00:00-06')
ON CONFLICT (id_persona) DO NOTHING;

INSERT INTO documento (id_documento, id_persona, id_tipo_documento, valor, ruta_fisica)
VALUES ('21000000-0000-0000-0000-000000000001',
        '20000000-0000-0000-0000-000000000001',
        '11000000-0000-0000-0000-000000000001', '01234567-8', NULL)
ON CONFLICT (id_documento) DO NOTHING;

INSERT INTO medio_contacto
    (id_medio_contacto, id_persona, id_tipo_medio_contacto, valor, fecha_creacion)
VALUES ('22000000-0000-0000-0000-000000000001',
        '20000000-0000-0000-0000-000000000001',
        '12000000-0000-0000-0000-000000000001', '7777-1234',
        '2026-09-24 09:00:00-06')
ON CONFLICT (id_medio_contacto) DO NOTHING;

INSERT INTO rol (id_rol, nombre, activo, observaciones)
VALUES
    ('30000000-0000-0000-0000-000000000001', 'Paciente - Prueba', true,
     'Rol utilizado por la prueba de integracion'),
    ('30000000-0000-0000-0000-000000000002', 'Odontologo - Prueba', true,
     'Rol utilizado por la prueba de integracion')
ON CONFLICT (id_rol) DO NOTHING;

INSERT INTO persona_rol (id_persona_rol, id_persona, id_rol, fecha_creacion, id_clinica)
VALUES
    ('40000000-0000-0000-0000-000000000001',
     '20000000-0000-0000-0000-000000000001',
     '30000000-0000-0000-0000-000000000001', '2026-09-24 09:05:00-06',
     '10000000-0000-0000-0000-000000000001'),
    ('40000000-0000-0000-0000-000000000002',
     '20000000-0000-0000-0000-000000000002',
     '30000000-0000-0000-0000-000000000002', '2026-09-24 09:05:00-06',
     '10000000-0000-0000-0000-000000000001')
ON CONFLICT (id_persona_rol) DO NOTHING;

INSERT INTO tipo_examen (id_tipo_examen, nombre, activo, observaciones)
VALUES ('50000000-0000-0000-0000-000000000001', 'Rayos X - Prueba', true,
        'Tipo creado por la prueba de integracion')
ON CONFLICT (id_tipo_examen) DO NOTHING;

INSERT INTO examen (id_examen, nombre, activo, observaciones)
VALUES ('51000000-0000-0000-0000-000000000001', 'Radiografia panoramica - Prueba', true,
        'Examen creado por la prueba de integracion')
ON CONFLICT (id_examen) DO NOTHING;

INSERT INTO examen_tipo_examen
    (id_examen_tipo_examen, id_examen, id_tipo_examen, fecha_creacion, observaciones)
VALUES ('52000000-0000-0000-0000-000000000001',
        '51000000-0000-0000-0000-000000000001',
        '50000000-0000-0000-0000-000000000001', '2026-09-24 09:10:00-06',
        'Relacion creada por la prueba de integracion')
ON CONFLICT (id_examen_tipo_examen) DO NOTHING;

INSERT INTO procedimiento (id_procedimiento, nombre, activo, observaciones)
VALUES ('60000000-0000-0000-0000-000000000001', 'Evaluacion de dolor molar - Prueba', true,
        'Procedimiento creado por la prueba de integracion')
ON CONFLICT (id_procedimiento) DO NOTHING;

INSERT INTO procedimiento_paso
    (id_procedimiento_paso, id_procedimiento, nombre, indica_fin, id_rol)
VALUES
    ('61000000-0000-0000-0000-000000000001',
     '60000000-0000-0000-0000-000000000001', 'Evaluacion clinica - Prueba', false,
     '30000000-0000-0000-0000-000000000002'),
    ('61000000-0000-0000-0000-000000000002',
     '60000000-0000-0000-0000-000000000001', 'Revision radiografica - Prueba', true,
     '30000000-0000-0000-0000-000000000002')
ON CONFLICT (id_procedimiento_paso) DO NOTHING;

INSERT INTO procedimiento_paso_secuencia
    (id_procedimiento_paso_secuencia, id_procedimiento_paso,
     id_procedimiento_paso_referencia, tipo_secuencia)
VALUES ('62000000-0000-0000-0000-000000000001',
        '61000000-0000-0000-0000-000000000002',
        '61000000-0000-0000-0000-000000000001', 'DESPUES_DE')
ON CONFLICT (id_procedimiento_paso_secuencia) DO NOTHING;

INSERT INTO procedimiento_paso_examen
    (id_procedimiento_paso_examen, id_procedimiento_paso, id_examen,
     fecha_creacion, activo, observaciones)
VALUES ('63000000-0000-0000-0000-000000000001',
        '61000000-0000-0000-0000-000000000002',
        '51000000-0000-0000-0000-000000000001', '2026-09-24 09:15:00-06', true,
        'Examen requerido por la prueba de integracion')
ON CONFLICT (id_procedimiento_paso_examen) DO NOTHING;

INSERT INTO consulta
    (id_consulta, fecha_inicio, fecha_fin, referencia_externa, observaciones, id_persona_rol)
VALUES ('70000000-0000-0000-0000-000000000001', '2026-09-24 10:00:00-06', NULL,
        'PRUEBA-INTEGRACION-001', 'Dolor en molar; consulta activa de prueba',
        '40000000-0000-0000-0000-000000000001')
ON CONFLICT (id_consulta) DO NOTHING;

INSERT INTO consulta_procedimiento
    (id_consulta_procedimiento, id_consulta, id_procedimiento,
     fecha_inicio, fecha_fin, observaciones)
VALUES ('71000000-0000-0000-0000-000000000001',
        '70000000-0000-0000-0000-000000000001',
        '60000000-0000-0000-0000-000000000001', '2026-09-24 10:05:00-06', NULL,
        'Seguimiento creado por la prueba de integracion')
ON CONFLICT (id_consulta_procedimiento) DO NOTHING;

INSERT INTO consulta_procedimiento_paso
    (id_consulta_procedimiento_paso, id_consulta_procedimiento, id_persona_rol,
     fecha_inicio, fecha_fin, estado)
VALUES
    ('72000000-0000-0000-0000-000000000001',
     '71000000-0000-0000-0000-000000000001',
     '40000000-0000-0000-0000-000000000002', '2026-09-24 10:10:00-06',
     '2026-09-24 10:25:00-06', 'FINALIZADO'),
    ('72000000-0000-0000-0000-000000000002',
     '71000000-0000-0000-0000-000000000001',
     '40000000-0000-0000-0000-000000000002', '2026-09-24 10:25:00-06',
     NULL, 'ACTIVO')
ON CONFLICT (id_consulta_procedimiento_paso) DO NOTHING;

INSERT INTO orden_examen
    (id_orden_examen, id_consulta_procedimiento_paso, fecha_creacion, indicaciones)
VALUES ('73000000-0000-0000-0000-000000000001',
        '72000000-0000-0000-0000-000000000002', '2026-09-24 10:30:00-06',
        'Realizar radiografia panoramica por dolor en molar')
ON CONFLICT (id_orden_examen) DO NOTHING;

INSERT INTO examen_resultado
    (id_examen_resultado, id_orden_examen, fecha_creacion, resultado,
     interpretacion, ruta_atestado)
VALUES ('74000000-0000-0000-0000-000000000001',
        '73000000-0000-0000-0000-000000000001', '2026-09-24 11:00:00-06',
        'Radiografia panoramica completada',
        'Hallazgo de caries profunda en molar; resultado de prueba',
        '/atestados/prueba-integracion-radiografia.pdf')
ON CONFLICT (id_examen_resultado) DO NOTHING;

DO $$
DECLARE
    registros integer;
BEGIN
    SELECT count(*) INTO registros
    FROM examen_resultado er
    JOIN orden_examen oe
      ON oe.id_orden_examen = er.id_orden_examen
    JOIN consulta_procedimiento_paso cpp
      ON cpp.id_consulta_procedimiento_paso = oe.id_consulta_procedimiento_paso
    JOIN consulta_procedimiento cp
      ON cp.id_consulta_procedimiento = cpp.id_consulta_procedimiento
    JOIN consulta c
      ON c.id_consulta = cp.id_consulta
    JOIN persona_rol paciente_rol
      ON paciente_rol.id_persona_rol = c.id_persona_rol
    JOIN persona paciente
      ON paciente.id_persona = paciente_rol.id_persona
    WHERE er.id_examen_resultado = '74000000-0000-0000-0000-000000000001'
      AND paciente.id_persona = '20000000-0000-0000-0000-000000000001';

    IF registros <> 1 THEN
        RAISE EXCEPTION 'El flujo paciente-consulta-procedimiento-orden-resultado no quedo completo';
    END IF;
END $$;

COMMIT;

SELECT paciente.nombres || ' ' || paciente.apellidos AS paciente,
       c.observaciones AS motivo_consulta,
       p.nombre AS procedimiento,
       oe.indicaciones AS orden,
       er.resultado,
       er.interpretacion
FROM examen_resultado er
JOIN orden_examen oe
  ON oe.id_orden_examen = er.id_orden_examen
JOIN consulta_procedimiento_paso cpp
  ON cpp.id_consulta_procedimiento_paso = oe.id_consulta_procedimiento_paso
JOIN consulta_procedimiento cp
  ON cp.id_consulta_procedimiento = cpp.id_consulta_procedimiento
JOIN procedimiento p
  ON p.id_procedimiento = cp.id_procedimiento
JOIN consulta c
  ON c.id_consulta = cp.id_consulta
JOIN persona_rol paciente_rol
  ON paciente_rol.id_persona_rol = c.id_persona_rol
JOIN persona paciente
  ON paciente.id_persona = paciente_rol.id_persona
WHERE er.id_examen_resultado = '74000000-0000-0000-0000-000000000001';

-- Limitacion detectada: consulta_procedimiento_paso no contiene
-- id_procedimiento_paso. Por eso la orden no puede relacionarse de manera
-- inequivoca con el examen requerido por un paso especifico.
