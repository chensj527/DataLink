package com.ucar.datalink.flinker.plugin.writer.eswriter.client.rest.search.listener;

import com.ucar.datalink.flinker.plugin.writer.eswriter.client.rest.vo.search.SearchResultDetailVO;

import java.util.List;

/**
 * @author forest
 * @email wei.wang09@ucarinc.com
 * @create 2017-06-15 17:52
 */
public interface ScrollQueryListener {

    public void onQuery(List<SearchResultDetailVO> results);
}
