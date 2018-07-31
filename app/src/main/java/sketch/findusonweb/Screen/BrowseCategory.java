package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import sketch.findusonweb.Adapter.AdapterBrowseCategory;
import sketch.findusonweb.Adapter.BrowseProductAdapter;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 6/7/18.
 */

public class BrowseCategory extends AppCompatActivity {
    ListView listView;
    String TAG = "Listing";
    TextView back_img,final_search,cart_img;
    ImageView img_grid,seach_button,header_img,menu;
    String textString;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    ProgressDialog pd;
    EditText search_edit;
    AdapterBrowseCategory adapterBrowse;

    ArrayList<HashMap<String,String>> list_names;

    ImageButton ib_icon_slide;
    RelativeLayout rl_slide_layout;
    RecyclerView recyclerView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_category);
        cart_img=findViewById(R.id.cart_img);
       // menu=findViewById(R.id.menu);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseCategory.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseCategory.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(BrowseCategory.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

/*
   menu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent menu=new Intent(getApplicationContext(),DashboardScreen.class);
        startActivity(menu);
    }
});
*/
cart_img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});

        list_names = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
        browseJob();
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

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    // String message = jobj.get("msg").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String level = images1.get("level").toString().replaceAll("\"", "");
                            String left_ = images1.get("left_").toString().replaceAll("\"", "");
                            String right_ = images1.get("right_").toString().replaceAll("\"", "");
                            String count = images1.get("count").toString().replaceAll("\"", "");
                            String count_total = images1.get("count_total").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String friendly_url_path = images1.get("friendly_url_path").toString().replaceAll("\"", "");
                            String link = images1.get("link").toString().replaceAll("\"", "");
                            String impressions = images1.get("impressions").toString().replaceAll("\"", "");
                            String description_short = images1.get("description_short").toString().replaceAll("\"", "");
                            String hidden = images1.get("hidden").toString().replaceAll("\"", "");
                            String no_follow = images1.get("no_follow").toString().replaceAll("\"", "");
                            String display_columns = images1.get("display_columns").toString().replaceAll("\"", "");
                            String closed = images1.get("closed").toString().replaceAll("\"", "");
                            String small_image_url = images1.get("small_image_url").toString().replaceAll("\"", "");
                            String image = images1.get("image").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("level", level);
                            hashMap.put("left_", left_);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("right_", right_);
                            hashMap.put("count", count);
                            hashMap.put("count_total", count_total);
                            hashMap.put("friendly_url_path", friendly_url_path);
                            hashMap.put("link", link);
                            hashMap.put("impressions", impressions);
                            hashMap.put("description_short", description_short);
                            hashMap.put("hidden", hidden);
                            hashMap.put("no_follow", no_follow);
                            hashMap.put("display_columns", display_columns);
                            hashMap.put("closed", closed);
                            hashMap.put("small_image_url", small_image_url);
                            hashMap.put("id", id);
                            hashMap.put("image", image);


                            list_names.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowse = new AdapterBrowseCategory(BrowseCategory.this, list_names);
                        recyclerView.setAdapter(adapterBrowse);


                    } else


                    {


                        //  Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseCategory.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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

                params.put("view", "browseCategory");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}