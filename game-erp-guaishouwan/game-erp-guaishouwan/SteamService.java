package com.ruoyi.system.steam;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.mapper.ApiReqRecMapper;
import com.ruoyi.system.V2.inter.service.IApiReqRecService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.ApiReqRec;
import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.model.domain.UserKey;
import com.ruoyi.system.model.service.IUserKeyService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * api请求记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-27
 */
@Service
public class SteamService {

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    static String  pxip = "pxip";

    public JSONObject getStr(String url){

        String sessiodId = StringUtils.generate(24);

        Map<String, String> stringStringMap = UrlParamParser.parseParams(url);
        String stoken = MapUtils.getString(stringStringMap, "stoken");

        List<SysDictData> sysDictData = sysDictTypeService.selectDictDataByType(pxip);

        String remark = sysDictData.get(0).getRemark();

//        String prxUrl = "http://proxy.siyetian.com/apis_get.html?token=AesJWLNpWU45kaJdXTq1EeNpWS65ERNhnTn1STqFUeNpXQz0kaZp3TEFVeNRVRz0ERRlnTUlVe.wN0QjNzQjM0cTM&limit=1&type=0&time=&split=1&split_text=";
        String s1 = HttpUtil.get(remark);
        System.out.println("代理IP：" + s1);
        String[] split = s1.split(":");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(split[0], Integer.parseInt(split[1])));


//        String stoken = "SGEVW%2BUHn1x7b23M655BZ7mc%2BpYqxPVWR7q0JIo%2F%2FrZ%2BLVK5hekzOWVOGlGAUysKK5VoP3UFe1wVDDWvSwK5mWBo9Fq47H%2FlXd1rtSUidiM%3D";

        String cookie = "timezoneOffset=28800,0; sessionid="+sessiodId+"; steamCountry=TW%7C73b08ff1c205027c6c0664956b1833b0; ak_bmsc=DF4C26E483640DAB2F557418DBF51FDE~000000000000000000000000000000~YAAQCQk+F9okkq6VAQAA+DEvsRu0SHHPgGnJ3YvoRdBKrW1Wf8B15XuyBFrKCdgxeJQ8lv0QFtKXdLztNr+BhUfURSW7joxtaHk0ECXsNWiBq7URazpRpeFAskLgXXBu43rcSbk4lPkGZmrF5SNt47YdxqdhbVSvs7fWZPZmx4bUc7EviRegWXTQawUkvXjtZ+Eqe5pn9wvsdu0msIcDbSTorogsbXWlSkR7UANUVOIN1zwpW0NEeKhnwL6Cg0/2ENQCKEOt1D2kQvAQe/AHDXTi7NrHk4NX+HlHZ8hGQ9GPDAyAT8v21TCb2foVYBRNxeMmTYju/1tJsDooHZuhzLrzETUUM47p+GrB5IPH0D423ZZPqY78JDdQjGAwwwevxRU8c/Cr; lock_email_data="+URLEncoder.encode(stoken)+"; bm_sv=C09357A4543956384B01C7C40F6FED96~YAAQDFFFywzija2VAQAARUE3sRuvii/xoQPiK3ngnTvSt4xMr2/KzXnGc6YZtunS7hySZkigGLScGCLzZFf2hIhMZqGlEp4MAuZ0GO9D3Yi9vzIgcloSs7gqUn/duJZVdhzRDxKgEM0nRTZsIBtC0KizXx0fPex9I0sFhWNqj9+JCGHorczobdc8pIa2i5WiMdKUmyfhBAGzd8eFjJDtjWbxUr6zYUeKpmk6PJlu1rTnd7iby1XKtppCEXxNuZxbSJHOxtt5~1";

//        String cookie = "timezoneOffset=28800,0; steamCountry=CN%7C53ef67994f61829ed81d2d203150b8c1; sessionid=" + sessiodId +"; lock_email_data=" + URLEncoder.encode(stoken) +"; ak_bmsc=340BDC80D22CC9A33DAD45C5A6923F46~000000000000000000000000000000~YAAQI04SAmhjT6SVAQAAKoRUrBv7z/B8ITjgru5LW/kQp9swhmCkNJwOgnJZ03XoLjkDvyL/MeDJylOkw6sCIa9E0rI01diIo+1EhU+gPElwOLGW1ErKrdy3NERfGIUmfPRwGHZ+T3XAKsf5b3Q2xTM8y6swDtZer2gpz2+7GgSs5Rv1u6ZiUd9L5MKshn7nto0Iots0rIsEilkmzDCPoa9vzUasUj2yATC8/t3OTnxfOr8X7auyZF6a2FbveCacwuyWqtR4KYCtVL0suOooh8rMcVnDlmDbEpXJJdHShzmg/h+nZKAnxE7TTKefCPYUsvlE7g1fI8Opzu8BThumnjZ7P3vldnXgiuCoWe0wjMe4152yw8bFCysFZxFq1+AYmxqux0VT; bm_sv=58451654B598A86C84873469D75C9E61~YAAQD1FFy76P/aOVAQAA+jtXrBulUweH/JRAZp4GaWIeiUVbMe/HrLwYDINn1vT6q5H1+WlHPidhdjCTNiZIRDsJN8soHYO24Uv9NrzUKCD7yr6GWpBln5bbBatf5WiVz3rI0EtkPA/2UNWJS29txPwSEKEFnCt7qJIx+7nUKjspjzdmKGo7V4PJ/p9DSEnC9q4CvrWpPQIyEw4+TwFnjjtp4cYkT2Z4K6VC343HH+iuQlO2yshLiYoh0JOQbVIl5wSw15K4~1";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .proxy(proxy)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("Sec-Fetch-Dest", "document")
                .addHeader("Sec-Fetch-Mode", "navigate")
                .addHeader("Sec-Fetch-Site", "none")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //下一步调用HelpUnauthorizedLoginCanRecover 获取 s 参数
            JSONObject s = HelpUnauthorizedLoginCanRecover(sessiodId, stoken,proxy);
            return s;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private JSONObject HelpUnauthorizedLoginCanRecover(String sessionId,String stoken,Proxy proxy) throws IOException {

        String cookie = "timezoneOffset=28800,0; sessionid="+sessionId+"; steamCountry=TW%7C73b08ff1c205027c6c0664956b1833b0; lock_email_data="+URLEncoder.encode(stoken)+"; ak_bmsc=C011C0A5D92CA334C6A993B3C354A80E~000000000000000000000000000000~YAAQsj7cPfnej6qVAQAAnpNrrRuwjopJu+wIitsVg7w9z0eOpmAgdSVK9tqW5nwUpVXDK810ptbNK7IwJdTTwi186PTCWwV3mRqMqpx2Mym2D2Lv/22nDxc+KG7tlbqff3tMqakXwnjO3MD+86rt/mJ4Av3TF/Cj1auUfawyCXgR7Mj+y0qXN5z73A2rjJMjeTfT8rj79veur8aS5edsWRKZTPCrQ6qCMKbvGgV5BbrLL7NDVEgaJ3LssntNNK1/N65nMxh56eFhtZR/KNtETQy/S0xVMa9h9FeektVFUnv7E6NxKao2tByU5mkMCweFf9jtHuHbdhNC2zB/GLG2ERmzKmxkzBH3V7kAubJx2Ta/8734TD1XCT30+bnSrjNfP5PbJ5Y=";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .proxy(proxy)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://help.steampowered.com/zh-cn/wizard/HelpUnauthorizedLoginCanRecover?sessionid=" + sessionId +"&wizard_ajax=1&gamepad=0")
                .method("GET", null)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "https://help.steampowered.com/zh-cn/wizard/HelpUnauthorizedLoginCanRecover")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();

