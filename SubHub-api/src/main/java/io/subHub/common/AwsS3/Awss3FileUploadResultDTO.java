package io.subHub.common.AwsS3;

import lombok.Data;

@Data
public class Awss3FileUploadResultDTO {

    private String fileKey;
    private String fileUrl;
}
