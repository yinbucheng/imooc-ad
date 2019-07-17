package cn.imooc.ad.delivery.service.impl;


import cn.imooc.ad.common.exception.AdException;
import cn.imooc.ad.delivery.constant.Constants;
import cn.imooc.ad.delivery.dao.AdPlanMapper;
import cn.imooc.ad.delivery.dao.AdUnitMapper;
import cn.imooc.ad.delivery.dao.CreativeMapper;
import cn.imooc.ad.delivery.dao.unit_condition.AdUnitDistrictMapper;
import cn.imooc.ad.delivery.dao.unit_condition.AdUnitItMapper;
import cn.imooc.ad.delivery.dao.unit_condition.AdUnitKeywordMapper;
import cn.imooc.ad.delivery.dao.unit_condition.CreativeUnitMapper;
import cn.imooc.ad.delivery.entity.AdPlan;
import cn.imooc.ad.delivery.entity.AdUnit;
import cn.imooc.ad.delivery.entity.Creative;
import cn.imooc.ad.delivery.entity.unit_condition.AdUnitDistrict;
import cn.imooc.ad.delivery.entity.unit_condition.AdUnitIt;
import cn.imooc.ad.delivery.entity.unit_condition.AdUnitKeyword;
import cn.imooc.ad.delivery.entity.unit_condition.CreativeUnit;
import cn.imooc.ad.delivery.service.IAdUnitService;
import cn.imooc.ad.delivery.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Qinyi.
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Autowired
    private AdPlanMapper planRepository;
    @Autowired
    private AdUnitMapper unitRepository;
    @Autowired
    private AdUnitKeywordMapper unitKeywordRepository;
    @Autowired
    private AdUnitItMapper unitItRepository;
    @Autowired
    private AdUnitDistrictMapper unitDistrictRepository;
    @Autowired
    private CreativeMapper creativeRepository;
    @Autowired
    private CreativeUnitMapper creativeUnitRepository;


    @Override
    public AdUnitResponse createUnit(AdUnitRequest request)
            throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan =
                planRepository.selectById(request.getPlanId());
        if (Objects.isNull(adPlan)) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        Wrapper<AdUnit> wrapper = new EntityWrapper<>();
        wrapper.eq("plan_id", request.getPlanId()).and().eq("unit_name", request.getUnitName());
        List<AdUnit> oldAdUnits = unitRepository.selectList(wrapper);
        if (!CollectionUtils.isEmpty(oldAdUnits)) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget());
        unitRepository.insert(newAdUnit);

        return new AdUnitResponse(newAdUnit.getId(),
                newAdUnit.getUnitName());
    }

    @Override
    @Transactional
    public AdUnitKeywordResponse createUnitKeyword(
            AdUnitKeywordRequest request) throws AdException {

        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {

            request.getUnitKeywords().forEach(i -> {
                        AdUnitKeyword keyword = new AdUnitKeyword(i.getUnitId(), i.getKeyword());
                        unitKeywordRepository.insert(keyword);
                        ids.add(keyword.getId());
                    }
            );
        }


        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(
            AdUnitItRequest request) throws AdException {

        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.EMPTY_LIST;
        request.getUnitIts().forEach(i -> {
                    AdUnitIt unitIt = new AdUnitIt(i.getUnitId(), i.getItTag());
                    unitItRepository.insert(unitIt);
                    ids.add(unitIt.getId());
                }
        );

        return new AdUnitItResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitDistrictResponse createUnitDistrict(
            AdUnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.EMPTY_LIST;
        request.getUnitDistricts().forEach(d -> {
                    AdUnitDistrict district = new AdUnitDistrict(d.getUnitId(), d.getProvince(),
                            d.getCity());
                    unitDistrictRepository.insert(district);
                    ids.add(district.getId());
                }
        );
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(
            CreativeUnitRequest request) throws AdException {

        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());

        if (!(isRelatedUnitExist(unitIds) && isRelatedUnitExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.EMPTY_LIST;
        request.getUnitItems().forEach(i -> {
                    CreativeUnit creativeUnit = new CreativeUnit(i.getCreativeId(), i.getUnitId());
                    creativeUnitRepository.insert(creativeUnit);
                    ids.add(creativeUnit.getId());
                }
        );

        return new CreativeUnitResponse(ids);
    }

    @SuppressWarnings("all")
    private boolean isRelatedUnitExist(List<Long> unitIds) {

        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        Wrapper<AdUnit> wrapper = new EntityWrapper<>();
        wrapper.in("id", unitIds);
        return unitRepository.selectList(wrapper).size() ==
                new HashSet<>(unitIds).size();
    }

    @SuppressWarnings("all")
    private boolean isRelatedCreativeExist(List<Long> creativeIds) {

        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        Wrapper<Creative> wrapper = new EntityWrapper<>();
        wrapper.in("id", creativeIds);
        return creativeRepository.selectList(wrapper).size() ==
                new HashSet<>(creativeIds).size();
    }
}
