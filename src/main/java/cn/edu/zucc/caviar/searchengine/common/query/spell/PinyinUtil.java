package cn.edu.zucc.caviar.searchengine.common.query.spell;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.util.List;

public class PinyinUtil {
    /***
     * 获取词拼音
     * @param pinyinList
     * @return
     */
    public static String getPinyin(List<Pinyin > pinyinList){
        StringBuffer pinyinString = new StringBuffer();
        for(Pinyin pinyin:pinyinList){
            pinyinString.append(pinyin.getPinyinWithoutTone());
        }
        return  pinyinString.toString();
    }
    public static void main(String args[]){
        List<Pinyin> pinyinList = HanLP.convertToPinyinList("芋头");

            System.out.println(SoundexCoder.soundex(pinyinList));

    }
}
