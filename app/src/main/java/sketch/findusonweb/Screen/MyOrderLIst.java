package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import sketch.findusonweb.Adapter.AdapterMyOrder;
import sketch.findusonweb.Adapter.Adapter_invite_friend;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class MyOrderLIst extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterMyOrder adapter_invoice;
    TextView back_img;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        listing=findViewById(R.id.my_order_list);
        back_img=findViewById(R.id.img_back);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(MyOrderLIst.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(MyOrderLIst.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(MyOrderLIst.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        ReviewList();
        list_namesfavoriteAll=new ArrayList<>();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
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
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String next_due_date = images1.get("next_due_date").toString().replaceAll("\"", "");
                            String type_id = images1.get("type_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String renewable = images1.get("renewable").toString().replaceAll("\"", "");
                            String product_status = images1.get("product_status").toString().replaceAll("\"", "");
                            String amount = images1.get("amount").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("order_id", order_id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("name", name);
                            hashMap.put("title", title);
                            hashMap.put("type", type);
                            hashMap.put("date", date);
                            hashMap.put("next_due_date", next_due_date);
                            hashMap.put("type_id", type_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("status", status);
                            hashMap.put("renewable", renewable);
                            hashMap.put("product_status", product_status);
                            hashMap.put("amount", amount);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new AdapterMyOrder(MyOrderLIst.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);
                    }
                    else


                    {


                        Toasty.success(MyOrderLIst.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(MyOrderLIst.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("user_id", globalClass.getId());
                params.put("view", "getMyOrders");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}