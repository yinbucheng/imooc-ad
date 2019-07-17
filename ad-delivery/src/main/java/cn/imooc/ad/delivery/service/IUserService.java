package cn.imooc.ad.delivery.service;

import cn.imooc.ad.common.exception.AdException;
import cn.imooc.ad.delivery.vo.CreateUserRequest;
import cn.imooc.ad.delivery.vo.CreateUserResponse;


/**
 * Created by Qinyi.
 */
public interface IUserService {

    /**
     * <h2>创建用户</h2>
     * */
    CreateUserResponse createUser(CreateUserRequest request)
            throws AdException;
}
