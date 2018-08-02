package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.Adapter_invoice;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class EditReview  extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    Adapter_invoice adapter_invoice;
    TextView back_img,review_available,review_edit_note,submit_review;
    ProgressDialog pd;
    RatingBar value_ratingbar;
    String id,review,title_name,review_name,ranking;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);
        review_available=findViewById(R.id.review_available);
        review_edit_note=findViewById(R.id.review_edit_note);
        value_ratingbar=findViewById(R.id.value_ratingbar);
        submit_review=findViewById(R.id.submit_review);
        back_img=findViewById(R.id.back_img);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(EditReview.this);
        prefrence.loadPrefrence();
        id = getIntent().getExtras().getString("id");
        LayerDrawable stars = (LayerDrawable) value_ratingbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getApplication().getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        pd = new ProgressDialog(EditReview.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(EditReview.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        ReviewList();
        list_namesfavoriteAll=new ArrayList<>();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_name  = review_available.getText().toString().trim();
                review_name  = review_edit_note.getText().toString().trim();
                ranking  = String.valueOf(value_ratingbar.getRating());

                SubmitEditedReview(title_name,review_name,ranking);
            }
        });
    }

    private void SubmitEditedReview(final String title, final String review,final String rating) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("success").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);
                    if(status.equals("1")){
                        Toasty.success(EditReview.this, message, Toast.LENGTH_SHORT, true).show();

                    }



                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("title", title);
                params.put("id", id);
                params.put("view","editReview");
                params.put("review",review);
                params.put("rating",rating);


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

        pd.show();

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
                      //  JsonArray data = jobj.getAsJsonArray("data");

                        JsonObject images1=jobj.getAsJsonObject("data");                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String rating_id = images1.get("rating_id").toString().replaceAll("\"", "");
                            String comment_count = images1.get("comment_count").toString().replaceAll("\"", "");
                            String helpful_count = images1.get("helpful_count").toString().replaceAll("\"", "");
                            String helpful_total = images1.get("helpful_total").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");

                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                             review = images1.get("review").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String rating = images1.get("rating").toString().replaceAll("\"", "");
                            String category_1 = images1.get("category_1").toString().replaceAll("\"", "");
                            String category_2 = images1.get("category_2").toString().replaceAll("\"", "");
                            String category_3 = images1.get("category_3").toString().replaceAll("\"", "");
                            String category_4 = images1.get("category_4").toString().replaceAll("\"", "");
                        JsonElement nullableText = images1.get("review");
                        String text = (nullableText instanceof JsonNull) ? "" : nullableText.getAsString();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("rating_id", rating_id);
                            hashMap.put("comment_count", comment_count);
                            hashMap.put("helpful_count", helpful_count);
                            hashMap.put("helpful_total", helpful_total);
                            hashMap.put("name", name);
                            hashMap.put("status", status);
                            hashMap.put("title", title);
                            hashMap.put("review", review);
                            hashMap.put("sync", sync);
                            hashMap.put("rating", rating);
                            hashMap.put("date", date);
                            hashMap.put("category_1", category_1);
                            hashMap.put("category_2", category_2);
                            hashMap.put("category_3", category_3);
                            hashMap.put("category_4", category_4);
                            value_ratingbar.setRating(Float.parseFloat(rating));

                                    review_edit_note.setText(text);
                                    review_available.setText(title);
                                    review_edit_note.setText(review);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "review: " + text);


                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                     /*   adapter_invoice = new Adapter_invoice(EditReview.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);*/
                    }
                    else


                    {


                        Toasty.success(EditReview.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(EditReview.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("id",id );
                params.put("view", "getReviewById");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}