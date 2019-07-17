package cn.imooc.ad.delivery.service.impl;


import cn.imooc.ad.common.exception.AdException;
import cn.imooc.ad.delivery.constant.CommonStatus;
import cn.imooc.ad.delivery.constant.Constants;
import cn.imooc.ad.delivery.dao.AdPlanMapper;
import cn.imooc.ad.delivery.dao.AdUserMapper;
import cn.imooc.ad.delivery.entity.AdPlan;
import cn.imooc.ad.delivery.entity.AdUser;
import cn.imooc.ad.delivery.service.IAdPlanService;
import cn.imooc.ad.delivery.utils.CommonUtils;
import cn.imooc.ad.delivery.vo.AdPlanGetRequest;
import cn.imooc.ad.delivery.vo.AdPlanRequest;
import cn.imooc.ad.delivery.vo.AdPlanResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Created by Qinyi.
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdUserMapper userRepository;
    @Autowired
    private AdPlanMapper planRepository;


    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request)
            throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的 User 是存在的
        AdUser adUser =
                userRepository.selectById(request.getUserId());
        if (Objects.isNull(adUser)) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        Wrapper<AdPlan> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", request.getUserId()).and().eq("plan_name", request.getPlanName());
        List<AdPlan> oldPlans = planRepository.selectList(
                wrapper
        );
        if (!CollectionUtils.isEmpty(oldPlans)) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())
        );
        planRepository.insert(newAdPlan);

        return new AdPlanResponse(newAdPlan.getId(),
                newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request)
            throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Wrapper<AdPlan> wrapper = new EntityWrapper<>();
        wrapper.in("id", request.getIds()).and().eq("user_id", request.getUserId());
        return planRepository.selectList(wrapper);
    }

    @Override
    @SuppressWarnings("all")
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request)
            throws AdException {

        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Wrapper<AdPlan> wrapper = new EntityWrapper<>();
        wrapper.eq("id", request.getId()).and().eq("user_id", request.getUserId());
        List<AdPlan> plans = planRepository.selectList(wrapper);
        if (CollectionUtils.isEmpty(plans)) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan plan = plans.get(0);
        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(
                    CommonUtils.parseStringDate(request.getStartDate())
            );
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(
                    CommonUtils.parseStringDate(request.getEndDate())
            );
        }

        plan.setUpdateTime(new Date());
        planRepository.insert(plan);

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @SuppressWarnings("all")
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {

        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Wrapper<AdPlan> wrapper = new EntityWrapper<>();
        wrapper.eq("id", request.getId()).and().eq("user_id", request.getUserId());

        List<AdPlan> plans = planRepository.selectList(wrapper);
        if (CollectionUtils.isEmpty(plans)) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan plan = plans.get(0);
        plan.setPlanStatus(CommonStatus.INVALID.getCode());
        plan.setUpdateTime(new Date());
        planRepository.insert(plan);
    }
}
