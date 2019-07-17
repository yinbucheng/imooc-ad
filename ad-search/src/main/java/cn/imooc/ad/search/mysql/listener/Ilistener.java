package cn.imooc.ad.search.mysql.listener;

import cn.imooc.ad.search.mysql.dto.BinlogRowData;

/**
 * Created by Qinyi.
 */
public interface Ilistener {

    void register();

    void onEvent(BinlogRowData eventData);
}
