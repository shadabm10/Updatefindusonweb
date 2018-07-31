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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import sketch.findusonweb.Adapter.AdapterBrowse;
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Adapter.BrowseProductAdapter;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class BrowseProduct extends AppCompatActivity{
    ListView listView;
    String TAG = "Listing";
    TextView back_img,final_search;
    ImageView img_grid,seach_button,header_img,menu;
    String textString;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;

    ProgressDialog pd;
    EditText search_edit;
    BrowseProductAdapter adapterBrowse;
    String serach_location,search_title,search_description,search_category,search_keywords,from,to,GradeOne,GradeTwo,GradeThree;
    String text="";
    ArrayList<HashMap<String,String>> list_names;

    ImageButton ib_icon_slide;
    RelativeLayout rl_slide_layout;
    TextView tv_search_1;
    EditText edt_location,edt_title,edt_description,edt_category,edt_keyword,edt_price_from,edt_price_to;
    RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_product_job);

       // menu=findViewById(R.id.menu);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseProduct.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseProduct.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(BrowseProduct.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }


        list_names = new ArrayList<>();
        browseJob();

        search_edit=findViewById(R.id.serach_edittext);
        seach_button=findViewById(R.id.search_button);
        // img_grid=findViewById(R.id.img_grid);
        listView=findViewById(R.id.list);
        final_search=findViewById(R.id.textView);
        back_img=findViewById(R.id.cart_img);
        header_img=findViewById(R.id.header_image);
        ib_icon_slide=findViewById(R.id.ib_icon_slide);
        rl_slide_layout=findViewById(R.id.rl_slide_layout);
        tv_search_1=findViewById(R.id.tv_search_1);
        ///////////////////////////////////////////////////////////////
        edt_location=findViewById(R.id.edt_location);
        edt_title=findViewById(R.id.edt_title);
        edt_description=findViewById(R.id.edt_description);
        edt_category=findViewById(R.id.edt_category);
        edt_keyword=findViewById(R.id.edt_keyword);
        edt_price_from=findViewById(R.id.edt_price_from);
        edt_price_to=findViewById(R.id.edt_price_to);
        radiogroup=findViewById(R.id.radiogroup);

        rl_slide_layout.setVisibility(View.GONE);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
