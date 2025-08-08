package io.subHub.modules.security.user;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * Login User Information
 *
 * @author By
 */
@Data
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String address;
    private String fullName;
    private Integer disabled;
    private String scope;
    private String inviteCode;
    private Date createDt;
    private Date updateDt;
    private Long cid;
    private int userType;

}
