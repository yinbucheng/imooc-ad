package cn.imooc.ad.search.sender;

import cn.imooc.ad.search.mysql.dto.MySqlRowData;

/**
 * Created by Qinyi.
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
