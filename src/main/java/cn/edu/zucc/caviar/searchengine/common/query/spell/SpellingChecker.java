package cn.edu.zucc.caviar.searchengine.common.query.spell;

import cn.edu.zucc.caviar.searchengine.common.utils.RedisUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellingChecker {
    private static RedisUtil redisUtil = new RedisUtil();


    public SpellingChecker(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    /***
     * 查询相同SoundexCode的字符串
     * @param token 要检查的内容
     * @param isPinyin 检查内容是否为拼音
     * @return
     */


    public Map<String, Double> checkSpell(String token,boolean isPinyin){


        List<String> similarSpellTokens = new ArrayList<String>();
        Map<String ,Double> checkMap = new HashMap<>();
        String pinyinString="";

        String soundexCode= "";
        String initial = "";

        if(isPinyin)
        {
            pinyinString = token;
            soundexCode = SoundexCoder.soudex(token);
            initial = token.substring(0,1);
        }
        else
        {
            List<Pinyin> pinyin = HanLP.convertToPinyinList(token);
            pinyinString = PinyinUtil.getPinyin(pinyin);
            initial = pinyin.get(0).getShengmu().toString();
            soundexCode = SoundexCoder.soundex(pinyin);
        }


        if(initial.equals("n")||initial.equals("l")||initial.equals("r")){
            similarSpellTokens.addAll(redisUtil.searchByScore("n",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("l",pinyinString,pinyinString));
        }
        else if(initial.equals("zh")||initial.equals("z")){
            similarSpellTokens.addAll(redisUtil.searchByScore("zh",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("z",pinyinString,pinyinString));
        }
        else if(initial.equals("j")||initial.equals("q")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }

        else if(initial.equals("s")||initial.equals("sh")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else if(initial.equals("c")||initial.equals("ch")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else if(initial.equals("g")||initial.equals("k")){
            similarSpellTokens.addAll(redisUtil.searchByScore("j",pinyinString,pinyinString));
            similarSpellTokens.addAll(redisUtil.searchByScore("q",pinyinString,pinyinString));
        }
        else
        {
            similarSpellTokens.addAll(redisUtil.searchByScore(initial,soundexCode,soundexCode));
        }
        for(String checkToken:similarSpellTokens){
            checkMap.put(checkToken,0.6);
        }

        return checkMap;
    }

    public static void main(String args[]){
//        List<String> rs = new SpellingChecker().checkSpell("tianpin");
//        for(String s :rs){
//            System.out.println(s);
//        }
    }
}
