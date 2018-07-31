package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by developer on 28/5/18.
 */

public class Search_business extends AppCompatActivity {
    ListView listView;
    String TAG = "Listing";
    TextView back_img;
    ImageView img_grid,seach_button,header_img;
    String textString;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    ProgressDialog pd;
    EditText search_edit;

    ArrayList<HashMap<String,String>> list_names;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
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
                    Log.d(TAG, "onResponse: "+jobj);
                    JsonObject offerObject = jobj.getAsJsonObject();
                    String id = offerObject.get("id").toString().replaceAll("\"", "");
                    String user_id = offerObject.get("user_id").toString().replaceAll("\"", "");
                    String primary_category_id = offerObject.get("primary_category_id").toString().replaceAll("\"", "");
                    String status = offerObject.get("status").toString().replaceAll("\"", "");
                    String priority = offerObject.get("priority").toString().replaceAll("\"", "");
                    String priority_weight = offerObject.get("priority_weight").toString().replaceAll("\"", "");
                    String priority_calculated = offerObject.get("priority_calculated").toString().replaceAll("\"", "");
                    String title = offerObject.get("title").toString().replaceAll("\"", "");
                    String friendly_url = offerObject.get("friendly_url").toString().replaceAll("\"", "");
                    String description_short = offerObject.get("description_short").toString().replaceAll("\"", "");
                    String description = offerObject.get("description").toString().replaceAll("\"", "");
                    String meta_title = offerObject.get("meta_title").toString().replaceAll("\"", "");
                    String meta_description = offerObject.get("meta_description").toString().replaceAll("\"", "");
                    String meta_keywords = offerObject.get("meta_keywords").toString().replaceAll("\"", "");
                    String keywords = offerObject.get("keywords").toString().replaceAll("\"", "");
                    String logo_extension = offerObject.get("logo_extension").toString().replaceAll("\"", "");
                    String logo_background = offerObject.get("logo_background").toString().replaceAll("\"", "");
                    String phone = offerObject.get("phone").toString().replaceAll("\"", "");
                    String fax = offerObject.get("fax").toString().replaceAll("\"", "");
                    String location_id = offerObject.get("location_id").toString().replaceAll("\"", "");
                    String listing_address1 = offerObject.get("listing_address1").toString().replaceAll("\"", "");
                    String listing_address2 = offerObject.get("listing_address2").toString().replaceAll("\"", "");
                    String listing_zip = offerObject.get("listing_zip").toString().replaceAll("\"", "");
                    String location_text_1 = offerObject.get("location_text_1").toString().replaceAll("\"", "");
                    String location_text_2 = offerObject.get("location_text_2").toString().replaceAll("\"", "");
                    String location_text_3 = offerObject.get("location_text_3").toString().replaceAll("\"", "");
                    String location_search_text = offerObject.get("location_search_text").toString().replaceAll("\"", "");
                    String hours = offerObject.get("hours").toString().replaceAll("\"", "");
                    String latitude = offerObject.get("latitude").toString().replaceAll("\"", "");
                    String longitude = offerObject.get("longitude").toString().replaceAll("\"", "");
                    String coordinates_date_checked = offerObject.get("coordinates_date_checked").toString().replaceAll("\"", "");
                    String www = offerObject.get("www").toString().replaceAll("\"", "");
                    String www_status = offerObject.get("www_status").toString().replaceAll("\"", "");
                    String www_reciprocal = offerObject.get("www_reciprocal").toString().replaceAll("\"", "");
                    String www_date_checked = offerObject.get("www_date_checked").toString().replaceAll("\"", "");
                    String website_clicks = offerObject.get("website_clicks").toString().replaceAll("\"", "");
                    String phone_views = offerObject.get("phone_views").toString().replaceAll("\"", "");
                    String email_views = offerObject.get("email_views").toString().replaceAll("\"", "");
                    String shares = offerObject.get("shares").toString().replaceAll("\"", "");
                    String www_screenshot_last_updated = offerObject.get("www_screenshot_last_updated").toString().replaceAll("\"", "");
                    String facebook_page_id = offerObject.get("facebook_page_id").toString().replaceAll("\"", "");
                    // JsonObject offerObject = offerObject.getAsJsonObject("result");
                    String google_page_id = offerObject.get("google_page_id").toString().replaceAll("\"", "");
                    String linkedin_id = offerObject.get("linkedin_id").toString().replaceAll("\"", "");
                    String linkedin_company_id = offerObject.get("linkedin_company_id").toString().replaceAll("\"", "");
                    String twitter_id = offerObject.get("twitter_id").toString().replaceAll("\"", "");
                    String pinterest_id = offerObject.get("pinterest_id").toString().replaceAll("\"", "");
                    String youtube_id = offerObject.get("youtube_id").toString().replaceAll("\"", "");
                    String foursquare_id = offerObject.get("foursquare_id").toString().replaceAll("\"", "");
                    String instagram_id = offerObject.get("instagram_id").toString().replaceAll("\"", "");
                    String ip = offerObject.get("ip").toString().replaceAll("\"", "");
                    String date = offerObject.get("date").toString().replaceAll("\"", "");
                    String date_update = offerObject.get("date_update").toString().replaceAll("\"", "");
                    String ip_update = offerObject.get("ip_update").toString().replaceAll("\"", "");
                    String impressions = offerObject.get("impressions").toString().replaceAll("\"", "");
                    String impressions_weekly = offerObject.get("impressions_weekly").toString().replaceAll("\"", "");
                    String search_impressions = offerObject.get("search_impressions").toString().replaceAll("\"", "");
                    String emails = offerObject.get("emails").toString().replaceAll("\"", "");
                    String rating = offerObject.get("rating").toString().replaceAll("\"", "");
                    String banner_impressions = offerObject.get("banner_impressions").toString().replaceAll("\"", "");
                    String banner_clicks = offerObject.get("banner_clicks").toString().replaceAll("\"", "");
                    String counterip = offerObject.get("counterip").toString().replaceAll("\"", "");
                    String mail = offerObject.get("mail").toString().replaceAll("\"", "");
                    String claimed = offerObject.get("claimed").toString().replaceAll("\"", "");
                    String comment = offerObject.get("comment").toString().replaceAll("\"", "");
                    String votes = offerObject.get("votes").toString().replaceAll("\"", "");
                    String header_template_file = offerObject.get("header_template_file").toString().replaceAll("\"", "");
                    String footer_template_file = offerObject.get("footer_template_file").toString().replaceAll("\"", "");
                    String wrapper_template_file = offerObject.get("wrapper_template_file").toString().replaceAll("\"", "");
                    String template_file = offerObject.get("template_file").toString().replaceAll("\"", "");
                    String template_file_results = offerObject.get("template_file_results").toString().replaceAll("\"", "");
                    String category_limit = offerObject.get("category_limit").toString().replaceAll("\"", "");
                    String featured = offerObject.get("featured").toString().replaceAll("\"", "");
                    String featured_date = offerObject.get("featured_date").toString().replaceAll("\"", "");
                    String friendly_url_allow = offerObject.get("friendly_url_allow").toString().replaceAll("\"", "");
                    String html_editor_allow = offerObject.get("html_editor_allow").toString().replaceAll("\"", "");
                    String phone_allow = offerObject.get("phone_allow").toString().replaceAll("\"", "");
                    String fax_allow = offerObject.get("fax_allow").toString().replaceAll("\"", "");
                    String address_allow = offerObject.get("address_allow").toString().replaceAll("\"", "");
                    String zip_allow = offerObject.get("zip_allow").toString().replaceAll("\"", "");
                    String hours_allow = offerObject.get("hours_allow").toString().replaceAll("\"", "");
                    String email_allow = offerObject.get("email_allow").toString().replaceAll("\"", "");
                    String email_friend_allow = offerObject.get("email_friend_allow").toString().replaceAll("\"", "");
                    String www_allow = offerObject.get("www_allow").toString().replaceAll("\"", "");
                    String www_screenshot_allow = offerObject.get("www_screenshot_allow").toString().replaceAll("\"", "");
                    String require_reciprocal = offerObject.get("require_reciprocal").toString().replaceAll("\"", "");
                    String map_allow = offerObject.get("map_allow").toString().replaceAll("\"", "");
                    String logo_allow = offerObject.get("logo_allow").toString().replaceAll("\"", "");
                    String logo_background_allow = offerObject.get("logo_background_allow").toString().replaceAll("\"", "");
                    String reviews_allow = offerObject.get("reviews_allow").toString().replaceAll("\"", "");
                    String ratings_allow = offerObject.get("ratings_allow").toString().replaceAll("\"", "");
                    String suggestion_allow = offerObject.get("suggestion_allow").toString().replaceAll("\"", "");
                    String claim_allow = offerObject.get("claim_allow").toString().replaceAll("\"", "");
                    String pdf_allow = offerObject.get("pdf_allow").toString().replaceAll("\"", "");
                    String vcard_allow = offerObject.get("vcard_allow").toString().replaceAll("\"", "");
                    String addtofavorites_allow = offerObject.get("addtofavorites_allow").toString().replaceAll("\"", "");
                    String print_allow = offerObject.get("print_allow").toString().replaceAll("\"", "");
                    String qrcode_allow = offerObject.get("qrcode_allow").toString().replaceAll("\"", "");
                    String share_allow = offerObject.get("share_allow").toString().replaceAll("\"", "");
                    String contact_requests_allow = offerObject.get("contact_requests_allow").toString().replaceAll("\"", "");
                    String social_links_allow = offerObject.get("social_links_allow").toString().replaceAll("\"", "");
                    String coordinates_allow = offerObject.get("coordinates_allow").toString().replaceAll("\"", "");
                    String classifieds_images_allow = offerObject.get("classifieds_images_allow").toString().replaceAll("\"", "");
                    String classifieds_limit = offerObject.get("classifieds_limit").toString().replaceAll("\"", "");
                    String events_limit = offerObject.get("events_limit").toString().replaceAll("\"", "");
                    String jobs_limit = offerObject.get("jobs_limit").toString().replaceAll("\"", "");
                    String locations_limit = offerObject.get("locations_limit").toString().replaceAll("\"", "");
                    String images_limit = offerObject.get("images_limit").toString().replaceAll("\"", "");
                    String documents_limit = offerObject.get("documents_limit").toString().replaceAll("\"", "");
                    String title_size = offerObject.get("title_size").toString().replaceAll("\"", "");
                    String short_description_size = offerObject.get("short_description_size").toString().replaceAll("\"", "");
                    String description_size = offerObject.get("description_size").toString().replaceAll("\"", "");
                    String description_images_limit = offerObject.get("description_images_limit").toString().replaceAll("\"", "");
                    String meta_title_size = offerObject.get("meta_title_size").toString().replaceAll("\"", "");
                    String meta_description_size = offerObject.get("meta_description_size").toString().replaceAll("\"", "");
                    String meta_keywords_limit = offerObject.get("meta_keywords_limit").toString().replaceAll("\"", "");
                    String keywords_limit = offerObject.get("keywords_limit").toString().replaceAll("\"", "");
                    String timezone = offerObject.get("timezone").toString().replaceAll("\"", "");
                    String custom_1 = offerObject.get("custom_1").toString().replaceAll("\"", "");
                    String custom_1_allow = offerObject.get("custom_1_allow").toString().replaceAll("\"", "");
                    String banner_limit_1 = offerObject.get("banner_limit_1").toString().replaceAll("\"", "");
                    String banner_limit_2 = offerObject.get("banner_limit_2").toString().replaceAll("\"", "");
                    String banner_limit_3 = offerObject.get("banner_limit_3").toString().replaceAll("\"", "");
                    String home_page = offerObject.get("home_page").toString().replaceAll("\"", "");
                    String listing_token = offerObject.get("listing_token").toString().replaceAll("\"", "");
                    String unconfirmed_acc = offerObject.get("unconfirmed_acc").toString().replaceAll("\"", "");
                    String login = offerObject.get("login").toString().replaceAll("\"", "");
                    String images_count = offerObject.get("images_count").toString().replaceAll("\"", "");
                    Log.d(TAG, "images: "+images_count);
                    JsonArray images=offerObject.getAsJsonArray("images");
                    Log.d(TAG, "onResponse: "+images);
                    for (int i = 0; i < images.size(); i++) {
                        JsonObject images1 = images.get(0).getAsJsonObject();
                        String id_image = images1.get("id").toString().replaceAll("\"", "");
                        String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String title_image = images1.get("title").toString().replaceAll("\"", "");
                        String date_image = images1.get("date").toString().replaceAll("\"", "");
                        String ordering_image = images1.get("ordering").toString().replaceAll("\"", "");
                        String description_image = images1.get("description").toString().replaceAll("\"", "");
                        String extension = images1.get("extension").toString().replaceAll("\"", "");
                        String thumb = images1.get("thumb").toString().replaceAll("\"", "");
                        String image = images1.get("image").toString().replaceAll("\"", "");

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("login", title_image);
                        hashMap.put("description", description_image);
                        hashMap.put("id", id_image);
                        hashMap.put("logo_url", image);
                        list_names.add(hashMap);
                        Log.d(TAG, "Image: " + image);
                        // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                    }





                    AdapterSearch adapterSearch = new AdapterSearch(Search_business.this, list_names);
                    listView.setAdapter(adapterSearch);
                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(Search_business.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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



                params.put("listing_id", getIntent().getStringExtra("code"));
                params.put("view", globalClass.list);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}