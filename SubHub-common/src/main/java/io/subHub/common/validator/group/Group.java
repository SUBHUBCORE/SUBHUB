package io.subHub.common.validator.group;

import javax.validation.GroupSequence;

/**
 * Define the verification order, if the AddGroup group fails, the UpdateGroup group will not verify again
 *
 * @author By
 * @since 1.0.0
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
