package cn.edu.zucc.caviar.searchengine.common.query.spell;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoundexCoder {
    /***
     *
     */
    public static Map<String,Integer> Initials = new HashMap<String, Integer>();
    static {
        Initials.put("b",1);Initials.put("p",1);Initials.put("m",3);Initials.put("f",1);
        Initials.put("d",5);Initials.put("t",5);Initials.put("n",7);Initials.put("l",7);
        Initials.put("g",8);Initials.put("k",8);Initials.put("h",10);Initials.put("j",11);
        Initials.put("q",11);Initials.put("x",13);Initials.put("zh",14);Initials.put("ch",15);
        Initials.put("sh",16);Initials.put("r",7);Initials.put("z",14);Initials.put("c",15);
        Initials.put("s",16);Initials.put("y",18);Initials.put("w",19);
    }


    public static Map<String,Integer> Finals= new HashMap<String, Integer>();;
    static {
        Finals.put("a",1);Finals.put("o",2);Finals.put("e",3);Finals.put("i",4);
        Finals.put("u",5);Finals.put("v",6);Finals.put("ai",7);Finals.put("ei",7);
        Finals.put("ui",8);Finals.put("ao",9);Finals.put("ou",10);Finals.put("iu",11);
        Finals.put("ie",12);Finals.put("ve",13);Finals.put("er",14);Finals.put("an",15);
        Finals.put("en",16);Finals.put("in",17);Finals.put("un",18);Finals.put("ven",19);
        Finals.put("ang",15);Finals.put("eng",16);Finals.put("ing",17);Finals.put("ong",20);
    }

    /***
     *
     * @param pinyinList
     * @return
     */
    public static String soundex(List<Pinyin > pinyinList) {

        StringBuffer soundexCode = new StringBuffer();

        for(Pinyin pinyin:pinyinList){
            soundexCode.append(Initials.get(pinyin.getShengmu().toString())==null?"":
                    Initials.get(pinyin.getShengmu().toString())  );
            soundexCode.append(Finals.get(pinyin.getYunmu().toString())==null?
                    Finals.get(pinyin.getYunmu().toString().substring(1)):             //包含辅音
                    Finals.get(pinyin.getYunmu().toString())      );
        }
        return soundexCode.toString();
    }

    public static String soudex(String pinyin){
        String pattern = "([bpmfdtnlqkhjqxzcsyw]|zh|ch|sh|)?[uiv]?(ai|ei|ui|ao|ou|iu|ie|ve|er|an|en|in|un|ven|ang|eng|ing|ong|[aoeiuv])";

        StringBuffer soundexCode = new StringBuffer();
        Pattern p =  Pattern.compile(pattern);
        Matcher matcher = p.matcher(pinyin);
        while(matcher.find()){
            soundexCode.append(Initials.get(matcher.group(1))==null?"":Initials.get(matcher.group(1)));
            soundexCode.append(Finals.get(matcher.group(2)));

        }
        return soundexCode.toString();
    }



    public static void main(String args[]){
//        System.out.println(SoundexCoder.soundex("没组"));
//        System.out.println(SoundexCoder.soundex("魅族"));
//        System.out.println(SoundexCoder.soundex("买组"));
        System.out.println(SoundexCoder.soudex("xihu"));
    }
}
