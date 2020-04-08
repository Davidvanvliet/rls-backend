package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.code.TractionType;
import org.dozer.CustomConverter;

public class TractionTypeConverter implements CustomConverter {
    @Override
    public Object convert(Object o, Object o1, Class<?> aClass, Class<?> aClass1) {
        TractionType tractionType = new TractionType();
        tractionType.setCode((String) o1);
        return tractionType;
    }
}
