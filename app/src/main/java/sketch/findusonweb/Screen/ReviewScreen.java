package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;

/**
 * Created by developer on 6/6/18.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;

import android.graphics.drawable.LayerDrawable;

import android.util.Log;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;




import java.util.HashMap;
import java.util.Map;


import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Utils.Shared_Preference;


/**
 *
 */
public class ReviewScreen extends AppCompatActivity {
    String TAG = "Review";
    RatingBar ratingBar,features_rating,performance_rating,value_rating,reliablity_rating;
    TextView review_note,save_review,back;
    EditText title_edt;
    String ratedValue;
    ProgressDialog pd;
    Shared_Preference prefrence;
    String id;

    ProgressDialog progressBar;
    GlobalClass global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_screen);

        pd = new ProgressDialog(ReviewScreen.this);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        back=findViewById(R.id.back_img);
        ratingBar =  findViewById(R.id.rating);
        title_edt=findViewById(R.id.title_review);
        features_rating =  findViewById(R.id.features_ratingbar);
        performance_rating =  findViewById(R.id.performance_ratingbar);
        reliablity_rating =  findViewById(R.id.reliablity_rating);
        value_rating =  findViewById(R.id.value_ratingbar);
        review_note =  findViewById(R.id.review_note);

        save_review=findViewById(R.id.save_review);
        global = (GlobalClass) this.getApplication();
       id = getIntent().getStringExtra("id");

        Log.d(TAG, "id: "+id);

        progressBar=new ProgressDialog(this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setTitle(getResources().getString(R.string.plz_wait));





        save_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if ((ratingBar.getRating() == 0)||(features_rating.getRating() == 0)||
                        (performance_rating.getRating() == 0)||
                        (reliablity_rating.getRating() == 0)||
                        (value_rating.getRating() == 0)){

                    Toasty.info(ReviewScreen.this, getResources().getString(R.string.Put_rate), Toast.LENGTH_LONG, true).show();

                }else if (review_note.getText().toString().length() == 0){

                    Toasty.info(ReviewScreen.this,getResources().getString(R.string.Enter_your_review) , Toast.LENGTH_LONG,  true).show();

                }else {

                    String rate = String.valueOf(ratingBar.getRating());
                    String features_rating1 = String.valueOf(features_rating.getRating());
                    String performance_rating1 = String.valueOf(performance_rating.getRating());
                    String reliablity_rating1 = String.valueOf(reliablity_rating.getRating());
                    String value_rating1 = String.valueOf(value_rating.getRating());
                    String note = review_note.getText().toString();
                    String title = title_edt.getText().toString();


                    if (global.isNetworkAvailable()){


                        rating_review(title,rate,features_rating1,performance_rating1,
                                reliablity_rating1,value_rating1,note);

                    }else {

                        Toasty.warning(ReviewScreen.this,getResources().getString(R.string.check_internet),Toast.LENGTH_LONG, true).show();

                    }



                }

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = String.valueOf(ratingBar.getRating());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

            }
        });
        features_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = String.valueOf(ratingBar.getRating());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

            }
        });

        performance_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = String.valueOf(ratingBar.getRating());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

            }
        });

        reliablity_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = String.valueOf(ratingBar.getRating());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

            }
        });

        value_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {


            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratedValue = String.valueOf(ratingBar.getRating());
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



    public void rating_review(final String title, final String rate,final String features_rating,
                              final String performance_rating,final String reliablity_rating,
                              final String value_rating,final String note ){

        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Review URl " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String user_id =data.get("user_id").toString().replaceAll("\"", "");
                        String listing_id=data.get("listing_id").toString().replaceAll("\"", "");
                        String title=data.get("title").toString().replaceAll("\"", "");
                        String review=data.get("review").toString().replaceAll("\"", "");
                        String rating=data.get("rating").toString().replaceAll("\"", "");
                        String category_1=data.get("category_1").toString().replaceAll("\"", "");
                        String category_2=data.get("category_2").toString().replaceAll("\"", "");
                        String category_3=data.get("category_3").toString().replaceAll("\"", "");
                        String category_4=data.get("category_4").toString().replaceAll("\"", "");
                        String review_id=data.get("review_id").toString().replaceAll("\"", "");
/*
                        global.setId(user_id);


                        prefrence.savePrefrence();*/

                        Toasty.success(ReviewScreen.this, message, Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(ReviewScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                        pd.dismiss();

                    } else


                    {


                        Toasty.success(ReviewScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(ReviewScreen.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Network Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", global.getId());

                params.put("listing_id", getIntent().getStringExtra("id"));
                params.put("title", title);
                params.put("review", note);
                params.put("rating", rate);
                params.put("category_2", features_rating);
                params.put("category_3", performance_rating);
                params.put("category_4",reliablity_rating);
                params.put("category_1",value_rating);
                params.put("view", "send_review");
                Log.d(TAG, "getParams: "+params.toString());
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    public void popup_verification(final Context reg ){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(reg);
        builder1.setMessage(getResources().getString(R.string.Your_rating_reviews_are_successfully_submitted_));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     /*   Intent intent= new Intent(Rate_it.this, MainActivity.class);
                        startActivity(intent);*/
                        dialog.dismiss();
                        finish();

                    }
                });

     /*   builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    public void onBackPressed() {

        finish();
    }
}



