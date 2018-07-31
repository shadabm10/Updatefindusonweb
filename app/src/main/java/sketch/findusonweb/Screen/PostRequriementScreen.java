package sketch.findusonweb.Screen;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

import static android.content.ContentValues.TAG;

/**
 * Created by developer on 25/5/18.
 */

public class PostRequriementScreen extends AppCompatActivity {
    String TAG = "post requirement";
    Spinner spinner_days;
    TextView back_button,attach_data,attach_data_link,tv_submit;
    String city_id,cat_id;
    Shared_Preference prefrence;
    Spinner spinner_select_category;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    GlobalClass globalClass;
    ArrayAdapter<String> dataAdapter1;
    ProgressDialog pd;

    RadioGroup rg;
    RadioButton rb;
    ArrayList<String> array;
    ImageView down_arrow;
    int columnIndex;
    String id;
    File attachmentFile;
    EditText edt_description,edt_title,edt_duration,edt_budget;
    String title , description, duration, budget;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private static final int PICKFILE_RESULT_CODE = 1;

    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.post_job);
        spinner_select_category = findViewById(R.id.spinner_category);

        pd = new ProgressDialog(PostRequriementScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        rg=findViewById(R.id.radiogroup);
        array = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(PostRequriementScreen.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(PostRequriementScreen.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: ");
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){
                    Log.d(TAG, "onCreate: ");
                }

            }
        }


        attach_data=findViewById(R.id.attach_data);
        spinner_days=findViewById(R.id.spinner_days);
        attach_data_link=findViewById(R.id.attach_data_name);
        back_button=findViewById(R.id.back_img);
        down_arrow=findViewById(R.id.down_arraowpost);
        tv_submit=findViewById(R.id.tv_submit);
        edt_title=findViewById(R.id.edt_title);
        edt_description=findViewById(R.id.edt_description);
        edt_duration=findViewById(R.id.edt_duration);
        edt_budget=findViewById(R.id.edt_budget);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(PostRequriementScreen.this);
        prefrence.loadPrefrence();


        if (globalClass.isNetworkAvailable()) {

            getCategory(globalClass.Category);

          //  getCategory_list();
            // getLocation(globalClass.Location);

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }
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
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_select_category.performClick();
            }
        });



/*
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_days.performClick();
            }
        });
*/
/*
        spinner_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                String item = parent.getItemAtPosition(position).toString();
                Spinner spinner_days = (Spinner) parent;
                // Set the text followed by the position
                if (spinner_days.getId() == R.id.spinner_days) {


                    if (position != 0) {
                        cat_id = selectedCategory.get(position-1).get("id");
                        //String str_service = selectedCategory.get(position - 1).get("name");

                        Log.d(TAG, "onItemSelected: "+cat_id);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
*/
         attach_data.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openFolder();
             }
         });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_title.getText().toString();
                description = edt_description.getText().toString();
                duration = edt_duration.getText().toString();
                budget = edt_budget.getText().toString();
                Log.d(TAG, "cat_id "+cat_id);

               postjob(title,description,duration,budget,cat_id);
              //  user_profile_pic_update_url();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(PostRequriementScreen.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            attachmentFile = cursor.getString(columnIndex);
//            Log.e("Attachment Path:", attachmentFile);
            URI = Uri.parse("file://" + attachmentFile);
            Log.d(TAG, "Attachment: "+attachmentFile);
            String filename = URI.getLastPathSegment();
            attach_data_link.setText(filename);
            cursor.close();
        }

    }
*/
/*
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

        Uri uri = data.getData();

        attachmentFile = new File(getRealPathFromURI(uri));
        URI = Uri.parse("file://" + attachmentFile);
        String filename = URI.getLastPathSegment();
        attach_data_link.setText(filename);

        Log.d(TAG, "image = "+attachmentFile);


    }

    if(globalClass.isNetworkAvailable()){

    }else{
        Toasty.info(PostRequriementScreen.this, "Please check your internet connection.", Toast.LENGTH_SHORT, true).show();
    }

}
*/
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
        Uri uri = data.getData();

        attachmentFile = new File(getRealPathFromURI(uri));
        String strFileName = attachmentFile.getName();
        attach_data_link.setText(strFileName);

        Log.d(TAG, "image = " + attachmentFile);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));
            //imageView2.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {


        File f = new File(Environment.getExternalStorageDirectory().toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }


        try {
            Bitmap bitmap;
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

/*
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);*/

            Log.d(TAG, "bitmap: " + bitmap);

            //imageView2.setImageBitmap(bitmap);

            String path = Environment.getExternalStorageDirectory() + File.separator;
            // + File.separator
            //   + "Phoenix" + File.separator + "default";
            f.delete();
            OutputStream outFile = null;
            File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {
                attachmentFile = file;

                outFile = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                outFile.flush();
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bitmap photo = (Bitmap) data.getExtras().get("data");
        // iv_product_image.setImageBitmap(photo);
    }
}

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void openFolder()
    {
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setType("application/image");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
*/
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {
                        getResources().getString(R.string.take_photo),
                        getResources().getString(R.string.choose_from_gallery),
                        getResources().getString(R.string.cancel),
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PostRequriementScreen.this);
                builder.setTitle(getResources().getString(R.string.select_option));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals(getResources().getString(R.string.take_photo))) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toasty.error(PostRequriementScreen.this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(PostRequriementScreen.this,  getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
/*
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

                      //  Log.d("selectedCategory", "" + selectedCategory);

                        dataAdapter1 = new ArrayAdapter<String>(PostRequriementScreen.this,R.layout.spinner_color,R.id.txt,array)
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
                        dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
                        spinner_days.setAdapter(dataAdapter1);


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
*/
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

                    dataAdapter1 = new ArrayAdapter<String>(PostRequriementScreen.this,R.layout.spinner_color,R.id.txt,array)
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
                    dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
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
   /* private void getCategory_list()
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



                        rb = new RadioButton(PostRequriementScreen.this);
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



                params.put("view","getCategory");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }*/


    private void postjob(final String title, final String description, final String duration,
                         final String budget, String cat_id){
    pd.show();
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("view","postJob");
        params.put("user_id",globalClass.getId());
        params.put("title",title);
        params.put("description",description);
        params.put("primary_category_id",cat_id);
        params.put("duration",duration);
        params.put("budget",budget);

        try{

            params.put("attachment", attachmentFile);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "postjob- " + response.toString());
                    try {

                        //JSONObject result = response.getJSONObject("result");

                        String status = response.optString("status");
                        String message = response.getString("message");
                        pd.dismiss();


                        Intent i=new Intent(PostRequriementScreen.this, ThankyouScreen.class);
                        i.putExtra("One",message);

                        startActivity(i);

                  //      Toasty.success(PostRequriementScreen.this, message, Toast.LENGTH_SHORT, true).show();
                        // pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                // pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }

}
