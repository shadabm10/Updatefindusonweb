package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import sketch.findusonweb.Adapter.AdapterBrowseCategorySingle;
import sketch.findusonweb.Adapter.AdapterSearch;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 10/7/18.
 */

public class BrowseProductSingleSelection extends AppCompatActivity {
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
    String id;

    ArrayList<HashMap<String,String>> list_names;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_category_single_selection);
       // menu=findViewById(R.id.menu);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseProductSingleSelection.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseProductSingleSelection.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(BrowseProductSingleSelection.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        list_names = new ArrayList<>();
        id = getIntent().getStringExtra("id");

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
                    Toasty.warning(BrowseProductSingleSelection.this, "Please check your internet connect", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
/*
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(BrowseProductSingleSelection.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(BrowseProductSingleSelection.this, DashboardScreen.class);
                    startActivity(intent);
                }
            }
        });
*/


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
                list_names.clear();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                  //  String message = jobj.get("message").toString().replaceAll("\"", "");
                  //  Log.d(TAG, "message: "+message);
                   // final_search.setText(message);

                    if (success.equals("1")) {

                        JsonArray images = jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {

                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String priority = images1.get("priority").toString().replaceAll("\"", "");
                            String priority_weight = images1.get("priority_weight").toString().replaceAll("\"", "");
                            String priority_calculated = images1.get("priority_calculated").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String description_short = images1.get("description_short").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String meta_title = images1.get("meta_title").toString().replaceAll("\"", "");
                            String meta_description = images1.get("meta_description").toString().replaceAll("\"", "");
                            String meta_keywords = images1.get("meta_keywords").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String logo_extension = images1.get("logo_extension").toString().replaceAll("\"", "");
                            String logo_background = images1.get("logo_background").toString().replaceAll("\"", "");
                            String phone = images1.get("phone").toString().replaceAll("\"", "");
                            String fax = images1.get("fax").toString().replaceAll("\"", "");
                            String location_id = images1.get("location_id").toString().replaceAll("\"", "");
                            String listing_address1 = images1.get("listing_address1").toString().replaceAll("\"", "");
                            String listing_address2 = images1.get("listing_address2").toString().replaceAll("\"", "");
                            String listing_zip = images1.get("listing_zip").toString().replaceAll("\"", "");
                            String location_text_1 = images1.get("location_text_1").toString().replaceAll("\"", "");
                            String location_text_2 = images1.get("location_text_2").toString().replaceAll("\"", "");
                            String location_text_3 = images1.get("location_text_3").toString().replaceAll("\"", "");
                            String location_search_text = images1.get("location_search_text").toString().replaceAll("\"", "");
                            String hours = images1.get("hours").toString().replaceAll("\"", "");
                            String latitude = images1.get("latitude").toString().replaceAll("\"", "");
                            String longitude = images1.get("longitude").toString().replaceAll("\"", "");
                            String coordinates_date_checked = images1.get("coordinates_date_checked").toString().replaceAll("\"", "");
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String www_status = images1.get("www_status").toString().replaceAll("\"", "");
                            String www_reciprocal = images1.get("www_reciprocal").toString().replaceAll("\"", "");
                            String www_date_checked = images1.get("www_date_checked").toString().replaceAll("\"", "");
                            String website_clicks = images1.get("website_clicks").toString().replaceAll("\"", "");
                            String phone_views = images1.get("phone_views").toString().replaceAll("\"", "");
                            String email_views = images1.get("email_views").toString().replaceAll("\"", "");
                            String shares = images1.get("shares").toString().replaceAll("\"", "");
                            String www_screenshot_last_updated = images1.get("www_screenshot_last_updated").toString().replaceAll("\"", "");
                            String facebook_page_id = images1.get("facebook_page_id").toString().replaceAll("\"", "");
                            String google_page_id = images1.get("google_page_id").toString().replaceAll("\"", "");
                            String linkedin_id = images1.get("linkedin_id").toString().replaceAll("\"", "");
                            String linkedin_company_id = images1.get("linkedin_company_id").toString().replaceAll("\"", "");
                            String twitter_id = images1.get("twitter_id").toString().replaceAll("\"", "");
                            String pinterest_id = images1.get("pinterest_id").toString().replaceAll("\"", "");
                            String youtube_id = images1.get("youtube_id").toString().replaceAll("\"", "");
                            String foursquare_id = images1.get("foursquare_id").toString().replaceAll("\"", "");
                            String instagram_id = images1.get("instagram_id").toString().replaceAll("\"", "");
                            String ip = images1.get("ip").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String date_update = images1.get("date_update").toString().replaceAll("\"", "");
                            String ip_update = images1.get("ip_update").toString().replaceAll("\"", "");
                            String impressions = images1.get("impressions").toString().replaceAll("\"", "");
                            String impressions_weekly = images1.get("impressions_weekly").toString().replaceAll("\"", "");
                            String search_impressions = images1.get("search_impressions").toString().replaceAll("\"", "");
                            String emails = images1.get("emails").toString().replaceAll("\"", "");
                            String rating = images1.get("rating").toString().replaceAll("\"", "");
                            String banner_impressions = images1.get("banner_impressions").toString().replaceAll("\"", "");
                            String banner_clicks = images1.get("banner_clicks").toString().replaceAll("\"", "");
                            String counterip = images1.get("counterip").toString().replaceAll("\"", "");
                            String mail = images1.get("mail").toString().replaceAll("\"", "");
                            String claimed = images1.get("claimed").toString().replaceAll("\"", "");
                            String comment = images1.get("comment").toString().replaceAll("\"", "");
                            String votes = images1.get("votes").toString().replaceAll("\"", "");
                            String header_template_file = images1.get("header_template_file").toString().replaceAll("\"", "");
                            String footer_template_file = images1.get("footer_template_file").toString().replaceAll("\"", "");
                            String wrapper_template_file = images1.get("wrapper_template_file").toString().replaceAll("\"", "");
                            String template_file = images1.get("template_file").toString().replaceAll("\"", "");
                            String template_file_results = images1.get("template_file_results").toString().replaceAll("\"", "");
                            String category_limit = images1.get("category_limit").toString().replaceAll("\"", "");
                            String featured = images1.get("featured").toString().replaceAll("\"", "");
                            String featured_date = images1.get("featured_date").toString().replaceAll("\"", "");
                            String friendly_url_allow = images1.get("friendly_url_allow").toString().replaceAll("\"", "");
                            String html_editor_allow = images1.get("html_editor_allow").toString().replaceAll("\"", "");
                            String phone_allow = images1.get("phone_allow").toString().replaceAll("\"", "");
                            String fax_allow = images1.get("fax_allow").toString().replaceAll("\"", "");
                            String address_allow = images1.get("address_allow").toString().replaceAll("\"", "");
                            String zip_allow = images1.get("zip_allow").toString().replaceAll("\"", "");
                            String hours_allow = images1.get("hours_allow").toString().replaceAll("\"", "");
                            String email_allow = images1.get("email_allow").toString().replaceAll("\"", "");
                            String email_friend_allow = images1.get("email_friend_allow").toString().replaceAll("\"", "");
                            String www_allow = images1.get("www_allow").toString().replaceAll("\"", "");
                            String www_screenshot_allow = images1.get("www_screenshot_allow").toString().replaceAll("\"", "");
                            String require_reciprocal = images1.get("require_reciprocal").toString().replaceAll("\"", "");
                            String map_allow = images1.get("map_allow").toString().replaceAll("\"", "");
                            String logo_allow = images1.get("logo_allow").toString().replaceAll("\"", "");
                            String logo_background_allow = images1.get("logo_background_allow").toString().replaceAll("\"", "");
                            String reviews_allow = images1.get("reviews_allow").toString().replaceAll("\"", "");
                            String ratings_allow = images1.get("ratings_allow").toString().replaceAll("\"", "");
                            String suggestion_allow = images1.get("suggestion_allow").toString().replaceAll("\"", "");
                            String claim_allow = images1.get("claim_allow").toString().replaceAll("\"", "");
                            String pdf_allow = images1.get("pdf_allow").toString().replaceAll("\"", "");
                            String vcard_allow = images1.get("vcard_allow").toString().replaceAll("\"", "");
                            String addtofavorites_allow = images1.get("addtofavorites_allow").toString().replaceAll("\"", "");
                            String print_allow = images1.get("print_allow").toString().replaceAll("\"", "");
                            String qrcode_allow = images1.get("qrcode_allow").toString().replaceAll("\"", "");
                            String share_allow = images1.get("share_allow").toString().replaceAll("\"", "");
                            String contact_requests_allow = images1.get("contact_requests_allow").toString().replaceAll("\"", "");
                            String social_links_allow = images1.get("social_links_allow").toString().replaceAll("\"", "");
                            String coordinates_allow = images1.get("coordinates_allow").toString().replaceAll("\"", "");
                            String classifieds_images_allow = images1.get("classifieds_images_allow").toString().replaceAll("\"", "");
                            String classifieds_limit = images1.get("classifieds_limit").toString().replaceAll("\"", "");
                            String events_limit = images1.get("events_limit").toString().replaceAll("\"", "");
                            String jobs_limit = images1.get("jobs_limit").toString().replaceAll("\"", "");
                            String locations_limit = images1.get("locations_limit").toString().replaceAll("\"", "");
                            String images_limit = images1.get("images_limit").toString().replaceAll("\"", "");
                            String documents_limit = images1.get("documents_limit").toString().replaceAll("\"", "");
                            String title_size = images1.get("title_size").toString().replaceAll("\"", "");
                            String short_description_size = images1.get("short_description_size").toString().replaceAll("\"", "");
                            String description_size = images1.get("description_size").toString().replaceAll("\"", "");
                            String description_images_limit = images1.get("description_images_limit").toString().replaceAll("\"", "");
                            String meta_title_size = images1.get("meta_title_size").toString().replaceAll("\"", "");
                            String meta_description_size = images1.get("meta_description_size").toString().replaceAll("\"", "");
                            String meta_keywords_limit = images1.get("meta_keywords_limit").toString().replaceAll("\"", "");
                            String keywords_limit = images1.get("keywords_limit").toString().replaceAll("\"", "");
                            String timezone = images1.get("timezone").toString().replaceAll("\"", "");
                            String custom_1 = images1.get("custom_1").toString().replaceAll("\"", "");
                            String custom_1_allow = images1.get("custom_1_allow").toString().replaceAll("\"", "");
                            String banner_limit_1 = images1.get("banner_limit_1").toString().replaceAll("\"", "");
                            String banner_limit_2 = images1.get("banner_limit_2").toString().replaceAll("\"", "");
                            String banner_limit_3 = images1.get("banner_limit_3").toString().replaceAll("\"", "");
                            String home_page = images1.get("home_page").toString().replaceAll("\"", "");
                            String listing_token = images1.get("listing_token").toString().replaceAll("\"", "");
                            String unconfirmed_acc = images1.get("unconfirmed_acc").toString().replaceAll("\"", "");
                            String custom_54 = images1.get("custom_54").toString().replaceAll("\"", "");
                            String custom_54_allow = images1.get("custom_54_allow").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String banner_limit_4 = images1.get("banner_limit_4").toString().replaceAll("\"", "");
                            String banner_limit_5 = images1.get("banner_limit_5").toString().replaceAll("\"", "");
                            String banner_limit_6 = images1.get("banner_limit_6").toString().replaceAll("\"", "");
                            String banner_limit_7 = images1.get("banner_limit_7").toString().replaceAll("\"", "");
                            String custom_56 = images1.get("custom_56").toString().replaceAll("\"", "");
                            String custom_56_allow = images1.get("custom_56_allow").toString().replaceAll("\"", "");
                            String logo_url = images1.get("logo_url").toString().replaceAll("\"", "");

                           /* JsonObject images1 = jobj.getAsJsonObject("ratings");
                            String rating =offerObject.get("rating").toString();*/

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description_short", description_short);
                            hashMap.put("location_text_1", location_text_1);
                            hashMap.put("logo_url", logo_url);
                            hashMap.put("id", id);
                            hashMap.put("location_search_text",location_search_text);
                            // hashMap.put("rating",rating);

                            list_names.add(hashMap);
                            Log.d(TAG, "id: " + id);

                            // Toasty.success(BrowseProductSingleSelection.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterBrowseCategorySingle adapterSearch = new AdapterBrowseCategorySingle(BrowseProductSingleSelection.this, list_names);
                        listView.setAdapter(adapterSearch);
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(BrowseProductSingleSelection.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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



                params.put("id",id );
                params.put("view", "browseCategory");
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
                            // Toasty.success(BrowseProductSingleSelection.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterSearch adapterSearch = new AdapterSearch(BrowseProductSingleSelection.this, list_names);
                        listView.setAdapter(adapterSearch);
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(BrowseProductSingleSelection.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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