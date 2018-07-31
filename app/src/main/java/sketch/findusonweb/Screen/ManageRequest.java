package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import sketch.findusonweb.Adapter.AdapterManageRequest;

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 21/6/18.
 */

public class ManageRequest extends AppCompatActivity{

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_list_product;
    String TAG = "product";
    ImageView down_arrow;
    TextView back_img,post_job;
    Spinner spinner_type;
    ArrayList<String> type;
    RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_proposal);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ManageRequest.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ManageRequest.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_products = new ArrayList<>();
        down_arrow = findViewById(R.id.down_arrow_category);

       post_job=findViewById(R.id.post_job);
       spinner_type=findViewById(R.id.spinner_type);
        back_img =findViewById(R.id.img_back);
        rv_list_product =findViewById(R.id.list_product);
        rl_add_product =findViewById(R.id.rl_add_product);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_list_product.setLayoutManager(mLayoutManager);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

post_job.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), PostRequriementScreen.class);
        startActivity(intent);    }
});
        type = new ArrayList<>();
        type.add("All");
        type.add("Active");
        type.add("Payment Received");
        type.add("Customer Review");
        type.add("Unapproved");
        type.add("Complete");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_color,R.id.txt,type)
        {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {



                } else {
                }
                return view;


            }
        };
        dataAdapter.setDropDownViewResource(R.layout.spinner_color);
        spinner_type.setAdapter(dataAdapter);
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_type.performClick();
            }
        });


        ViewList();
    }

    public void ViewList() {
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
                    //      String status = jobj.get("status").toString().replaceAll("\"", "");
                    //      String message = jobj.get("message").toString().replaceAll("\"", "");
                    //    Log.d(TAG, "message: "+message);
                    // final_search.setText(message);

                    // if (status.equals("1")) {
                    JsonArray products = jobj.getAsJsonArray("data");

                    for (int i = 0; i < products.size(); i++) {



                        JsonObject images1 = products.get(i).getAsJsonObject();
                        String fw_id = images1.get("fw_id").toString().replaceAll("\"", "");
                        String fw_user_id = images1.get("fw_user_id").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                        String duration = images1.get("duration").toString().replaceAll("\"", "");
                        String status = images1.get("status").toString().replaceAll("\"", "");
                        String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");
                        String budget = images1.get("budget").toString().replaceAll("\"", "");


                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("fw_id", fw_id);

                        hashMap.put("iterations", primary_category);
                        hashMap.put("duration", duration);
                        hashMap.put("budget", budget);
                        hashMap.put("description", description);
                        hashMap.put("status",status);
                        hashMap.put("title",title);
                        hashMap.put("date_requested",date_requested);
                        hashMap.put("fw_user_id",fw_user_id);


                           /* hashMap.put("www",www);
                            hashMap.put("id",id);
                            hashMap.put("listingfriendly_url",listingfriendly_url);*/
                        // hashMap.put(pricerating",rating);

                        list_products.add(hashMap);
                        Log.d(TAG, "id: " + fw_id);
                        // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                    }

                    AdapterManageRequest adapterSearch = new AdapterManageRequest(ManageRequest.this, list_products);
                    rv_list_product.setAdapter(adapterSearch);
                    //  }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ManageRequest.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                // Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_id", globalClass.getId());
                params.put("view","getAllRequestsByUserID");
                Log.d(TAG, "getID: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
