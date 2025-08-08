package io.subHub.common.AwsS3;

import io.subHub.modules.security.oauth2.TokenGenerator;
import io.subHub.common.configBean.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Awss3FileUtil {
    final private static Logger logger = LoggerFactory.getLogger(Awss3FileUtil.class);

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

    private static final String BASE64_PATTERN = "^(data:image/([A-Za-z0-9-]+);base64,)?([A-Za-z0-9+/=]+)$";

    public static Awss3FileUploadResultDTO uploadFile(MultipartFile file, ConfigBean configBean){
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(configBean.getAccessKey(), configBean.getSecretKey());

        S3Client s3 = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create("https://"+configBean.getEndpoint()))
                .build();
        String bucketName = configBean.getBucketName();
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            return null;
        }
        LocalDate currentDate = LocalDate.now();
        String fileKey = "subhubProjectImg/"+currentDate.format(formatter)+"/"+ TokenGenerator.generateValue()+fileName.substring(fileName.lastIndexOf(".") + 1);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .contentType(file.getContentType())
                .build();
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectResponse response = s3.putObject(request,
                    RequestBody.fromInputStream(inputStream, file.getSize()));
            logger.info("File uploaded successfully. ETag: " + response.eTag());
            inputStream.close();
            if (response.eTag()!=null){
                Awss3FileUploadResultDTO resultDTO = new Awss3FileUploadResultDTO();
                resultDTO.setFileKey(fileKey);
                resultDTO.setFileUrl("https://"+configBean.getBucketName()+"."+configBean.getEndpoint()+"/"+fileKey);
                return resultDTO;
            }
        } catch (S3Exception e) {
            logger.error("S3Exception:",e);
            e.printStackTrace();
        } catch (Exception e){
            logger.error("Exception:",e);
            e.printStackTrace();
        }
        return null;
    }

    public static Awss3FileUploadResultDTO uploadBase64(String base64File,ConfigBean configBean){
        if (!isValidBase64(base64File)) {
            throw new IllegalArgumentException("Invalid Base64 string");
        }
        String fileSuffix = getImageFormatFromBase64(base64File);
        base64File = removeBase64Prefix(base64File);
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(configBean.getAccessKey(), configBean.getSecretKey());
        S3Client s3 = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create("https://"+configBean.getEndpoint()))
                .build();

        String bucketName = configBean.getBucketName();

        LocalDate currentDate = LocalDate.now();
        String fileKey = "subhubProjectImg/"+currentDate.format(formatter)+"/"+TokenGenerator.generateValue()+"."+fileSuffix;
        logger.info("uploadBase64  fileKey:"+fileKey);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .contentType("image/jpeg")
                .build();
        byte[] decodedBytes = Base64.getDecoder().decode(base64File);
        try (InputStream inputStream = new ByteArrayInputStream(decodedBytes)) {
            PutObjectResponse response = s3.putObject(request,
                    RequestBody.fromInputStream(inputStream, decodedBytes.length));
            logger.info("uploadBase64 uploaded successfully. ETag: " + response.eTag());
            inputStream.close();
            if (response.eTag()!=null){
                Awss3FileUploadResultDTO resultDTO = new Awss3FileUploadResultDTO();
                resultDTO.setFileKey(fileKey);
                resultDTO.setFileUrl("https://"+configBean.getBucketName()+"."+configBean.getEndpoint()+"/"+fileKey);
                logger.info("successfully  url:"+"https://"+configBean.getBucketName()+"."+configBean.getEndpoint()+"/"+fileKey);
                return resultDTO;
            }
        } catch (S3Exception e) {
            logger.error("S3Exception:",e);
            e.printStackTrace();
        } catch (Exception e){
            logger.error("Exception:",e);
        }
        return null;
    }

    public static Awss3FileUploadResultDTO uploadFileUrl(String fileUrl, ConfigBean configBean){
        try {
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(configBean.getAccessKey(), configBean.getSecretKey());

            S3Client s3 = S3Client.builder()
                    .region(Region.AP_SOUTH_1)
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                    .endpointOverride(URI.create("https://"+configBean.getEndpoint()))
                    .build();
            InputStream inputStream = new URL(fileUrl).openStream();
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int contentLength = connection.getContentLength();
            String bucketName = configBean.getBucketName();
            LocalDate currentDate = LocalDate.now();
            String fileKey = "subhubProjectImg/"+currentDate.format(formatter)+"/"+TokenGenerator.generateValue()+getFileNameByUrl(fileUrl);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(connection.getContentType())
                    .build();
            PutObjectResponse response = s3.putObject(request,
                    RequestBody.fromInputStream(inputStream, contentLength));
            logger.info("uploadFileUrl successfully. ETag: " + response.eTag());
            inputStream.close();
            if (response.eTag()!=null){
                Awss3FileUploadResultDTO resultDTO = new Awss3FileUploadResultDTO();
                resultDTO.setFileKey(fileKey);
                resultDTO.setFileUrl("https://"+configBean.getBucketName()+"."+configBean.getEndpoint()+"/"+fileKey);
                return resultDTO;
            }
        } catch (S3Exception e) {
            logger.error("S3Exception:",e);
            e.printStackTrace();
        } catch (Exception e){
            logger.error("Exception:",e);
        }
        return null;
    }

    public static void deleteFile(String fileKye,ConfigBean configBean){
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(configBean.getAccessKey(), configBean.getSecretKey());
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create("https://"+configBean.getEndpoint())) // Linode S3 endpoint
                .region(Region.AP_SOUTH_1)
                .build();
        try {
            String bucketName = configBean.getBucketName();;
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKye)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            logger.info("File deleted successfully!");
        } catch (S3Exception e) {
            logger.info("Error deleting file: " + e.awsErrorDetails().errorMessage());
            e.printStackTrace();
        }
    }


    public static String getImageFormatFromBase64(String base64String) {
        String regex = "data:image/(.*?);base64,";
        if (base64String != null && base64String.matches(".*" + regex + ".*")) {
            String format = base64String.replaceAll("data:image/(.*?);base64,.*", "$1");
            if ("jpeg".equals(format)) {
                return "jpg";
            }
            return format;
        }
        return null;
    }

    public static String getFileNameByUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String fileName = Paths.get(url.getPath()).getFileName().toString();
            return fileName.substring(fileName.lastIndexOf('.'));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String processBase64(String base64Str) {
        if (!isValidBase64(base64Str)) {
            throw new IllegalArgumentException("Invalid Base64 string");
        }
        return removeBase64Prefix(base64Str);
    }

    private static boolean isValidBase64(String base64Str) {
        Pattern pattern = Pattern.compile(BASE64_PATTERN);
        Matcher matcher = pattern.matcher(base64Str);
        return matcher.matches();
    }
    private static String removeBase64Prefix(String base64Str) {
        if (base64Str != null && base64Str.startsWith("data:image")) {
            String[] parts = base64Str.split(",");
            if (parts.length > 1) {
                return parts[1];
            }
        }
        return base64Str;
    }
}
