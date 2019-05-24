package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.utils.HBaseTest;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.stereotype.Service;


@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    HBaseTest hBaseTest;

    public Document getNodeByNoteId(String noteId) {
        return hBaseTest.get(noteId);
    }
}
