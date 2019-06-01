package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.Response;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchService {
    public Response keywordSearch(String keyword, Integer page);
    public long documentPageCount(String keyword);
    public Set<Document> documentsInPage(long currentPage,long count);
    public Map<String, Double> checkSpell(String token, boolean isPinyin);
}
