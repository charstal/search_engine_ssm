package cn.edu.zucc.caviar.searchengine.core.pojo;

import java.util.List;
import java.util.Set;

public class Response {
    private Set<Document> documentSet;
    private Integer documentNumber;
    private List<String> spellCheckList;
    private Set<Document> recommendDocumentSet;
    private List<String> hotpotList;

    public Set<Document> getDocumentSet() {
        return documentSet;
    }

    public void setDocumentSet(Set<Document> documentSet) {
        this.documentSet = documentSet;
    }

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

    public List<String> getSpellCheckList() {
        return spellCheckList;
    }

    public void setSpellCheckList(List<String> spellCheckList) {
        this.spellCheckList = spellCheckList;
    }

    public Set<Document> getRecommendDocumentSet() {
        return recommendDocumentSet;
    }

    public void setRecommendDocumentSet(Set<Document> recommendDocumentSet) {
        this.recommendDocumentSet = recommendDocumentSet;
    }

    public List<String> getHotpotList() {
        return hotpotList;
    }

    public void setHotpotList(List<String> hotpotList) {
        this.hotpotList = hotpotList;
    }
}
