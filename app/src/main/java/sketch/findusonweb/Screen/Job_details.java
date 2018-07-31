package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 26/5/18.
 */

public class Job_details extends AppCompatActivity {

    String TAG = "Listing";
    TextView back_img,final_search,post_requirement_job,details_about,duration_new,category_name,price,date_time,title_job,send_proposal;
    Shared_Preference prefrence;
    Context context;
    String id;
    GlobalClass globalClass;
    ArrayList<HashMap<String,String>> list_names;
    ProgressDialog pd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);
        back_img=findViewById(R.id.back_img);
        send_proposal=findViewById(R.id.send_proposal);
        details_about=findViewById(R.id.detail_about_us);
        duration_new=findViewById(R.id.duration);
        category_name=findViewById(R.id.category_name);
        title_job=findViewById(R.id.title_job);
        post_requirement_job=findViewById(R.id.post_requirement_job);
        price=findViewById(R.id.price);
        date_time=findViewById(R.id.dataTime);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Job_details.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Job_details.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {

            }
        } else {
            Toasty.info(Job_details.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        list_names = new ArrayList<>();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id = getIntent().getStringExtra("id");
        JobDetails(id);
        Log.d(TAG, "onCreate:deatail "+ getIntent().getStringExtra("id"));
        post_requirement_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(Job_details.this, LoginScreen.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    startActivity(new Intent(getApplicationContext(), PostRequriementScreen.class));
                }
            }
        });
        send_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(Job_details.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Job_details.this, SendProposal.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

    }
    public void JobDetails(final String id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    //final_search.setText(message);

                    if (success.equals("1")) {
                        JsonObject images1 = jobj.getAsJsonObject("data");


                        String id = images1.get("id").toString().replaceAll("\"", "");
                        String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                        String duration = images1.get("duration").toString().replaceAll("\"", "");
                        String budget = images1.get("budget").toString().replaceAll("\"", "");
                        String status = images1.get("status").toString().replaceAll("\"", "");
                        String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");
                        String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                        String sync = images1.get("sync").toString().replaceAll("\"", "");
                                  details_about.setText(description);
                                  date_time.setText(date_requested);
                                  duration_new.setText((duration)+" days");
                                  price.setText(budget);
                                  category_name.setText(primary_category);
                                  title_job.setText(title);



                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }





                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                  //  Toasty.warning(Job_details.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();




                params.put("request_id", id);
                params.put("view", "getrequestDetails");
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}