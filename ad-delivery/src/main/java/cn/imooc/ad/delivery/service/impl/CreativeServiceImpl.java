package cn.imooc.ad.delivery.service.impl;

import cn.imooc.ad.delivery.dao.CreativeMapper;
import cn.imooc.ad.delivery.entity.Creative;
import cn.imooc.ad.delivery.service.ICreativeService;
import cn.imooc.ad.delivery.vo.CreativeRequest;
import cn.imooc.ad.delivery.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Qinyi.
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    @Autowired
    private  CreativeMapper creativeRepository;


    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = request.convertToEntity();
         creativeRepository.insert(creative);

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
