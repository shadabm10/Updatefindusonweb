package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import sketch.findusonweb.Adapter.Adapter_invite_friend;
import sketch.findusonweb.Adapter.Adapter_invoice;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class invite_friend_from_dashboard extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    Adapter_invite_friend adapter_invoice;
    TextView back_img;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend_from_dashboard);
        listing=findViewById(R.id.invite_listing);
        back_img=findViewById(R.id.back_img);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(invite_friend_from_dashboard.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(invite_friend_from_dashboard.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(invite_friend_from_dashboard.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        ReviewList();
        list_namesfavoriteAll=new ArrayList<>();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
    private void ReviewList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        // pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);

                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String mail_id = images1.get("mail_id").toString().replaceAll("\"", "");
                            String user_organization = images1.get("user_organization").toString().replaceAll("\"", "");
                            String user_first_name = images1.get("user_first_name").toString().replaceAll("\"", "");
                            String user_last_name = images1.get("user_last_name").toString().replaceAll("\"", "");
                            String user_email = images1.get("user_email").toString().replaceAll("\"", "");
                            String user_phone = images1.get("user_phone").toString().replaceAll("\"", "");
                            String refer_userid = images1.get("refer_userid").toString().replaceAll("\"", "");
                            String campaign_source_field = images1.get("campaign_source_field").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String cor_mail_id = images1.get("cor_mail_id").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("mail_id", mail_id);
                            hashMap.put("user_organization", user_organization);
                            hashMap.put("user_first_name", user_first_name);
                            hashMap.put("user_last_name", user_last_name);
                            hashMap.put("user_email", user_email);
                            hashMap.put("user_phone", user_phone);
                            hashMap.put("refer_userid", refer_userid);
                            hashMap.put("campaign_source_field", campaign_source_field);
                            hashMap.put("sync", sync);
                            hashMap.put("cor_mail_id", cor_mail_id);
                            hashMap.put("status", status);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new Adapter_invite_friend(invite_friend_from_dashboard.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);
                    }
                    else


                    {


                        Toasty.success(invite_friend_from_dashboard.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(invite_friend_from_dashboard.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("user_id", globalClass.getId());
                params.put("view", "getInviteFriendsByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}