/*
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(getApplicationContext(),DashboardScreen.class);
                startActivity(menu);
            }
        });
*/

        seach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit.setVisibility(View.VISIBLE);
                header_img.setVisibility(View.GONE);
                final_search.setText("");
              //  list_names.clear();

            }
        });

       /* img_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                img_grid.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.button), android.graphics.PorterDuff.Mode.MULTIPLY);            }
        });*/
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=findViewById(checkedId);
                text=  rb.getText().toString();

                if(text.equals("Grade One")){
                    GradeOne="one";
                   // Toast.makeText(getApplicationContext(), GradeOne, Toast.LENGTH_SHORT).show();

                }
                else if(text.equals("Grade Two"))
                {
                    GradeTwo="Two";
                   // Toast.makeText(getApplicationContext(), GradeTwo, Toast.LENGTH_SHORT).show();

                }
                else{
                    GradeThree="Three";
                   // Toast.makeText(getApplicationContext(), GradeThree, Toast.LENGTH_SHORT).show();
                    // textViewChoice.setText("You Selected " + rb.getText());
                    // Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
               // textViewChoice.setText("You Selected " + rb.getText());
               // Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    //ViewList_new(search_edit.getText().toString());

                }else{
                    Toasty.warning(BrowseProduct.this, "Please check your internet connect", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ib_icon_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation RightSwipe = AnimationUtils.loadAnimation(BrowseProduct.this, R.anim.right_to_left);
                rl_slide_layout.startAnimation(RightSwipe);
                rl_slide_layout.setVisibility(View.VISIBLE);
            }
        });

        tv_search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_keywords=edt_keyword.getText().toString();
                search_title=edt_title.getText().toString();
                search_description=edt_description.getText().toString();
                serach_location=edt_location.getText().toString();
                search_category=edt_category.getText().toString();
                from=edt_price_from.getText().toString();
                to=edt_price_to.getText().toString();
                Log.d(TAG, "onClick: Search");
                BrowseFilter(search_keywords,search_title,search_description,serach_location,search_category,from,to,text);
                rl_slide_layout.setVisibility(View.GONE);
            }
        });


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
              //  list_names.clear();

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
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String listing_location_id = images1.get("listing_location_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String meta_title = images1.get("meta_title").toString().replaceAll("\"", "");
                            String meta_description = images1.get("meta_description").toString().replaceAll("\"", "");
                            String meta_keywords = images1.get("meta_keywords").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String expire_date = images1.get("expire_date").toString().replaceAll("\"", "");
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String buttoncode = images1.get("buttoncode").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String priority = images1.get("priority").toString().replaceAll("\"", "");
                            String custom_15 = images1.get("custom_15").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");
                            String image_url = images1.get("images").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("listing_location_id", listing_location_id);
                            hashMap.put("primary_category_id", primary_category_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("date", date);
                            hashMap.put("meta_title", meta_title);
                            hashMap.put("meta_description", meta_description);
                            hashMap.put("meta_keywords", meta_keywords);
                            hashMap.put("keywords", keywords);
                            hashMap.put("price", price);
                            hashMap.put("expire_date", expire_date);
                            hashMap.put("www", www);
                            hashMap.put("buttoncode", buttoncode);
                            hashMap.put("type", type);
                            hashMap.put("priority", priority);
                            hashMap.put("custom_15", custom_15);
                            hashMap.put("sync", sync);
                            hashMap.put("listingfriendly_url", listingfriendly_url);
                            hashMap.put("description", description);
                            hashMap.put("listing_name", listing_name);
                            hashMap.put("images", image_url);
                            hashMap.put("id", id);


                            list_names.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowse = new BrowseProductAdapter(BrowseProduct.this, list_names);
                        listView.setAdapter(adapterBrowse);


                    } else


                    {


                      //  Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                  //  Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseProduct.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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

                params.put("view", globalClass.browseProducts);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
/*
    public void ViewList_new(final String product) {
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
                    String status = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    final_search.setText(message);

                    if (status.equals("1")) {
                        JsonArray images = jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {

                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String listing_location_id = images1.get("listing_location_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String meta_title = images1.get("meta_title").toString().replaceAll("\"", "");
                            String meta_description = images1.get("meta_description").toString().replaceAll("\"", "");
                            String meta_keywords = images1.get("meta_keywords").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String expire_date = images1.get("expire_date").toString().replaceAll("\"", "");
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String buttoncode = images1.get("buttoncode").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String priority = images1.get("priority").toString().replaceAll("\"", "");
                            String custom_15 = images1.get("custom_15").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");
                            String category = images1.get("category").toString().replaceAll("\"", "");

                            String image_url = images1.get("image_url").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description);
                            hashMap.put("primary_category_name", listing_name);
                            hashMap.put("logo_url", image_url);
                            hashMap.put("id", id);
                            hashMap.put("location_name",category);

                            list_names.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        BrowseProductAdapter adapterSearch = new BrowseProductAdapter(BrowseProduct.this, list_names);
                        listView.setAdapter(adapterSearch);
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(BrowseProduct.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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



                params.put("title", product);
                params.put("description", getIntent().getStringExtra("description"));
                params.put("category", getIntent().getStringExtra("category"));
                params.put("keywords", getIntent().getStringExtra("keywords"));
                params.put("page", getIntent().getStringExtra("page"));
                params.put("view", globalClass.browseProducts);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
*/


    private void BrowseFilter(final String keywords,final String title,final String description,final String location,final String category ,final String price1,final String price2,final String grade )
    {
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
                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    // String message = jobj.get("msg").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String listing_location_id = images1.get("listing_location_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String meta_title = images1.get("meta_title").toString().replaceAll("\"", "");
                            String meta_description = images1.get("meta_description").toString().replaceAll("\"", "");
                            String meta_keywords = images1.get("meta_keywords").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String expire_date = images1.get("expire_date").toString().replaceAll("\"", "");
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String buttoncode = images1.get("buttoncode").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String priority = images1.get("priority").toString().replaceAll("\"", "");
                            String custom_15 = images1.get("custom_15").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");
                            String image_url = images1.get("images").toString().replaceAll("\"", "");
                            String id = images1.get("id").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("listing_location_id", listing_location_id);
                            hashMap.put("primary_category_id", primary_category_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("date", date);
                            hashMap.put("meta_title", meta_title);
                            hashMap.put("meta_description", meta_description);
                            hashMap.put("meta_keywords", meta_keywords);
                            hashMap.put("keywords", keywords);
                            hashMap.put("price", price);
                            hashMap.put("expire_date", expire_date);
                            hashMap.put("www", www);
                            hashMap.put("buttoncode", buttoncode);
                            hashMap.put("type", type);
                            hashMap.put("priority", priority);
                            hashMap.put("custom_15", custom_15);
                            hashMap.put("sync", sync);
                            hashMap.put("listingfriendly_url", listingfriendly_url);
                            hashMap.put("description", description);
                            hashMap.put("listing_name", listing_name);
                            hashMap.put("images", image_url);
                            hashMap.put("id", id);


                            list_names.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowse = new BrowseProductAdapter(BrowseProduct.this, list_names);
                        listView.setAdapter(adapterBrowse);


                    } else


                    {


                        //  Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseProduct.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Access Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Data not access", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view", "browseProducts");
                params.put("keywords", keywords);
                params.put("title", title);
                params.put("description", description);
                params.put("category", category);
                params.put("grade", grade);

                params.put("price1", price1);
                params.put("price2", price2);


                params.put("location", location);


                Log.d(TAG, "SearchgetParams: "+params);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}