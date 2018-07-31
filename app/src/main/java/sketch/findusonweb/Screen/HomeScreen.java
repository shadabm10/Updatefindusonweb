package sketch.findusonweb.Screen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 7/5/18.
 */

public class HomeScreen  extends AppCompatActivity {
    LinearLayout refer_friend_layout,get_quote_layout;
    String TAG = "login";
    ImageView dialog_cut,profile_img,toolbar_drawer;
    GlobalClass globalClass;
    ProgressDialog pd;

    EditText search_by_code,search_by_business,search_by_product;
    public static final String PREFS_NAME = "MyPrefsFile";

    Button search_button;
    Shared_Preference prefrence;
    Dialog dialog;
    TextView save,submit,tv_browse;
    LinearLayout post_job_layout;
    EditText firstname_dialog,lastname_dialog,email_dialog,business_name,phone_dialog,campaign_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        tv_browse=findViewById(R.id.tv_browse);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(HomeScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(HomeScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(HomeScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        refer_friend_layout=findViewById(R.id.refer_friend_layout);
        dialog_cut=findViewById(R.id.cut_dialog);
        profile_img=findViewById(R.id.profile_img);
        toolbar_drawer=findViewById(R.id.toolbar_drawer);

        refer_friend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    openDialog1();
                }
            }
        });
        search_by_code=findViewById(R.id.search_by_code);
        search_by_business=findViewById(R.id.search_by_business);
        search_by_product=findViewById(R.id.search_by_product);
        get_quote_layout=findViewById(R.id.get_quote_layout);

        search_button=findViewById(R.id.search_button);
        post_job_layout=findViewById(R.id.post_job_layout);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String search = search_by_code.getText().toString().trim();
                if (!search_by_code.getText().toString().isEmpty()||!search_by_business.getText().toString().isEmpty()||!search_by_product.getText().toString().isEmpty()) {

                   Intent intent =new Intent(HomeScreen.this,SearchListing.class);
                   intent.putExtra("product",search_by_product.getText().toString());
                   intent.putExtra("keyword",search_by_business.getText().toString());
                   intent.putExtra("location",search_by_code.getText().toString());

                    startActivity(intent);
                    search_by_code.setText("");
                    search_by_business.setText("");
                    search_by_product.setText("");


                }else {

                    Intent intent =new Intent(HomeScreen.this,SearchListing.class);
                    intent.putExtra("product",search_by_product.getText().toString());
                    intent.putExtra("keyword",search_by_business.getText().toString());
                    intent.putExtra("location",search_by_code.getText().toString());

                    startActivity(intent);

                }
            }
        });
        tv_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HomeScreen.this,BrowseJobCategory.class);
                startActivity(intent);
            }
        });
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyProfile.class));

            }
        });

        post_job_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalClass.login_status.equals(false)) {

                    Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(HomeScreen.this, PostRequriementScreen.class);
                    startActivity(intent);
                }
            }
        });

        get_quote_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalClass.login_status.equals(false)) {

                    Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(HomeScreen.this, QuotesOfferScreen.class);
                    startActivity(intent);
                }
            }
        });

        toolbar_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(HomeScreen.this, DashboardScreen.class);
                    startActivity(intent);
                }
            }
        });

    }
    public void openDialog1() {
         dialog = new Dialog(HomeScreen.this);
        dialog.setContentView(R.layout.invite_dialog);
        dialog_cut=dialog.findViewById(R.id.cut_dialog);
        firstname_dialog=dialog.findViewById(R.id.firstname);
        lastname_dialog=dialog.findViewById(R.id.lastname);
        email_dialog=dialog.findViewById(R.id.email);
        phone_dialog=dialog.findViewById(R.id.phoone_no);
        campaign_id=dialog.findViewById(R.id.campaign_id);
        business_name=dialog.findViewById(R.id.business_name);
        save=dialog.findViewById(R.id.save);
        submit=dialog.findViewById(R.id.tv_submit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstname_dialog.getText().toString().trim();
                String lastname = lastname_dialog.getText().toString().trim();
                String email = email_dialog.getText().toString().trim();
                String Phone = phone_dialog.getText().toString().trim();
                String organization = business_name.getText().toString().trim();
                String campaign_id1 = campaign_id.getText().toString().trim();

                if (globalClass.isNetworkAvailable()) {
                    if (!firstname_dialog.getText().toString().isEmpty()) {
                        if (!lastname_dialog.getText().toString().isEmpty()) {
                            if (isValidEmail(email_dialog.getText().toString())) {
                                if (!phone_dialog.getText().toString().isEmpty()) {
                                    if (!business_name.getText().toString().isEmpty()) {
                                        if (!campaign_id.getText().toString().isEmpty()) {

                                            inviteFriend(firstname,lastname,email,organization,Phone,campaign_id1,globalClass.view_friend);

                                                }
                                                else {
                                            Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_campaign_id), Toast.LENGTH_SHORT, true).show();

                                        }
                                            } else {
                                                Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT, true).show();
                                            }
                                        }
                                    } else {
                                        Toasty.warning(HomeScreen.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                                    }
                                } else {
                                    Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT, true).show();
                                }
                            } else {
                                Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(HomeScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                        }
                    }



        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstname_dialog.getText().toString().trim();
                String lastname = lastname_dialog.getText().toString().trim();
                String email = email_dialog.getText().toString().trim();
                String Phone = phone_dialog.getText().toString().trim();
                String organization = business_name.getText().toString().trim();
                String campaign_id1 = campaign_id.getText().toString().trim();

                if (globalClass.isNetworkAvailable()) {
                    if (!firstname_dialog.getText().toString().isEmpty()) {
                        if (!lastname_dialog.getText().toString().isEmpty()) {
                            if (isValidEmail(email_dialog.getText().toString())) {
                                if (!phone_dialog.getText().toString().isEmpty()) {
                                    if (!business_name.getText().toString().isEmpty()) {
                                        if (!campaign_id.getText().toString().isEmpty()) {

                                           submitFriend(firstname,lastname,email,organization,Phone,campaign_id1,globalClass.view_friend);

                                        }
                                        else {
                                            Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_campaign_id), Toast.LENGTH_SHORT, true).show();

                                        }
                                    } else {
                                        Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            } else {
                                Toasty.warning(HomeScreen.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT, true).show();
                        }
                    } else {
                        Toasty.warning(HomeScreen.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(HomeScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                }
            }



        });

        dialog_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void inviteFriend(final String user_first_name, final String user_last_name, final String user_email,  final String user_organizaton, final String phone_number, final String campaign_source_field, final String view) {
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
                    String result = jobj.get("result").getAsString().replaceAll("\"", "");
                    String message = jobj.get("msg").getAsString().replaceAll("\"", "");
                   // JsonObject offerObject = jobj.getAsJsonObject("result");

                    if (result.equals("success")) {


                        Toasty.success(HomeScreen.this, message, Toast.LENGTH_SHORT, true).show();
                       // Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                       // startActivity(intent);
                       // finish();
                        dialog.dismiss();

                    } else


                    {


                        Toasty.success(HomeScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(HomeScreen.this, "Something went wrong", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_first_name", user_first_name);
                params.put("user_last_name", user_last_name);
                params.put("user_email", user_email);
                params.put("user_organization", user_organizaton);
                params.put("user_phone", phone_number);
                params.put("status", globalClass.Status_new);
                params.put("campaign_source_field",campaign_source_field);
                params.put("refer_user_id",globalClass.getId());
                params.put("view", view);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void submitFriend(final String user_first_name, final String user_last_name, final String user_email,  final String user_organizaton, final String phone_number, final String campaign_source_field, final String view) {
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
                    String result = jobj.get("result").getAsString().replaceAll("\"", "");
                    String message = jobj.get("msg").getAsString().replaceAll("\"", "");
                    // JsonObject offerObject = jobj.getAsJsonObject("result");

                    if (result.equals("success")) {


                        Toasty.success(HomeScreen.this, message, Toast.LENGTH_SHORT, true).show();
                        // Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                        // startActivity(intent);
                        // finish();
                        dialog.dismiss();

                    } else


                    {


                        Toasty.success(HomeScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(HomeScreen.this, "Something went wrong", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_first_name", user_first_name);
                params.put("user_last_name", user_last_name);
                params.put("user_email", user_email);
                params.put("user_organization", user_organizaton);
                params.put("user_phone", phone_number);
                params.put("campaign_source_field",campaign_source_field);
                params.put("refer_user_id",globalClass.getId());
                params.put("view", view);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}