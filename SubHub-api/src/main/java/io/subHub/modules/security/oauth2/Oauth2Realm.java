package io.subHub.modules.security.oauth2;

import io.subHub.common.exception.ErrorCode;
import io.subHub.common.redis.RedisUtils;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.common.utils.MessageUtils;
import io.subHub.modules.security.user.UserDetail;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * authentication
 */
@Component
public class Oauth2Realm extends AuthorizingRealm {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * Authorization (called when verifying permissions)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * Authentication (called upon login)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        //Query user information based on accessToken
        Object user = redisUtils.get(accessToken);
        //Token failure
        if(user == null){
            throw new IncorrectCredentialsException(MessageUtils.getMessage(ErrorCode.TOKEN_INVALID));
        }
        UserDetail userDetail = ConvertUtils.sourceToTarget(user, UserDetail.class);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDetail, accessToken, getName());
        return info;
    }

}
