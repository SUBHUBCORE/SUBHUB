package io.subHub.common.validator.validator;

import io.subHub.common.validator.anno.Values;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @description: check
 */
public class ValuesStrConstraintValidator implements ConstraintValidator<Values,String> {
    private String[] value;

    @Override
    public void initialize(Values constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        for (String s : value) {
            if (Objects.equals(s,str)) {
                return true;
            }

        }
        return false;
    }
}
