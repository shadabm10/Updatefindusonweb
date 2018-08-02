package sketch.findusonweb.Screen;


import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import sketch.findusonweb.Adapter.AdapterFavorite;
import sketch.findusonweb.Adapter.AdapterReview;
import sketch.findusonweb.Adapter.AdapterToDoScreen;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class ToDoScreen extends AppCompatActivity {
    ListView listView;
    String TAG = "Listing";
    TextView back_img,final_search,cart_img,view_all_favorite,view_all,view_all_review,credit_tv;
    ImageView img_grid,seach_button,header_img,menu;
    String textString;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    ProgressDialog pd;
    EditText search_edit;
    AdapterToDoScreen adapterToDoScreen;
    AdapterFavorite adapterFavorite;
    AdapterReview adapterReview;
    ArrayList<HashMap<String,String>> list_namesfavorite;
    ArrayList<HashMap<String,String>> list_names;
    ArrayList<HashMap<String,String>> Arraylist_review;

    RecyclerView recyclerView_to,recyclerView_favorite,recyclerView_review;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_screen);
       cart_img=findViewById(R.id.back_img);
       view_all_favorite=findViewById(R.id.view_all);
       credit_tv=findViewById(R.id.my_credit_tv);
        view_all_review=findViewById(R.id.view_all_review);
        // menu=findViewById(R.id.menu);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ToDoScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ToDoScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(ToDoScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }


        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list_names = new ArrayList<>();
        list_namesfavorite=new ArrayList<>();
        Arraylist_review =new ArrayList<>();
        recyclerView_to = findViewById(R.id.to_do);
        recyclerView_favorite=findViewById(R.id.favorites);
        recyclerView_review=findViewById(R.id.my_reviews);

        recyclerView_to.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_to.setLayoutManager(mLayoutManager);

        recyclerView_favorite.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManagerFavorite = new LinearLayoutManager(getApplicationContext());
        recyclerView_favorite.setLayoutManager(mLayoutManagerFavorite);

        recyclerView_review.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManagerReview= new LinearLayoutManager(getApplicationContext());
        recyclerView_review.setLayoutManager(mLayoutManagerReview);
        ToDoList();
        view_all_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Favorites.class));
            }
        });
        view_all_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Listings.class));
            }
        });
        credit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),CreditHistory.class));
            }
        });

    }
    private void ToDoList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
             {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                //pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                      //  String User_id = images1.get("user_id").toString().replaceAll("\"", "");
                       // String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String credit_id = images1.get("credit_id").toString().replaceAll("\"", "");
                        String points = images1.get("points").toString().replaceAll("\"", "");
                      //  String date = images1.get("date").toString().replaceAll("\"", "");
                        String status = images1.get("status").toString().replaceAll("\"", "");
                       // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String todo_task = images1.get("todo_task").toString().replaceAll("\"", "");
//                        String credits_points = images1.get("credits_points").toString().replaceAll("\"", "");
                        String fw_id = images1.get("fw_id").toString().replaceAll("\"", "");

                      //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                      //  hashMap.put("user_id", User_id);
                      //  hashMap.put("listing_id", listing_id);
                        hashMap.put("credit_id", credit_id);
                        hashMap.put("points", points);
                       // hashMap.put("date", date);
                        hashMap.put("status", status);
                       // hashMap.put("sync", sync);
                        hashMap.put("todo_task", todo_task);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("fw_id", fw_id);


                        list_names.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_names);

                    }
                    Log.d(TAG, "Listmane outer: " + list_names);

                    adapterToDoScreen = new AdapterToDoScreen(ToDoScreen.this, list_names);
                    recyclerView_to.setAdapter(adapterToDoScreen);

                    favorite();

                } catch (Exception e) {

                    Toasty.warning(ToDoScreen.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
              //  pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id", globalClass.getId());
                params.put("view", "getToDoListByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void favorite() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

       // pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                //pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                          String id = images1.get("id").toString().replaceAll("\"", "");
                         String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String listing_title = images1.get("listing_title").toString().replaceAll("\"", "");
                        String listing_url = images1.get("listing_url").toString().replaceAll("\"", "");
                          String type = images1.get("type").toString().replaceAll("\"", "");
                        String product_title = images1.get("product_title").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String product_url = images1.get("product_url").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        //  hashMap.put("listing_id", listing_id);
                        hashMap.put("id",id);
                        hashMap.put("listing_id", listing_id);
                         hashMap.put("listing_title", listing_title);
                        hashMap.put("listing_url", listing_url);
                         hashMap.put("product_title", product_title);
                        hashMap.put("type", type);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("product_url", product_url);


                        list_namesfavorite.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavorite);

                    }

                    adapterFavorite = new AdapterFavorite(ToDoScreen.this, list_namesfavorite);
                    recyclerView_favorite.setAdapter(adapterFavorite);
                    ReviewList();


                } catch (Exception e) {

                    Toasty.warning(ToDoScreen.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getFavoritiesListByUser");
                params.put("type", "All");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

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
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String rating_id = images1.get("rating_id").toString().replaceAll("\"", "");
                            String comment_count = images1.get("comment_count").toString().replaceAll("\"", "");
                            String helpful_count = images1.get("helpful_count").toString().replaceAll("\"", "");
                            String helpful_total = images1.get("helpful_total").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String review = images1.get("review").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String rating = images1.get("rating").toString().replaceAll("\"", "");
                            String comments = images1.get("comments").toString().replaceAll("\"", "");
                            String rating_static = images1.get("rating_static").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("rating_id", rating_id);
                            hashMap.put("comment_count", comment_count);
                            hashMap.put("helpful_count", helpful_count);
                            hashMap.put("helpful_total", helpful_total);
                            hashMap.put("status", status);
                            hashMap.put("date", date);
                            hashMap.put("name", name);
                            hashMap.put("title", title);
                            hashMap.put("review", review);
                            hashMap.put("sync", sync);
                            hashMap.put("comments", comments);
                            hashMap.put("rating", rating);
                            hashMap.put("rating_static", rating_static);


                            Arraylist_review.add(hashMap);
                            Log.d(TAG, "Listmane: " + Arraylist_review);

                        }
                        Log.d(TAG, "Listmane outer: " + Arraylist_review);

                        adapterReview = new AdapterReview(ToDoScreen.this, Arraylist_review);
                        recyclerView_review.setAdapter(adapterReview);
                    }
                    else


                    {


                          Toasty.success(ToDoScreen.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(ToDoScreen.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getMyReviews");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}