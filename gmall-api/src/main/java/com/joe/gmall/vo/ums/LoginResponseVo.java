package com.joe.gmall.vo.ums;

import lombok.Data;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-13
 */
@Data
public class LoginResponseVo {

    private Long memberLevelId;
    private String username;
    private String nickname;
    private String phone;
    private String accessToken;

}
