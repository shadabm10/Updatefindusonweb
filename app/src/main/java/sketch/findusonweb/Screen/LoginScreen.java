package sketch.findusonweb.Screen;

/**
 * Created by developer on 7/5/18.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.content.pm.Signature;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
        import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.AppController;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class LoginScreen extends AppCompatActivity
         {

    String TAG = "login";
    GlobalClass globalClass;
    ProgressDialog pd;
    Shared_Preference prefrence;
    TextView login_tv, signup_tv,skip;
    EditText email_edt, password_edt;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        email=findViewById(R.id.email);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(LoginScreen.this);
        prefrence.loadPrefrence();
        skip=findViewById(R.id.skip);
        pd = new ProgressDialog(LoginScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toasty.info(LoginScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
      signup_tv=findViewById(R.id.register);
      signup_tv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),Register.class));
          }
      });
      email_edt=findViewById(R.id.email);
      password_edt=findViewById(R.id.password);
      login_tv=findViewById(R.id.login_tv);
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));


            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip=new Intent(getApplicationContext(),HomeScreen.class);
                startActivity(skip);
            }
        });
        login_tv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String email_new = email_edt.getText().toString().trim();
                String password_new = password_edt.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!email_edt.getText().toString().isEmpty()) {

                            if (!password_edt.getText().toString().isEmpty()) {
                                checkLogin(email_new,password_new,globalClass.view1);
                            } else {
                                Toasty.warning(LoginScreen.this, getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT, true).show();
                            }

                    } else {
                        Toasty.warning(LoginScreen.this, getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(LoginScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                }
            }
        });


    }

             private void checkLogin(final String username, final String password,final String view) {
                 // Tag used to cancel the request
                 String tag_string_req = "req_login";

                 pd.show();

                 StringRequest strReq = new StringRequest(Request.Method.POST,
                         AppConfig.URL_DEV, new Response.Listener<String>() {

                     @Override
                     public void onResponse(String response) {
                         Log.d(TAG, "Login Response: " + response.toString());

                       pd.dismiss();

                         Gson gson = new Gson();

                         try
                         {


                             JsonObject jobj = gson.fromJson(response, JsonObject.class);
                             String status = jobj.get("status").getAsString().replaceAll("\"", "");
                             String message = jobj.get("message").getAsString().replaceAll("\"", "");

                             Log.d(TAG, "Message: "+message);

                             if(status.equals("1") ) {
                                 JsonObject data=jobj.getAsJsonObject("data");

                               //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                                 String user_id = data.get("user_id").getAsString().replaceAll("\"", "");
                                 String login = data.get("login").getAsString().replaceAll("\"", "");
                                 String user_first_name = data.get("user_first_name").getAsString().replaceAll("\"", "");
                                 String user_last_name = data.get("user_last_name").getAsString().replaceAll("\"", "");
                                 String user_organization = data.get("user_organization").getAsString().replaceAll("\"", "");
                                 String user_phone = data.get("user_phone").getAsString().replaceAll("\"", "");
                                 String email = data.get("user_email").getAsString().replaceAll("\"", "");

                                 globalClass.setId(user_id);
                                 globalClass.setName(login);
                                 globalClass.setFname(user_first_name);
                                 globalClass.setLname(user_last_name);
                                 globalClass.setPhone_number(user_phone);
                                 globalClass.setOrgazination(user_organization);
                                 globalClass.setEmail(email);
                                 globalClass.setLogin_status(true);

                                 globalClass.setLogin_from("signup");

                                 prefrence.savePrefrence();

                                 Toasty.success(LoginScreen.this,message, Toast.LENGTH_SHORT, true).show();
                                 Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                                 startActivity(intent);
                                 finish();
                                 pd.dismiss();

                             }
                             else {

                                 Toasty.success(LoginScreen.this, message, Toast.LENGTH_SHORT, true).show();
                             }


                                 Log.d(TAG,"Token \n" +message);



                         }catch (Exception e) {

                             Toast.makeText(getApplicationContext(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                             e.printStackTrace();

                         }


                     }
                 }, new Response.ErrorListener() {

                     @Override

                     public void onErrorResponse(VolleyError error) {
                         Log.e(TAG, "Login Error: " + error.getMessage());
                         Toast.makeText(getApplicationContext(),
                                 "Connection Error", Toast.LENGTH_LONG).show();
                         pd.dismiss();
                     }
                 }) {

                     @Override
                     protected Map<String, String> getParams() {
                         // Posting parameters to login url
                         Map<String, String> params = new HashMap<>();

                         params.put("username", username);
                         params.put("password", password);
                         params.put("view",view);

                         return params;
                     }

                 };

                 // Adding request to request queue
                 GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
                 strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

             }


         }