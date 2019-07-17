package cn.imooc.ad.delivery.controller;

import cn.imooc.ad.common.exception.AdException;
import cn.imooc.ad.delivery.service.IUserService;
import cn.imooc.ad.delivery.vo.CreateUserRequest;
import cn.imooc.ad.delivery.vo.CreateUserResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Qinyi.
 */
@Slf4j
@RestController
public class UserOPController {

    private final IUserService userService;

    @Autowired
    public UserOPController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}",
                JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
