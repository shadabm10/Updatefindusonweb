package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.Pojo.ItemAge;
import sketch.findusonweb.Pojo.ItemCity;
import sketch.findusonweb.Pojo.ItemData;
import sketch.findusonweb.Pojo.ItemYear;

import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 8/5/18.
 */

public class Register extends AppCompatActivity{
    LinearLayout li;
    String TAG = "login";
    GlobalClass globalClass;
    String city_id,cat_id;
    TextView login_navigate, register;

    Shared_Preference prefrence;

    EditText username, email, pin_code, repeat_email, phone, password, repeat_password, business_name, owner_firstname, owner_lastname;
    ImageView down_arrow, country_spinner, image_username, img_email, img_repeat_email, img_phone, img_pass, img_repass, img_business, img_business_first, img_business_last;
    Spinner sp, Sp1;
    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    ArrayList<HashMap<String, String>> selectedLocation = new ArrayList<>();
    ArrayAdapter<String> dataAdapter1, dataAdapter7, dataAdapter;
    Spinner spinner_select_category, spinner_city;
    ProgressDialog pd;
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Register.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Register.this);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                Intent intent = new Intent(Register.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toasty.info(Register.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }



        pin_code = findViewById(R.id.pin_code_edit);
        login_navigate = findViewById(R.id.login);
        img_email = findViewById(R.id.img_email);
        img_phone = findViewById(R.id.img_phone);
        img_repeat_email = findViewById(R.id.img_repeat_email);
        img_pass = findViewById(R.id.img_pass);
        img_repass = findViewById(R.id.img_re_pass);
        img_business = findViewById(R.id.img_business_name);
        img_business_first = findViewById(R.id.img_business_first);
        img_business_last = findViewById(R.id.img_business_last);
        email = findViewById(R.id.email_edit);
        repeat_email = findViewById(R.id.repeat_email_edit);
        phone = findViewById(R.id.phone_edit);
        password = findViewById(R.id.password_edit);
        repeat_password = findViewById(R.id.re_password_edit);
        business_name = findViewById(R.id.business_name_edit);
        owner_firstname = findViewById(R.id.business_first_edit);
        owner_lastname = findViewById(R.id.business_last_edit);

        register = findViewById(R.id.tv_register);
        username = findViewById(R.id.username_edit);
        image_username = findViewById(R.id.img_username);
        // age_spinner=findViewById(R.id.down_arrow_age);
        sp = findViewById(R.id.spinner);
        country_spinner = findViewById(R.id.down_arrow1);
        // year_spinner=findViewById(R.id.down_arrow_year);
        // age_spinner=findViewById(R.id.down_arrow_age);
        Sp1 = findViewById(R.id.spinner1);
        spinner_select_category = findViewById(R.id.spinner_category);
        spinner_city = findViewById(R.id.spinner1);



        login_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });
        if (globalClass.isNetworkAvailable()) {


            getCategory(globalClass.Category);
            getLocation(globalClass.Location);

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }

        // li=findViewById(R.id.rl_email);

        down_arrow = findViewById(R.id.down_arrow_category);

        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_select_category.performClick();
            }
        });
        country_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sp1.performClick();
            }
        });

             spinner_select_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View arg1, int position, long arg3) {
                        // TODO Auto-generated method stub
                        // Locate the textviews in activity_main.xml
                        String item = parent.getItemAtPosition(position).toString();
                        Spinner spinner_select_category = (Spinner) parent;
                        // Set the text followed by the position
                        if (spinner_select_category.getId() == R.id.spinner_category) {



                            if (position != 0) {
                                cat_id = selectedCategory.get(position - 1).get("id");
                               String str_service = selectedCategory.get(position - 1).get("name");

                                Log.d(TAG, "onItemSelected: "+cat_id);


                            }

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                String item = parent.getItemAtPosition(position).toString();
                Spinner spinner_city = (Spinner) parent;
                // Set the text followed by the position
                if (spinner_city.getId() == R.id.spinner1) {



                    if (position != 0) {
                         city_id = selectedLocation.get(position - 1).get("id");
                        String str_service = selectedLocation.get(position - 1).get("name");

                        Log.d(TAG, "onItemSelected: "+city_id);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!email.getText().toString().matches(repeat_email.getText().toString())) {
                    phone.clearFocus();
                    repeat_email.requestFocus();
                    Toasty.info(Register.this, getResources().getString(R.string.email_mismatch), Toast.LENGTH_SHORT).show();
                }/*else {
                    phone.requestFocus();


                }*/
            }
        });
        business_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!password.getText().toString().matches(repeat_password.getText().toString())) {
                    business_name.clearFocus();
                    repeat_password.requestFocus();
                    Toasty.info(Register.this, getResources().getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
                }/*else {
                    business_name.requestFocus();

                }*/
            }
        });

        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String username_new = username.getText().toString().trim();
                String email_new = email.getText().toString().trim();
                String Pin_Code = pin_code.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String organization = business_name.getText().toString().trim();
                String business_first_name = owner_firstname.getText().toString().trim();
                String business_last_name = owner_lastname.getText().toString().trim();


                if (globalClass.isNetworkAvailable()) {
                    if (!username.getText().toString().isEmpty()) {
                        if (!email.getText().toString().isEmpty()) {
                            if (isValidEmail(email.getText().toString())) {
                                if (!phone.getText().toString().isEmpty()) {
                                    if (!password.getText().toString().isEmpty()) {
                                        if (!repeat_password.getText().toString().isEmpty()) {
                                            if (!business_name.getText().toString().isEmpty()) {
                                                if (!owner_firstname.getText().toString().isEmpty()) {
                                                    if (!owner_lastname.getText().toString().isEmpty()) {
                                                        if(!pin_code.getText().toString().isEmpty()){
                                                            checkLogin(username_new, email_new, Phone, Password, organization, business_first_name, business_last_name, Pin_Code, globalClass.RegisterView,city_id,cat_id);

                                                        }
                                                        else {
                                                            Toasty.error(Register.this, getResources().getString(R.string.enter_pin), Toast.LENGTH_SHORT, true).show();

                                                        }
                                                    } else {
                                                        Toasty.error(Register.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT, true).show();
                                                    }
                                                } else {
                                                    Toasty.info(Register.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT, true).show();
                                                }
                                            } else {
                                                Toasty.warning(Register.this, getResources().getString(R.string.enter_business_name), Toast.LENGTH_SHORT, true).show();
                                            }
                                        } else {
                                            Toasty.warning(Register.this, getResources().getString(R.string.enter_confirm_password), Toast.LENGTH_SHORT, true).show();
                                        }
                                    } else {
                                        Toasty.warning(Register.this, getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT, true).show();
                                    }
                                } else {
                                    Toasty.warning(Register.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT, true).show();
                                }
                            } else {
                                Toasty.warning(Register.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(Register.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                        }
                    } else {
                        Toasty.warning(Register.this, getResources().getString(R.string.enter_user_name), Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(Register.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                }
            }
        });


    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkLogin(final String username, final String user_email, final String phone_number, final String password, final String user_organizaton, final String user_first_name, final String user_last_name, final String user_pin, final String view,final String cat_id,final String city_id) {
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

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String user_id =data.get("user_id").toString().replaceAll("\"", "");
                        String login=data.get("login").toString().replaceAll("\"", "");
                        String user_first_name=data.get("user_first_name").toString().replaceAll("\"", "");
                        String user_last_name=data.get("user_last_name").toString().replaceAll("\"", "");
                        String user_organization=data.get("user_organization").toString().replaceAll("\"", "");
                        String user_phone=data.get("user_phone").toString().replaceAll("\"", "");

                        globalClass.setId(user_id);
                        globalClass.setName(login);
                        globalClass.setFname(user_first_name);
                        globalClass.setLname(user_last_name);
                        globalClass.setPhone_number(user_phone);
                        globalClass.setOrgazination(user_organization);
                        globalClass.setLogin_status(true);

                        prefrence.savePrefrence();
                        Log.d(TAG, "onResponse: "+cat_id);
                        Log.d(TAG, "onResponse: "+city_id);
                        Toasty.success(Register.this, message, Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(Register.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                        pd.dismiss();

                    } else


                        {


                        Toasty.success(Register.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(Register.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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

                params.put("username", username);
                params.put("password", password);
                params.put("user_email", user_email);
                params.put("user_first_name", user_first_name);
                params.put("user_last_name", user_last_name);
                params.put("user_organization", user_organizaton);
                params.put("user_zip", user_pin);
                params.put("user_phone", phone_number);
                params.put("category_id",cat_id);
                params.put("location_id",city_id);

                params.put("view", view);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void getCategory(final String Category)
    {
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Category " + response.toString());



                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);

                    Log.d("jobj", "" + jobj);

                    JsonObject offerObject = jobj.getAsJsonObject();
                    category.add("Select Category");
                        JsonArray jarray=offerObject.getAsJsonArray("name");
                    Log.d("jarray", "" + jarray.toString());
                    ArrayList<String> array = new ArrayList<>();
                        for (int i = 0; i < jarray.size(); i++) {
                            JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            //get the object

                            //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            String id =jobj1.get("id").toString().replaceAll("\"", "");
                            String title= jobj1.get("title").toString().replaceAll("\"", "");
                            HashMap<String, String> map_ser = new HashMap<>();


                            map_ser.put("id", id);
                            map_ser.put("title", title);

                            selectedCategory.add(map_ser);
                            array.add(title);

                            Log.d("title", "" + array);

                            dataAdapter1 = new ArrayAdapter<String>(Register.this,R.layout.spinner_layout,R.id.txt,array)
                            {

                                @Override
                                public boolean isEnabled(int position) {
                                    return position != 0;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                    View view = super.getDropDownView(position, convertView, parent);

                                    if (position == 0) {



                                    } else {
                                    }
                                    return view;


                                }
                            };
                           // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                            dataAdapter1.setDropDownViewResource(R.layout.spinner_layout);
                            spinner_select_category.setAdapter(dataAdapter1);


                            pd.dismiss();

                        }


                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"data Not found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    pd.dismiss();

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



                params.put("view",Category);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
      private void getLocation(final String Location_new)
      {
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Category " + response.toString());



                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);

                    Log.d("jobj", "" + jobj);

                    JsonObject offerObject = jobj.getAsJsonObject();
                    location.add("Select Country");
                    JsonArray jarray=offerObject.getAsJsonArray("name");
                    Log.d("jarray", "" + jarray.toString());
                    ArrayList<String> array = new ArrayList<>();

                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        //get the object
                        //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        String id =jobj1.get("id").toString().replaceAll("\"", "");
                        String title= jobj1.get("title").toString().replaceAll("\"", "");

                        HashMap<String, String> map_ser = new HashMap<>();


                        map_ser.put("id", id);
                        map_ser.put("title", title);

                        selectedLocation.add(map_ser);
                        array.add(title);

                        Log.d("title", "" + location);
                        dataAdapter7 = new ArrayAdapter<String>(Register.this,R.layout.spinner_layout,R.id.txt,array)
                        {

                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View view = super.getDropDownView(position, convertView, parent);


                                if (position == 0) {

                                    // tv.setTextColor(getResources().getColor(R.color.white));

                                } else {
                                    // tv.setTextColor(Color.WHITE);
                                }
                                return view;


                            }
                        };
                        // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                        dataAdapter7.setDropDownViewResource(R.layout.spinner_layout);
                        spinner_city.setAdapter(dataAdapter7);


                        pd.dismiss();

                    }


                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"data Not found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    pd.dismiss();

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



                params.put("view",Location_new);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }




}