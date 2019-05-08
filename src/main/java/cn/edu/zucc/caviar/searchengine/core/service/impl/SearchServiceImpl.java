package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.utils.QueryUtil;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchServiceImpl implements SearchService {

//    @Autowired
//    QueryUtil queryUtil;


    @Override
    public List<Document> keywordSearch(String keyword) {
        return null;
    }
}
