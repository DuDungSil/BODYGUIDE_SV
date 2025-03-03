package org.bodyguide_sv.notification.enums.converter;

import org.bodyguide_sv.notification.enums.NotificationType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(NotificationType attribute) {
        return (attribute != null) ? attribute.getCode() : null;
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? NotificationType.fromCode(dbData) : null;
    }

}
