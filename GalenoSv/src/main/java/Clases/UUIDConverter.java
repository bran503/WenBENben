package Clases;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

/**
 * Converter correcto para PostgreSQL uuid <-> java.util.UUID.
 * Modo EXPLÍCITO: cada campo UUID debe anotarse con
 * @Convert(converter = UUIDConverter.class)
 * 
 * En JPA 3.1 + driver PostgreSQL moderno, UUID ya se mapea nativo,
 * este converter asegura null-safety y compatibilidad con PGobject.
 *
 * @author duran
 */
@Converter(autoApply = false)
public class UUIDConverter implements AttributeConverter<UUID, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        // Java -> PostgreSQL uuid (se envía como UUID nativo)
        return attribute; // null safe: si es null, JPA inserta NULL
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        // PostgreSQL uuid -> Java
        return dbData;
    }
}
