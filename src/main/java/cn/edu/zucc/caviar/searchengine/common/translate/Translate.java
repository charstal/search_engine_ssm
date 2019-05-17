package cn.edu.zucc.caviar.searchengine.common.translate;

public class Translate {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20190509000295751";
    private static final String SECURITY_KEY = "0LXk5vFBK8YdgIo11F3N";
    public static String TranslateToZH(String query) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String answer=api.getTransResult(query, "auto", "zh");
        return unicodeToCn(answer.substring(answer.indexOf("dst")+6,answer.lastIndexOf("\"")));
    }
    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

}
