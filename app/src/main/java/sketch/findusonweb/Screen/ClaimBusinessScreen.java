package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import sketch.findusonweb.Adapter.AdapterClaim;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 5/7/18.
 */

public class ClaimBusinessScreen extends AppCompatActivity {

    ListView claim_business_lv;
    String TAG = "claim";
    TextView back_img,tv_submit;

    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    String id,listing_name,location;
    ProgressDialog pd;
    EditText edit_search_by_business,edt_comment,edt_location,edt_list_name,edt_username,edit_phoneno,edit_mail;
    LinearLayout ll_search;
    ArrayList<HashMap<String,String>> list_claim;
    AdapterClaim adapterClaim;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_business);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ClaimBusinessScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ClaimBusinessScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_claim = new ArrayList<>();
        tv_submit=findViewById(R.id.tv_submit);
        edit_search_by_business = findViewById(R.id.edit_search_by_business);
        ll_search = findViewById(R.id.ll_search);
        claim_business_lv = findViewById(R.id.claim_business);
        edt_list_name=findViewById(R.id.edt_listingname);
        edt_username=findViewById(R.id.edt_requestername);
        edit_mail=findViewById(R.id.edt_email);
        edit_phoneno=findViewById(R.id.edt_phn);
        edt_comment=findViewById(R.id.edt_comment);
        edt_location=findViewById(R.id.edt_location);
        edt_list_name.setEnabled(false);
        edt_username.setEnabled(false);
        edit_mail.setEnabled(false);
        edit_phoneno.setEnabled(false);
        edt_location.setEnabled(false);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String business_name_text = edt_comment.getText().toString();
                claimBusiness(business_name_text);
            }
        });
        id = getIntent().getStringExtra("id");
        listing_name=getIntent().getStringExtra("title");
        location=getIntent().getStringExtra("location_search_text");
        edit_mail.setText(globalClass.getEmail());
        edit_phoneno.setText(globalClass.getPhone_number());
        edt_username.setText(globalClass.getName());
        edt_list_name.setText(listing_name);
        edt_location.setText(location);

    }
    private void claimBusiness(final String business_name_text) {
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
                   // JsonArray data = jobj.getAsJsonArray("data");

                    String success = jobj.get("success").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: " + message);


                    //  list_claim.add(hashMap);
                    Log.d(TAG, "id: " + id);
                    Toasty.success(ClaimBusinessScreen.this, message, Toast.LENGTH_SHORT, true).show();





                    /*} else


                    {


                        Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }
*/
                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    // Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(ClaimBusinessScreen.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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

                params.put("view","claim_submit");
                params.put("user_id",globalClass.getId());
                params.put("listing_id",id);
                params.put("comments",business_name_text);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}