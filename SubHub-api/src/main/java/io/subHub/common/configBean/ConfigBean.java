package io.subHub.common.configBean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigBean {
    @Value("${twitter_dev_platform.client_id}")
    private String twitter_clientId;
    @Value("${twitter_dev_platform.client_secret}")
    private String twitter_clientSecret;
    @Value("${twitter_dev_platform.code_verifier}")
    private String twitter_codeVerifier;
    @Value("${twitter_dev_platform.code_challenge}")
    private String twitter_codeChallenge;
    @Value("${twitter_dev_platform.state}")
    private String twitter_state;
    @Value("${twitter_dev_platform.redirect_uri}")
    private String twitter_redirectUri;
    @Value("${twitter_dev_platform.local_redirect_uri}")
    private String twitter_localRedirectUri;
    @Value("${twitter_dev_platform.dev_redirect_uri}")
    private String twitter_devRedirectUri;
    @Value("${twitter_dev_platform.bearer_token}")
    private String bearer_token;

    @Value("${google.client_id}")
    private String google_client_id;

    @Value("${grpc.url}")
    private String grpc_url;
    @Value("${grpc.platform}")
    private String grpc_platform;

    @Value("${mq.host}")
    private String mq_host;
    @Value("${mq.port}")
    private int mq_port;
    @Value("${mq.username}")
    private String mq_username;
    @Value("${mq.password}")
    private String mq_password;

    @Value("${linode_object_storage.bucket_name}")
    private String bucketName;

    @Value("${linode_object_storage.endpoint}")
    private String endpoint;

    @Value("${linode_object_storage.access_key}")
    private String accessKey;

    @Value("${linode_object_storage.secret_key}")
    private String secretKey;

    @Value("${chain_endpoints.bsc.url}")
    private String bscUrl;


}
