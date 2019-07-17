package cn.imooc.ad.delivery.service.impl;

import cn.imooc.ad.common.exception.AdException;
import cn.imooc.ad.delivery.constant.Constants;
import cn.imooc.ad.delivery.dao.AdUserMapper;
import cn.imooc.ad.delivery.entity.AdUser;
import cn.imooc.ad.delivery.service.IUserService;
import cn.imooc.ad.delivery.utils.CommonUtils;
import cn.imooc.ad.delivery.vo.CreateUserRequest;
import cn.imooc.ad.delivery.vo.CreateUserResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Qinyi.
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private AdUserMapper userRepository;


    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request)
            throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Wrapper<AdUser> wrapper = new EntityWrapper<>();
        wrapper.eq("username", request.getUsername());
        List<AdUser> oldUsers = userRepository.
                selectList(wrapper);
        if (CollectionUtils.isEmpty(oldUsers)) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = new AdUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername()));
        userRepository.insert(newUser);

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getPassword(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
