package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;

import java.util.List;

public interface SearchService {
    public List<Document> keywordSearch(String keyword);
}
