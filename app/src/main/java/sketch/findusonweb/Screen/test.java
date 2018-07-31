package sketch.findusonweb.Screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import sketch.findusonweb.Controller.AppController;
import sketch.findusonweb.R;

/**
 * Created by developer on 10/5/18.
 */

public class test extends AppCompatActivity {
    public static final String TAG = test.class.getSimpleName();
    public String AppConfig="http://lab-3.sketchdemos.com/P-1081-drinkdrop/web-service/owner-receive-order-list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_new);
        checkLogin();
    }
    private void checkLogin() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
              //  hideDialog();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);


                    String status = jobj.get("status").getAsString();
                    Log.d(TAG, "status" + status);
                    String message = jobj.get("message").getAsString();
                    Log.d(TAG, "message" + message);



                    JsonArray jarray = jobj.getAsJsonArray("data");

                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();

                        String order_id = jobj1.get("order_id").getAsString();
                        Log.d("TAG", "Order Id" + order_id);

                        String order_status = jobj1.get("order_status").getAsString();
                        String order_date = jobj1.get("order_date").getAsString();

                        String payment_type = jobj1.get("payment_type").getAsString();

                        String user_id = jobj1.get("user_id").getAsString();
                        String sl = jobj1.get("sl").getAsString();
                        String grant_total = jobj1.get("grant_total").getAsString();

                        Log.d("TAG", "Name of Cluster" + order_status);
                        JsonObject offerObject = jobj1.getAsJsonObject("user");
                        String fname = offerObject.get("fname").getAsString();
                        String lname = offerObject.get("lname").getAsString();
                        String emailid = offerObject.get("emailid").getAsString();
                        Log.d("TAG", "name" + fname);

                        JsonArray jarray_new = jobj1.getAsJsonArray("product");


                            JsonObject jobj11 = jarray_new.getAsJsonObject();

                            String order_id1 = jobj11.get("order_id").getAsString();
                            Log.d("TAG", "Order Id" + order_id);

                            String product_id = jobj11.get("product_id").getAsString();
                            String product_quantity = jobj1.get("product_quantity").getAsString();

                            String product_price = jobj11.get("product_price").getAsString();
                            String product_name = jobj11.get("product_name").getAsString();
                            String productId = jobj11.get("productId").getAsString();

                            String user_ida = jobj1.get("user_id").getAsString();
                            String sl1 = jobj1.get("sl").getAsString();

                            Log.d("TAG", "productId" + productId);

                        }







                    Toast.makeText(getApplicationContext(), "Successfully Access", Toast.LENGTH_SHORT).show();





                }catch (Exception e) {

                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
              //  Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
               // hideDialog();
            }
        }) {


        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}