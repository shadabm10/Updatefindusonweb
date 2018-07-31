package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import sketch.findusonweb.R;


public class QuotesOfferScreen extends AppCompatActivity {
    ProgressDialog pd;
    ArrayList<String> category = new ArrayList<>();
    TextView image_back;
    EditText search;
    String TAG = "quotesnoffers";
    RadioGroup rg;
    GlobalClass globalClass;
    ArrayList<String> array;
    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    String id;
    String city_id,cat_id;

    RadioButton rb;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotesnoffers);

        globalClass = (GlobalClass) getApplicationContext();
        pd = new ProgressDialog(QuotesOfferScreen.this);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        search=findViewById(R.id.search);
        image_back=findViewById(R.id.back_img);
         rg=findViewById(R.id.radiogroup);
        array = new ArrayList<>();
       // rg = new RadioGroup(this); // create the RadioGroup
      //  rg.setOrientation(RadioGroup.HORIZONTAL);// or RadioGroup.VERTICAL
        if (globalClass.isNetworkAvailable()) {


            getCategory(globalClass.Category);
            // getLocation(globalClass.Location);

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    getCategory(search.getText().toString());

                }else{
                    Toasty.warning(QuotesOfferScreen.this, "Please check your internet connect", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

      image_back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for(int i=0; i<radioGroup.getChildCount(); i++) {
                    rb = (RadioButton) radioGroup.getChildAt(i);
                    if(rb.getId() == checkedId) {
                        String text = (String) rb.getText();
                        //search.setText(text);
                        String str_service = selectedCategory.get(i).get("id");

                        Log.d(TAG, "onCheckedChanged:id - "+text + " :" + rb.getId());
                        Log.d(TAG, "Category Id: "+str_service);
                       // Log.d(TAG, "onCheckedChanged:id - "+text );
                        // do something with text
                        return;
                    }
                }
            }
        });

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

                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        //get the object

                        //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                         id =jobj1.get("id").toString().replaceAll("\"", "");
                        String title= jobj1.get("title").toString().replaceAll("\"", "");
                        HashMap<String, String> map_ser = new HashMap<>();


                        map_ser.put("id", id);
                        map_ser.put("title", title);

                       // selectedCategory.add(map_ser);
                        selectedCategory.add(map_ser);
                        array.add(title);



                        rb = new RadioButton(QuotesOfferScreen.this);
                        rb.setText(title +"");
                        rg.addView(rb);

                        //rb.setLayoutParams(params);
                        }
                         Log.d("title", "" + array);
                        pd.dismiss();




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
}
