package com.min.blog.controller;

import com.min.blog.model.BasicResponse;
import com.min.blog.model.Role;
import com.min.blog.model.User;
import com.min.blog.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    private final String BACK_SERVER_URI = "http://localhost:8080";			// local (back)
//	private final String BACK_SERVER_URI = "https://i3a308.p.ssafy.io";		// server (back)

    private final String FRONT_SERVER_URI = "http://localhost/agreement";		// local (front)
//	private final String FRONT_SERVER_URI = "https://i3a308.p.ssafy.io";	// server (front)

    /* 네이버 소셜 로그인 URI */
    /* Back */
    private final String naverRedirectBackURI = BACK_SERVER_URI + "/user/signin/naver/access";
    /* Front */
    private final String naverRedirectFrontURI = FRONT_SERVER_URI + "/";

    @PostMapping("/naverlogin")
    public Object naverlogin(@RequestBody Map token, HttpServletRequest request) throws UnsupportedEncodingException {
        BasicResponse response = new BasicResponse();

        String clientId = "RnP9opRdc0Dm21kuldCI";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "ieEWDBkbEG";//애플리케이션 클라이언트 시크릿값";
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
        String redirectURI = URLEncoder.encode(FRONT_SERVER_URI+"/agreement", "UTF-8");
        String apiURL;
        apiURL = "https://openapi.naver.com/v1/nid/me";
        apiURL += "?client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&redirect_uri=" + redirectURI;
//        apiURL += "&code=" + code;
//        apiURL += "&state=" + state;
        String access_token = (String)token.get("token");
        String refresh_token = "";

        User user=null;
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization","Bearer "+access_token);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.println("responseCode="+responseCode);

            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if(responseCode==200) {

                user=makeUser(res.toString());

            }
        } catch (Exception e) {

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        response.status=true;
        response.data="success";
        response.object=user;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public User makeUser(String res) throws ParseException {
        String nickname="";
        String email="";
        String name="";
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(res);
        JSONObject resObj = (JSONObject) obj.get("response");
        email=(String)resObj.get("email");
        name= URLDecoder.decode((String) resObj.get("name"));
        nickname= URLDecoder.decode((String) resObj.get("nickname"));

        Optional<User> ouser = userService.findByEmail(email);
        User user=null;
        if(ouser.isPresent()){
            user=ouser.get();
        }else{
            user = User.builder()
                    .name(name)
                    .role(Role.USER)
                    .nickname(nickname)
                    .email(email)
                    .build();
            userService.save(user);
        }
        return user;
    }



}
