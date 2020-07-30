package com.shi.client.authcode.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * OrderController
 *
 * @author fengzheng
 * @date 2019/10/11
 */

@Controller
public class CodeClientController {

    /**
     * 用来展示index.html 模板
     * @return
     */
    @GetMapping(value = "index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "login")
    public String test(){
        return "index2";
    }

//    @GetMapping(value = "login")
//    public Object login(String code,Model model) {
//        String tokenUrl = "http://localhost/oauth/token";
//        OkHttpClient httpClient = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("grant_type", "authorization_code")
//                .add("client", "code-client")
//                .add("redirect_uri","http://localhost:9001/login")
//                .add("code", code)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(tokenUrl)
//                .post(body)
//                .addHeader("Authorization", "Basic Y29kZS1jbGllbnQ6Y29kZS1zZWNyZXQtODg4OA==")
//                .build();
//        try {
//            Response response = httpClient.newCall(request).execute();
//            String result = response.body().string();
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map tokenMap = objectMapper.readValue(result,Map.class);
//            String accessToken = tokenMap.get("access_token").toString();
////            Claims claims = Jwts.parser()
////                    .setSigningKey("dev".getBytes(StandardCharsets.UTF_8))
////                    .parseClaimsJws(accessToken)
////                    .getBody();
//
////            String userName = claims.get("user_name").toString();
//            model.addAttribute("username", "userName");
//            model.addAttribute("accessToken", result);
//            return "index";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    @org.springframework.web.bind.annotation.ResponseBody
    @GetMapping(value = "get")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Object get(Authentication authentication) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = details.getTokenValue();
        return token;
    }

}
