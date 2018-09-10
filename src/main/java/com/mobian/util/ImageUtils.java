package com.mobian.util;

import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.thirdpart.oss.OSSUtil;
import com.mobian.thirdpart.wx.DownloadMediaUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageUtils {


    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag, String realPath) {
        try {
            String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
            String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
            Pattern patternForTag = Pattern.compile (regxpForTag, Pattern.CASE_INSENSITIVE);
            Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib, Pattern.CASE_INSENSITIVE);
            Matcher matcherForTag = patternForTag.matcher(str);
            StringBuffer sb = new StringBuffer();
            boolean result = matcherForTag.find();
            while (result) {
                StringBuffer sbreplace = new StringBuffer("<" + tag + " ");
                Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
                if (matcherForAttrib.find()) {
                    String attributeStr = matcherForAttrib.group(1);
                    if (attributeStr.indexOf(Application.getString("SV200")) == -1 && attributeStr.indexOf(OSSUtil.cdnUrl) == -1) {
                        if(F.empty(realPath)) {
                            matcherForAttrib.appendReplacement(sbreplace, startTag + PathUtil.getPicPath(attributeStr) + endTag);
                        } else {
                            try {
                                //String fileExt = attributeStr.substring(attributeStr.lastIndexOf(".") + 1).toLowerCase();
                                URL url = new URL(attributeStr);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);
                                conn.setDoOutput(true);
                                conn.setUseCaches(false);
                                conn.setRequestMethod("GET");
                                conn.connect();
                                String fileExt = DownloadMediaUtil.getFileExtName(conn.getHeaderField("Content-Type"));
                                String path = OSSUtil.putInputStream(OSSUtil.bucketName, conn.getInputStream(), getFilePath(fileExt));
//                                path = pressImage(path, realPath);
                                conn.disconnect();
                                matcherForAttrib.appendReplacement(sbreplace, startTag + path + endTag);
                            } catch (Exception e) {
                                String error = String.format("replaceHtmlTag失败：%s", e);
                                System.out.println(error);
                            }
                        }
                    }
                }
                matcherForAttrib.appendTail(sbreplace);
                matcherForTag.appendReplacement(sb, sbreplace.toString());
                result = matcherForTag.find();
            }
            matcherForTag.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            String error = String.format("replaceHtmlTag失败：%s", e);
            System.out.println(error);
        }
        return str;
    }

    public static String getFilePath(String fileExt) {
        String savePath = "attached/image/";
        SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthDf = new SimpleDateFormat("MM");
        SimpleDateFormat dateDf = new SimpleDateFormat("dd");
        Date date = new Date();
        String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
        savePath += ymd;
        String newFileName = com.mobian.absx.UUID.uuid() + "." + fileExt;
        return savePath + newFileName;
    }

}
