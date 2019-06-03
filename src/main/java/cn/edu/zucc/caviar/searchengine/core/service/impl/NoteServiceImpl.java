package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.pagination.Page;
import cn.edu.zucc.caviar.searchengine.common.utils.HBaseTest;
import cn.edu.zucc.caviar.searchengine.common.utils.RedisTest;
import cn.edu.zucc.caviar.searchengine.common.utils.digest.TextRankSentence;
import cn.edu.zucc.caviar.searchengine.core.dao.NoteDao;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.pojo.UserDocumentRecord;
import cn.edu.zucc.caviar.searchengine.core.service.NoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    HBaseTest hBaseTest;

    @Autowired
    NoteDao noteDao;

    @Autowired
    RedisTest redisTest;

    public Document getNodeByNoteId(String noteId) {
        return hBaseTest.get(noteId);
    }

    @Override
    public Page<UserDocumentRecord> findNoteList(Integer userId, Integer page, Integer rows) {

        UserDocumentRecord userDocumentRecord = new UserDocumentRecord();
        userDocumentRecord.setUserId(userId);

        // 当前页
        userDocumentRecord.setStart((page-1) * rows) ;
        // 每页数
        userDocumentRecord.setRows(rows);
        // 查询客户列表
        List<UserDocumentRecord> notes = noteDao.selectNoteList(userDocumentRecord);

        for(int i = 0; i < notes.size(); ++i) {
            String noteId = notes.get(i).getNoteId();
            Document document = hBaseTest.get(noteId);
            notes.get(i).setTitle(TextRankSentence.getSummary(document.getTitle(), 15));
            notes.get(i).setDescribe(TextRankSentence.getSummary(document.getContent(), 100));
        }
        // 查询客户列表总记录数
        Integer count = noteDao.selectNoteListCount(userDocumentRecord);
        // 创建Page返回对象
        Page<UserDocumentRecord> result = new Page<>();
        result.setPage(page);
        result.setRows(notes);
        result.setSize(rows);
        result.setTotal(count);

        return result;

    }

    @Override
    public boolean insertCollectionNoteForUser(Integer userId, String noteId) {
        try {
            Timestamp date = new Timestamp(System.currentTimeMillis());
            int row = noteDao.insertToCollection(userId, noteId, date);
            if (row <= 0)
                return false;
            else
                return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean insertLikeNoteForUser(Integer userId, String noteId) {
        try {
            Timestamp date = new Timestamp(System.currentTimeMillis());
            int row = noteDao.insertToCollection(userId, noteId, date);

            if (row <= 0)
                return false;
            else
                return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteCollectionNoteForUser(Integer userId, String noteId) {
        try {
            Timestamp date = new Timestamp(System.currentTimeMillis());
            int row = noteDao.deleteToCollection(userId, noteId);
            if (row <= 0)
                return false;
            else
                return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteLikeNoteForUser(Integer userId, String noteId) {
        try {
            Timestamp date = new Timestamp(System.currentTimeMillis());
            int row = noteDao.deleteToLike(userId, noteId);
            if (row <= 0)
                return false;
            else
                return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Set<Document> similarNoteRecommend(String docId) {
        Set<String> docIdSet = redisTest.searchSimilarDocId(docId, 0, 1);

        Set<Document> documentSet = new HashSet<>();

        for(String a: docIdSet) {
            documentSet.add(hBaseTest.get(a));
        }

        return documentSet;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        NoteService noteService = applicationContext.getBean(NoteService.class);

        Set<Document> documentSet = noteService.similarNoteRecommend("5ca23d20000000000e02fc9a");

        for(Document a: documentSet) {
            System.out.println(a.getTitle());
        }
    }

}
