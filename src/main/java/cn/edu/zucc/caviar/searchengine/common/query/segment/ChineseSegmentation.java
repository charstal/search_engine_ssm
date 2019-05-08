package cn.edu.zucc.caviar.searchengine.common.query.segment;

import com.hankcs.hanlp.HanLP;
import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.List;

public class ChineseSegmentation {
    public static List<String> keywordsSegmentaion(String sentence){
        List<String> keywordList = HanLP.extractKeyword(sentence, 3);
        return  keywordList;
    }
    public static void main(String args[]) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =
                new String[] {"xiangyao要问问你敢不敢","西湖醋yu"};
        for (String sentence : sentences) {
            List<String> keywordList = HanLP.extractKeyword(sentence, 3);
            System.out.println(keywordList);
        }
    }
}
