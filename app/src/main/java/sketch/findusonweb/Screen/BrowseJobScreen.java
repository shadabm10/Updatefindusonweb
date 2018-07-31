package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterBrowse;
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class BrowseJobScreen extends AppCompatActivity {
    String TAG = "login";
    ListView list_browse;
    TextView back_img,post_requirement,browser_job_search;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    String keywords,business,low,high;
    EditText bj_keyword,bj_business,bj_low,bj_high;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_names;
   // ArrayList<String> list_names = new ArrayList<>();
    AdapterBrowse adapterBrowse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_screen);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseJobScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseJobScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(BrowseJobScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        browser_job_search=findViewById(R.id.browse_job_search);
        bj_business=findViewById(R.id.browse_job_business);
        bj_keyword=findViewById(R.id.browse_job_keywords);
        bj_low=findViewById(R.id.browser_job_low);
        bj_high=findViewById(R.id.browser_job_hign);
        post_requirement=findViewById(R.id.post_requirement);
        list_browse = findViewById(R.id.list_browse);
        back_img=findViewById(R.id.back_img);
        list_names = new ArrayList<>();
        browseJob();


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        post_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(BrowseJobScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                startActivity(new Intent(getApplicationContext(),PostRequriementScreen.class));
            }
        });
       // browseJob();
        browser_job_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (!bj_business.getText().toString().isEmpty()||!bj_keyword.getText().toString().isEmpty() || !bj_high.getText().toString().isEmpty()|| !bj_low.getText().toString().isEmpty()) {

                    keywords=bj_keyword.getText().toString();
                    business=bj_business.getText().toString();
                    low=bj_low.getText().toString();
                    high=bj_high.getText().toString();
                    Log.d(TAG, "onClick: browser_job_search ");
                    browseJobFilter(keywords,business,low,high);
             //   }
            }
        });

    }
    private void browseJobFilter(final String keyword,final String business,final String low_price,final String high_price ) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                list_names.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String profile_pic = images1.get("profile_pic").toString().replaceAll("\"", "");
                            String duration = images1.get("duration").toString().replaceAll("\"", "");
                            String budget = images1.get("budget").toString().replaceAll("\"", "");
                            String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description);
                            hashMap.put("primary_category", primary_category);
                            hashMap.put("status", status);
                            hashMap.put("duration", duration);
                            hashMap.put("profile_pic", profile_pic);
                            hashMap.put("budget", budget);
                            hashMap.put("id", id);
                            hashMap.put("date_requested", date_requested);
                            list_names.add(hashMap);
                            Log.d(TAG, "Image: " + primary_category);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowse = new AdapterBrowse(BrowseJobScreen.this, list_names);
                        list_browse.setAdapter(adapterBrowse);



                    } else


                    {


                        Toasty.success(BrowseJobScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseJobScreen.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view", globalClass.browse_job);
                params.put("low_price", low_price);
                params.put("high_price", high_price);
                params.put("keyword", keyword);
                params.put("business", business);


                Log.d(TAG, "getParams: "+params);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                list_names.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String profile_pic = images1.get("profile_pic").toString().replaceAll("\"", "");
                            String duration = images1.get("duration").toString().replaceAll("\"", "");
                            String budget = images1.get("budget").toString().replaceAll("\"", "");
                            String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description);
                            hashMap.put("primary_category", primary_category);
                            hashMap.put("status", status);
                            hashMap.put("duration", duration);
                            hashMap.put("profile_pic", profile_pic);
                            hashMap.put("budget", budget);
                            hashMap.put("id", id);
                            hashMap.put("date_requested", date_requested);
                            list_names.add(hashMap);
                            Log.d(TAG, "Image: " + primary_category);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                         adapterBrowse = new AdapterBrowse(BrowseJobScreen.this, list_names);
                        list_browse.setAdapter(adapterBrowse);


                    } else


                    {


                        Toasty.success(BrowseJobScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseJobScreen.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view", globalClass.browse_job);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
