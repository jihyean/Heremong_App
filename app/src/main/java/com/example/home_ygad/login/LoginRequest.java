package com.example.home_ygad.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


//로그인 요청을 함
public class LoginRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://heremong.dothome.co.kr/Login.php";
    private Map<String, String> map;


    public LoginRequest(String ID, String PW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ID",ID);
        map.put("PW", PW);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}