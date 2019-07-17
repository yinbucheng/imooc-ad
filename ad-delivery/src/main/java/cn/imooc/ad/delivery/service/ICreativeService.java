package cn.imooc.ad.delivery.service;

import cn.imooc.ad.delivery.vo.CreativeRequest;
import cn.imooc.ad.delivery.vo.CreativeResponse;


/**
 * Created by Qinyi.
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);
}
