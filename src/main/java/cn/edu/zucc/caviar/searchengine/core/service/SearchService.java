package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;

import java.util.List;
import java.util.Map;

public interface SearchService {
    public List<Document> keywordSearch(String keyword);
    public Map<String, Double> checkSpell(String token, boolean isPinyin);
}
