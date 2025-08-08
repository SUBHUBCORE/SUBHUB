package io.subHub.common.validator.validator;

import io.subHub.common.validator.anno.Values;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**

 * @description: check
 */
public class ValuesConstraintValidator implements ConstraintValidator<Values,Integer> {
    private String[] value;

    @Override
    public void initialize(Values constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null) {
            return true;
        }
        for (String s : value) {
            if (Objects.equals(s,integer.toString())) {
                return true;
            }

        }
        return false;
    }
}
