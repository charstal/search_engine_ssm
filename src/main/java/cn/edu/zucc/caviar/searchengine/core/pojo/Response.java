package cn.edu.zucc.caviar.searchengine.core.pojo;

import java.util.Set;

public class Response {
    private Set<Document> documentSet;
    private Integer documentNumber;

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
}
