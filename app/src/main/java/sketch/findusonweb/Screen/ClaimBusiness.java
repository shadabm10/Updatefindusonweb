package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import sketch.findusonweb.Adapter.BrowseProductAdapter;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 2/7/18.
 */

public class ClaimBusiness extends AppCompatActivity {

    ListView claim_business_lv;
    String TAG = "claim";
    TextView back_img;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    ProgressDialog pd;
    EditText edit_search_by_business;
    LinearLayout ll_search;
    ArrayList<HashMap<String,String>> list_claim;
    AdapterClaim adapterClaim;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claimbusiness);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ClaimBusiness.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ClaimBusiness.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_claim = new ArrayList<>();

        edit_search_by_business = findViewById(R.id.edit_search_by_business);
        ll_search = findViewById(R.id.ll_search);
        claim_business_lv = findViewById(R.id.claim_business);

        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String business_name_text = edit_search_by_business.getText().toString();
                claimBusiness(business_name_text);
            }
        });


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
                    JsonArray data=jobj.getAsJsonArray("data");

                        for (int i = 0; i < data.size(); i++) {
                            JsonObject obj_data = data.get(i).getAsJsonObject();
                            String id = obj_data.get("id").toString().replaceAll("\"", "");
                            String title = obj_data.get("title").toString().replaceAll("\"", "");
                            String rating = obj_data.get("rating").toString().replaceAll("\"", "");
                            String logo_extension = obj_data.get("logo_extension").toString().replaceAll("\"", "");
                            String location_search_text = obj_data.get("location_search_text").toString().replaceAll("\"", "");
                            String logo_url = obj_data.get("logo_url").toString().replaceAll("\"", "");



                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("title", title);
                            hashMap.put("rating", rating);
                            hashMap.put("logo_extension", logo_extension);
                            hashMap.put("location_search_text", location_search_text);
                            hashMap.put("logo_url", logo_url);


                            list_claim.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterClaim = new AdapterClaim(ClaimBusiness.this, list_claim);
                        claim_business_lv.setAdapter(adapterClaim);


                    /*} else


                    {


                        Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }
*/
                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                   // Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(ClaimBusiness.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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

                params.put("view","claimBusiness");
                params.put("business_name",business_name_text);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}