package io.subHub.common.validator.anno;

import io.subHub.common.validator.validator.ValuesConstraintValidator;
import io.subHub.common.validator.validator.ValuesStrConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @description: Parameter verification
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {ValuesConstraintValidator.class, ValuesStrConstraintValidator.class}
)
public @interface Values {

    String[] value();

    String message() default "parameter error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
