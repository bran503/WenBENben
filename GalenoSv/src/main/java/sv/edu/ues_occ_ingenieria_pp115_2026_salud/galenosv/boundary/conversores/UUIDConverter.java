package sv.edu.ues_occ_ingenieria_pp115_2026_salud.galenosv.boundary.conversores;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = false)
public class UUIDConverter implements AttributeConverter<UUID, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }
}
