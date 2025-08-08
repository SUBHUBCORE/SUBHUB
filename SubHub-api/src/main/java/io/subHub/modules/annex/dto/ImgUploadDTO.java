package io.subHub.modules.annex.dto;

import io.subHub.common.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ImgUploadDTO {

    @NotEmpty(message="content name cannot be empty", groups = AddGroup.class)
    private String name;
    @NotEmpty(message="content name cannot be empty", groups = AddGroup.class)
    private String content;
}
