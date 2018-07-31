package sketch.findusonweb.Screen;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
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
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class SearchListing extends AppCompatActivity{
    ListView listView;
    String TAG = "Listing";
    TextView back_img,final_search;
    ImageView img_grid,seach_button,header_img;
    String textString;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    ProgressDialog pd;
    EditText search_edit;

    ArrayList<HashMap<String,String>> list_names;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_listing);


        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(SearchListing.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(SearchListing.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(SearchListing.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        list_names = new ArrayList<>();

        search_edit=findViewById(R.id.search_service);
        seach_button=findViewById(R.id.search_button);
        listView=findViewById(R.id.list);
        final_search=findViewById(R.id.textView);
        back_img=findViewById(R.id.cart_img);
        header_img=findViewById(R.id.header_image);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        seach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit.setVisibility(View.VISIBLE);
                header_img.setVisibility(View.GONE);
                final_search.setText("");
                //  list_names.clear();

            }
        });



        ViewList();
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    ViewList_new(search_edit.getText().toString());

                }else{
                    Toasty.warning(SearchListing.this, "Please check your internet connect", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    public void ViewList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        // pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();
                list_names.clear();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    final_search.setText(message);

                    if (status.equals("1")) {

                        JsonArray images = jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {

                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String primary_category_name = images1.get("primary_category_name").toString().replaceAll("\"", "");
                            String description_image = images1.get("short_description").toString().replaceAll("\"", "");
                            String location_name = images1.get("location_name").toString().replaceAll("\"", "");
                            String logo_url = images1.get("logo_url").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");
                           /* JsonObject offerObject = jobj.getAsJsonObject("ratings");
                            String rating =offerObject.get("rating").toString();*/

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description_image);
                            hashMap.put("primary_category_name", primary_category_name);
                            hashMap.put("logo_url", logo_url);
                            hashMap.put("id", id);
                            hashMap.put("location_name",location_name);
                            // hashMap.put("rating",rating);

                            list_names.add(hashMap);
                            Log.d(TAG, "id: " + id);

                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterSearch adapterSearch = new AdapterSearch(SearchListing.this, list_names);
                        listView.setAdapter(adapterSearch);
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(SearchListing.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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



                params.put("product", getIntent().getStringExtra("product"));
                params.put("keyword", getIntent().getStringExtra("keyword"));
                params.put("location", getIntent().getStringExtra("location"));
                params.put("view", globalClass.search_business);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    public void ViewList_new(final String product) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        //   pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();
                list_names.clear();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    final_search.setText(message);

                    if (status.equals("1")) {
                        JsonArray images = jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {

                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String primary_category_name = images1.get("primary_category_name").toString().replaceAll("\"", "");
                            String description_image = images1.get("short_description").toString().replaceAll("\"", "");
                            String location_name = images1.get("location_name").toString().replaceAll("\"", "");
                            String logo_url = images1.get("logo_url").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            //  globalClass.setListId(id);



                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description_image);
                            hashMap.put("primary_category_name", primary_category_name);
                            hashMap.put("logo_url", logo_url);
                            hashMap.put("id", id);
                            hashMap.put("location_name",location_name);

                            list_names.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterSearch adapterSearch = new AdapterSearch(SearchListing.this, list_names);
                        listView.setAdapter(adapterSearch);
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(SearchListing.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                //  pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("product",getIntent().getStringExtra("product"));
                params.put("keyword",product);
                params.put("location", getIntent().getStringExtra("location"));
                params.put("view", globalClass.search_business);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}