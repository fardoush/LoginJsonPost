package com.example.loginjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText phn,pass;
    Button button;
    TextView textView1,textView2;
    String mobile_no,password,users_name_en,users_name_bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phn = findViewById(R.id.emailId);
        pass = findViewById(R.id.passId);
        button =  findViewById(R.id.signInId);
        textView1= findViewById(R.id.text1Id);
        textView2 =  findViewById(R.id.text2Id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_no= phn.getText().toString();
                password = pass.getText().toString();
                prev();
            }
        });

    }

    private void prev() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://demo.olivineltd.com/primary_attendance/api/admin/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Invalid Creatation")){
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                }
                else{

                    try {
                        JSONObject jsonObject= new JSONObject(response);

                        users_name_en = jsonObject.getString("users_name_en");
                        users_name_bn = jsonObject.getString("users_name_bn");

                        Toast.makeText(MainActivity.this, " Success ", Toast.LENGTH_SHORT).show();
                        textView1.setText(users_name_en);
                        textView2.setText(users_name_bn);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


//                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("mobile_no",mobile_no);
                params.put("password",password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
}