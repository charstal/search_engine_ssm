package cn.edu.zucc.caviar.searchengine.common.query.spell;

import cn.edu.zucc.caviar.searchengine.common.utils.RedisUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.util.ArrayList;
import java.util.List;

public class SpellingChecker {
    private static RedisUtil redisUtil = new RedisUtil();

    /***
     * 查询相同SoundexCode的字符串
     * @param token 要检查的内容
     * @return
     */
    public List<String> checkSpell(String token){
        List<Pinyin> pinyin = HanLP.convertToPinyinList(token);
        List<String> similarSpellTokens = new ArrayList<String>();
        String pinyinString = PinyinUtil.getPinyin(pinyin);
        String initial = pinyin.get(0).getShengmu().toString();
        String soundexCode = SoundexCoder.soundex(pinyin);

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

        return similarSpellTokens;
    }

    public static void main(String args[]){
        List<String> rs = new SpellingChecker().checkSpell("天平");
        for(String s :rs){
            System.out.println(s);
        }
    }
}
