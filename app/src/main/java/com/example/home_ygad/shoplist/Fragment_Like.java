package com.example.home_ygad.shoplist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.home_ygad.Item;
import com.example.home_ygad.R;
import com.example.home_ygad.adapterphone1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Like extends Fragment {

    private View view2;
    RecyclerView recyclerView1;
    ArrayList<Item> items = new ArrayList<>(); //혹시몰라 아이템(DB연동데이터)용 데이터 넣을 배열 새로만듦


    adapterphone1 adapter;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cafe);
//
//        recyclerView1 = findViewById(R.id.recyclerview_cafe);
//        adapter = new adapterphone1(this, items);
//        recyclerView1.setAdapter(adapter); //        이 문장에서 런타임오류가 나면 어댑터에 문제가있다는뜻
//        recyclerView1.setLayoutManager(layoutManager2);
//



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.fragment_like,container, false);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//
            //서버주소

        recyclerView1 = view2.findViewById(R.id.recyclerview_like);
        adapter = new adapterphone1(getActivity().getApplicationContext(), items);
       recyclerView1.setAdapter(adapter); //        이 문장에서 런타임오류가 나면 어댑터에 문제가있다는뜻
        recyclerView1.setLayoutManager(layoutManager2);
            String serverUrl = "http://heremong.dothome.co.kr/Liked.php";
            //         PlaceList3.php 를 사용, Place_id, P_name, P_address, P_image,P_image2 from Place

            //결과를 JsonArray 받을 것이므로..
            //StringRequest가 아니라..
            //JsonArrayRequest를 이용할 것임
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
                //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
                @Override
                public void onResponse(JSONArray response) {
                    // Toast.makeText(MainActivity_2.this, response.toString(), Toast.LENGTH_SHORT).show();


                    //      파라미터로 응답받은 결과 JsonArray를 분석

                    items.clear();
                    adapter.notifyDataSetChanged();//      일단 따라서 넣어보자


                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            int Place_id = Integer.parseInt(jsonObject.getString("Place_id")); //no가 문자열이라서 바꿔야함.
                            String P_name = jsonObject.getString("P_name");
                            String P_address = jsonObject.getString("P_address");
                            String P_image = jsonObject.getString("P_image");
                            //         어...그러니까 이 clikload 안의 onResponce 함수에서 json으로 어케한 정보들을 여기서 사용가능한 string들로 바꿔준다?
                            //         근데 이미지는 어케 넣어지게 되는거야

                            //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                            //P_image = "http://heremong.dothome.co.kr/"+P_image;
                            //String P_image = "http://localhost/univ/displayFile?fileName=6d4408e3-9f4a-4ce8-a224-8b101921e92c.jpg";

                            items.add(0, new Item(Place_id, P_name, P_address, P_image)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                            adapter.notifyItemInserted(0);


//                        phoneRecycler3.setAdapter(adapter2);//테스트용 수평리사이클러어댑터
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Toast.makeText(MainActivity_2.this, "ERROR", Toast.LENGTH_SHORT).show(); //     에러시 토스트 메세지
                }
            });

            //실제 요청 작업을 수행해주는 요청큐 객체 생성
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

            //요청큐에 요청 객체 생성
            requestQueue.add(jsonArrayRequest);



        return view2;
    }


}