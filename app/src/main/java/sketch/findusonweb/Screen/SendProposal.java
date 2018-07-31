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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by developer on 8/6/18.
 */

public class SendProposal extends AppCompatActivity {
    String TAG = "post requirement";
    Spinner spinner_days;
    TextView back_button,attach_data,attach_data_link,tv_submit,back;
    String city_id,cat_id,id;
    Shared_Preference prefrence;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    GlobalClass globalClass;
    ArrayAdapter<String> dataAdapter1;
    ProgressDialog pd;
    ImageView down_arrow;
    int columnIndex;
    File attachmentFile;
    EditText edt_description,edt_title,edt_duration,edt_budget,edt_iteration;
    String title , description, duration, budget,iteration;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private static final int PICKFILE_RESULT_CODE = 1;

    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.send_proposal);
        pd = new ProgressDialog(SendProposal.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SendProposal.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(SendProposal.this,
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
        }        attach_data=findViewById(R.id.attach_data);
        spinner_days=findViewById(R.id.spinner_days);
        back=findViewById(R.id.back_img);
        attach_data_link=findViewById(R.id.attach_data_name);
        edt_iteration=findViewById(R.id.edt_iteration);
        back_button=findViewById(R.id.back_img);
        down_arrow=findViewById(R.id.down_arrow_category);
        tv_submit=findViewById(R.id.tv_submit);
        //edt_title=findViewById(R.id.edt_title);
        edt_description=findViewById(R.id.edt_description);
        edt_duration=findViewById(R.id.edt_duration);
        edt_budget=findViewById(R.id.edt_budget);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(SendProposal.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(SendProposal.this);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {

            }
        } else {
            Toasty.info(SendProposal.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
               // title = edt_title.getText().toString();
                description = edt_description.getText().toString();
                duration = edt_duration.getText().toString();
                budget = edt_budget.getText().toString();
                iteration=edt_iteration.getText().toString();
                Log.d(TAG, "cat_id "+cat_id);

                postjob(id,description,duration,budget,cat_id,iteration);
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
                if (ContextCompat.checkSelfPermission(SendProposal.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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
        Toasty.info(SendProposal.this, "Please check your internet connection.", Toast.LENGTH_SHORT, true).show();
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
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SendProposal.this);
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
                Toasty.error(SendProposal.this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(SendProposal.this,  getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void postjob( final  String id,final String description, final String duration,
                         final String budget, String cat_id,final String iteration){
        pd.show();
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("view","send_proposal");
        params.put("user_id",globalClass.getId());

        params.put("description",description);
        params.put("request_id",getIntent().getStringExtra("id"));
        params.put("duration",duration);
        params.put("budget",budget);
        params.put("iterations",iteration);

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

                        android.app.AlertDialog alert = new android.app.AlertDialog.Builder(SendProposal.this).create();
                        alert.setMessage(message);
                        alert.setButton(Dialog.BUTTON_POSITIVE, "Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        finish();
                                    }
                                });
                        alert.show();

                        //      Toasty.success(SendProposal.this, message, Toast.LENGTH_SHORT, true).show();
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

/*
    private void postjob(final String title, final String description,
                         final String duration, final String budget, final String cat_id) {

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

                 //   String msg =jobj.get("message").toString().replaceAll("\"", "");
                    String msg =jobj.get("message").toString().replaceAll("\"", "");

                   // Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
                    android.app.AlertDialog alert = new android.app.AlertDialog.Builder(SendProposal.this).create();
                    alert.setMessage(msg);
                    alert.setButton(Dialog.BUTTON_POSITIVE, "Ok",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    finish();
                                }
                            });
                    alert.show();

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



                params.put("view","postJob");
                params.put("user_id",globalClass.getId());
                params.put("title",title);
                params.put("description",description);
                params.put("primary_category_id",cat_id);
                params.put("duration",duration);
                params.put("budget",budget);
                params.put("attachment", attachmentFile.toString());

                Log.d(TAG, "getParams: "+params);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
*/
}
