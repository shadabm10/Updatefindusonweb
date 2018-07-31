package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import sketch.findusonweb.Adapter.AdapterProduct;
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class ProductScreen extends AppCompatActivity{

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_list_product;
    String TAG = "product";
    TextView back_img;
    RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ProductScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ProductScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_products = new ArrayList<>();



        back_img =findViewById(R.id.back_img);
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


        rl_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductScreen.this,AddProductScreen.class);
                startActivity(intent);
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
                        JsonArray products = jobj.getAsJsonArray("products");

                        for (int i = 0; i < products.size(); i++) {



                            JsonObject images1 = products.get(i).getAsJsonObject();
                            String fow_id = images1.get("fow_id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String expire_date = images1.get("expire_date").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String fw_user_id = images1.get("fw_user_id").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String images = images1.get("images").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("fow_id", fow_id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("title", title);
                            hashMap.put("date", date);
                            hashMap.put("description", description);
                            hashMap.put("price",price);
                            hashMap.put("expire_date",expire_date);
                            hashMap.put("type",type);
                            hashMap.put("fw_user_id",fw_user_id);
                            hashMap.put("listing_name",listing_name);
                            hashMap.put("images",images);
                            hashMap.put("keywords",keywords);
                           /* hashMap.put("www",www);
                            hashMap.put("id",id);
                            hashMap.put("listingfriendly_url",listingfriendly_url);*/
                            // hashMap.put(pricerating",rating);

                            list_products.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterProduct adapterSearch = new AdapterProduct(ProductScreen.this, list_products);
                        rv_list_product.setAdapter(adapterSearch);
                  //  }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ProductScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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



                params.put("listing_id", "808684");
                params.put("view","getProductsByListing");
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
