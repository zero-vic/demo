package com.hy.im.codec.pack.user;


import lombok.Data;





@Data
public class UserStatusChangeNotifyPack {

    private Integer appId;

    private String userId;

    private Integer status;

    private String siteId;

//    private List<UserSession> client;

}