            String string = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(string);
            String html = jsonObject.getString("html");
            Element body1 = Jsoup.parse(html).body();
            Elements href = body1.select("a");
            for (Element element : href) {
                String s = element.absUrl("href");
                if (s.contains("https://help.steampowered.com/zh-cn/wizard/HelpWithLoginInfoSendCode")){

                    Map<String, String> stringStringMap = UrlParamParser.parseParams(s);
                    String s1 = MapUtils.getString(stringStringMap, "s");
                    return HelpWithLoginInfoSendCode(sessionId,stoken,s1,proxy);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private JSONObject HelpWithLoginInfoSendCode(String sessionId,String stoken,String s,Proxy proxy) {

        JSONObject result = new JSONObject();

        String cookie = "timezoneOffset=28800,0; sessionid="+sessionId+"; lock_email_data="+URLEncoder.encode(stoken) +"; ak_bmsc=340BDC80D22CC9A33DAD45C5A6923F46~000000000000000000000000000000~YAAQI04SAmhjT6SVAQAAKoRUrBv7z/B8ITjgru5LW/kQp9swhmCkNJwOgnJZ03XoLjkDvyL/MeDJylOkw6sCIa9E0rI01diIo+1EhU+gPElwOLGW1ErKrdy3NERfGIUmfPRwGHZ+T3XAKsf5b3Q2xTM8y6swDtZer2gpz2+7GgSs5Rv1u6ZiUd9L5MKshn7nto0Iots0rIsEilkmzDCPoa9vzUasUj2yATC8/t3OTnxfOr8X7auyZF6a2FbveCacwuyWqtR4KYCtVL0suOooh8rMcVnDlmDbEpXJJdHShzmg/h+nZKAnxE7TTKefCPYUsvlE7g1fI8Opzu8BThumnjZ7P3vldnXgiuCoWe0wjMe4152yw8bFCysFZxFq1+AYmxqux0VT; steamCountry=TW%7C73b08ff1c205027c6c0664956b1833b0; bm_sv=58451654B598A86C84873469D75C9E61~YAAQD1FFyw+Q/aOVAQAAw1pXrBt0wK5a9W/3Dy+mBWrVTaoCy3M1LeJ0ojpRyoknlxMLTdi+fEX3TpcNR+laC8TaHLHbYO6ZbxKVoNi5a1SZ4zcYccVy4GqtJfvr4mfS85sP+aMTP0UedJH7c9rTCHCqWGN05y59d+HgPwbusafX3J+Fs1JrN39jx4joJzkCfGp5w1HbCb/OMN4ITCO0PNLzJk27D3nzpuTwhA7Pg20hKjKWDggYux9syLUozoZvdhFNx1Ny~1";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .proxy(proxy)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://help.steampowered.com/zh-cn/wizard/HelpWithLoginInfoSendCode?s=" + s +"&sessionid=" + sessionId +"&wizard_ajax=1&gamepad=0&lost=8")
                .method("GET", null)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "https://help.steampowered.com/zh-cn/wizard/HelpWithLoginInfoSendCode?s="+s)
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(string);
            String html = jsonObject.getString("html");
            Element body = Jsoup.parse(html).body();
            Elements span = body.select("span");
            String phone = "";
            for (Element element : span) {
                String text = element.text();
                if (text.contains("通过短信发送帐户验证码到尾")){
                    //通过短信发送帐户验证码到尾号为“73”的手机号码
                    phone = text.replace("通过短信发送帐户验证码到尾号为","")
                            .replace("”的手机号码","");
                }
            }
            String email = getEmail(sessionId, stoken, s,proxy);

            result.put("phone", phone);
            result.put("email", email);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


   private String getEmail(String sessionId,String stoken,String s,Proxy proxy){


       String cookie = "timezoneOffset=28800,0; sessionid="+sessionId+"; steamCountry=TW%7C73b08ff1c205027c6c0664956b1833b0; lock_email_data=" + URLEncoder.encode(stoken) + "; ak_bmsc=C011C0A5D92CA334C6A993B3C354A80E~000000000000000000000000000000~YAAQsj7cPfnej6qVAQAAnpNrrRuwjopJu+wIitsVg7w9z0eOpmAgdSVK9tqW5nwUpVXDK810ptbNK7IwJdTTwi186PTCWwV3mRqMqpx2Mym2D2Lv/22nDxc+KG7tlbqff3tMqakXwnjO3MD+86rt/mJ4Av3TF/Cj1auUfawyCXgR7Mj+y0qXN5z73A2rjJMjeTfT8rj79veur8aS5edsWRKZTPCrQ6qCMKbvGgV5BbrLL7NDVEgaJ3LssntNNK1/N65nMxh56eFhtZR/KNtETQy/S0xVMa9h9FeektVFUnv7E6NxKao2tByU5mkMCweFf9jtHuHbdhNC2zB/GLG2ERmzKmxkzBH3V7kAubJx2Ta/8734TD1XCT30+bnSrjNfP5PbJ5Y=; bm_sv=08426937EDBBCDC366E95F30711948D6~YAAQ1j7cPVE1/KWVAQAANLiZrRubqnpGeHFB9pM0Wa/LjI5cAGNBcm6A3x0k+LszlxWhv7mvMrs6y25NuDfdEEYA3Cfd/Mf+NC8fn4hgxlN5DWCBfHYY8gmXCiKCctLuYOeLIfr1WPDLlbOxx4L4aP0RKhm02ajHJAoytjhDZUKQPM2pLzRASau3b8VJJ+OtidFB1bfHxnWoqEVE2uGfwirx8h8kCfzwc2/3ZJbDm2o0visMaTrjzC+u0PiRlRGBPehlxnFw~1";

       OkHttpClient client = new OkHttpClient().newBuilder()
               .proxy(proxy)
               .build();
       MediaType mediaType = MediaType.parse("text/plain");
       RequestBody body = RequestBody.create(mediaType, "");
       Request request = new Request.Builder()
               .url("https://help.steampowered.com/zh-cn/wizard/HelpWithLoginInfoSendCode/?s="+s+"&lost=12")
               .method("GET", null)
               .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
               .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
               .addHeader("Connection", "keep-alive")
               .addHeader("Referer", "https://help.steampowered.com/zh-cn/wizard/HelpWithLoginInfoSendCode?s="+s)
               .addHeader("Sec-Fetch-Dest", "document")
               .addHeader("Sec-Fetch-Mode", "navigate")
               .addHeader("Sec-Fetch-Site", "same-origin")
               .addHeader("Sec-Fetch-User", "?1")
               .addHeader("Upgrade-Insecure-Requests", "1")
               .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
               .addHeader("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134\"")
               .addHeader("sec-ch-ua-mobile", "?0")
               .addHeader("sec-ch-ua-platform", "\"Windows\"")
               .addHeader("Cookie", cookie)
               .build();
       try {
           String email = "";
           Response response = client.newCall(request).execute();
           String string = response.body().string();
           Element body1 = Jsoup.parse(string).body();
           Elements elementsByClass = body1.getElementsByClass("help_wizard_button help_wizard_arrow_right");
           for (Element byClass : elementsByClass) {
               String text = byClass.text();
               if (text.contains("通过电子邮件发送帐户验证码至")){
                 email = text.replace("通过电子邮件发送帐户验证码至","").replace(" ","");
               }
           }
           return email;
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

}
