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
import android.os.PersistableBundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 12/6/18.
 */

public class AddProductScreen extends AppCompatActivity {
    TextView back_img;
    Spinner spinner_type,spinner_status;
    EditText edt_title,edt_description,edt_price,edt_expire;
    ArrayList<String> type;
    ArrayList<String> status;
    String TAG = "add_product";
    ProgressDialog pd;
    String listing_id;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    String status_text,type_text;
    TextView tv_submit,attach_data,attach_data1,attach_data2,attach_data3,attach_data4;
    String title , description, expire, price;
    File file1,file2,file3,file4,file5;
    private static final int PICK_IMAGE_CAMERA1 = 11;
    private static final int PICK_IMAGE_CAMERA2 = 12;
    private static final int PICK_IMAGE_CAMERA3 = 13;
    private static final int PICK_IMAGE_CAMERA4 = 14;
    private static final int PICK_IMAGE_CAMERA5 = 15;
    private static final int PICK_IMAGE_GALLERY1 = 21;
    private static final int PICK_IMAGE_GALLERY2 = 22;
    private static final int PICK_IMAGE_GALLERY3 = 23;
    private static final int PICK_IMAGE_GALLERY4 = 24;
    private static final int PICK_IMAGE_GALLERY5 = 25;
    TextView attach_data_name,attach_data_name1,attach_data_name2,attach_data_name3,attach_data_name4;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(AddProductScreen.this);
        prefrence.loadPrefrence();

        pd = new ProgressDialog(AddProductScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddProductScreen.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddProductScreen.this,
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

        type = new ArrayList<>();
        status = new ArrayList<>();
       /* Bundle b = getIntent().getExtras();
        listing_id=b.getString("id");*/
        type.add("Product");
        type.add("Service");
        type.add("Promotional");

        status.add("Active");
        status.add("Inactive");
        back_img=findViewById(R.id.back_img);
        spinner_type=findViewById(R.id.spinner_type);
        spinner_status=findViewById(R.id.spinner_status);
        edt_title=findViewById(R.id.edt_title);
        edt_description=findViewById(R.id.edt_description);
        edt_price=findViewById(R.id.edt_price);
        edt_expire=findViewById(R.id.edt_expire);
        attach_data=findViewById(R.id.attach_data);
        attach_data1=findViewById(R.id.attach_data1);
        attach_data2=findViewById(R.id.attach_data2);
        attach_data3=findViewById(R.id.attach_data3);
        attach_data4=findViewById(R.id.attach_data4);
        tv_submit=findViewById(R.id.tv_submit);
        attach_data_name=findViewById(R.id.attach_data_name);
        attach_data_name1=findViewById(R.id.attach_data_name1);
        attach_data_name2=findViewById(R.id.attach_data_name2);
        attach_data_name3=findViewById(R.id.attach_data_name3);
        attach_data_name4=findViewById(R.id.attach_data_name4);

     //   ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_color,R.id.txt,type)
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
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dataAdapter.setDropDownViewResource(R.layout.spinner_color);
        spinner_type.setAdapter(dataAdapter);


        ArrayAdapter<String> dataAdapter1  = new ArrayAdapter<String>(this,R.layout.spinner_color,R.id.txt,status)
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


        dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
        spinner_status.setAdapter(dataAdapter1);


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
               type_text= parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+type_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
               status_text= parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+status_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_title.getText().toString();
                description = edt_description.getText().toString();
                expire = edt_expire.getText().toString();
                price = edt_price.getText().toString();


                postjob(title,description,price,expire);
                //  user_profile_pic_update_url();
            }
        });

        attach_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA1,PICK_IMAGE_GALLERY1);
            }
        });

        attach_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA2,PICK_IMAGE_GALLERY2);
            }
        });
        attach_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA3,PICK_IMAGE_GALLERY3);
            }
        });
        attach_data3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA4,PICK_IMAGE_GALLERY4);
            }
        });
        attach_data4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA5,PICK_IMAGE_GALLERY5);
            }
        });
    }

    public void openFolder(final int code_camera,final int code_gallery)
    {


        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {
                        getResources().getString(R.string.take_photo),
                        getResources().getString(R.string.choose_from_gallery),
                        getResources().getString(R.string.cancel),
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddProductScreen.this);
                builder.setTitle(getResources().getString(R.string.select_option));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals(getResources().getString(R.string.take_photo))) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, code_camera);
                            Log.d(TAG, "code_camera: "+code_camera);
                        } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, code_gallery);
                            Log.d(TAG, "code_gallery: "+code_gallery);
                        } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toasty.error(AddProductScreen.this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AddProductScreen.this,  getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode);

        if(resultCode == RESULT_OK && data != null && data.getData() != null){
            switch (requestCode){
                case PICK_IMAGE_CAMERA1:

                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Log.d(TAG, "onActivityResult: "+bitmap);
                    file1=camera_pick(bitmap);
                    attach_data_name.setText(file1.getName());
                    break;
                case PICK_IMAGE_CAMERA2:
                    Bitmap bitmap1;
                    bitmap1 = (Bitmap) data.getExtras().get("data");
                    file2=camera_pick(bitmap1);
                    attach_data_name1.setText(file2.getName());
                    break;
                case PICK_IMAGE_CAMERA3:
                    Bitmap bitmap2;
                    bitmap2 = (Bitmap) data.getExtras().get("data");
                    file3=camera_pick(bitmap2);
                    attach_data_name2.setText(file3.getName());
                    break;
                case PICK_IMAGE_CAMERA4:
                    Bitmap bitmap3;
                    bitmap3 = (Bitmap) data.getExtras().get("data");
                    file4=camera_pick(bitmap3);
                    attach_data_name3.setText(file4.getName());
                    break;
                case PICK_IMAGE_CAMERA5:
                    Bitmap bitmap4;
                    bitmap4 = (Bitmap) data.getExtras().get("data");
                    file5=camera_pick(bitmap4);
                    attach_data_name4.setText(file5.getName());
                    break;

                case PICK_IMAGE_GALLERY1:
                    Uri uri = data.getData();
                    file1 = gallery_pick(uri);
                    attach_data_name.setText(file1.getName());
                    break;
                case PICK_IMAGE_GALLERY2:
                    Uri uri2 = data.getData();
                    file2 = gallery_pick(uri2);
                    attach_data_name1.setText(file2.getName());
                    break;
                case PICK_IMAGE_GALLERY3:
                    Uri uri3 = data.getData();
                    file3 = gallery_pick(uri3);
                    attach_data_name2.setText(file3.getName());
                    break;
                case PICK_IMAGE_GALLERY4:
                    Uri uri4 = data.getData();
                    file4 = gallery_pick(uri4);
                    attach_data_name3.setText(file4.getName());
                    break;
                case PICK_IMAGE_GALLERY5:
                    Uri uri5 = data.getData();
                    file5 = gallery_pick(uri5);
                    attach_data_name4.setText(file5.getName());
                    break;


            }
        }




    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public File camera_pick(Bitmap bitmap){

        File file = null;


        File f = new File(Environment.getExternalStorageDirectory().toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }


        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


            Log.d(TAG, "bitmap: " + bitmap);

            //imageView2.setImageBitmap(bitmap);

            String path = Environment.getExternalStorageDirectory() + File.separator;

            f.delete();
            OutputStream outFile = null;
            file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {
                file1 = file;
                Log.d(TAG, "camera_pick: "+file.getName());

                outFile = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
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

        return file;
    }

    public File gallery_pick(Uri uri){
        File file = null;
        file = new File(getRealPathFromURI(uri));
        /*String strFileName = attachmentFile.getName();
        attach_data_link.setText(strFileName);

        Log.d(TAG, "image = " + attachmentFile);*/

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));
            //imageView2.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    private void postjob(final String title, final String description, final String price,
                         final String expire_date){
        pd.show();
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("view","addProductApp");
        params.put("title",title);
        params.put("type",status_text);
        params.put("description",description);
        params.put("expire_date",expire_date);
        params.put("custom_16",status_text);
        params.put("price",price);
        params.put("listing_id", "808685");
        Log.d(TAG, "listing ID for Add Product: "+listing_id);
        params.put("friendly_url","https://www.mydevsystems.com/dev/findusonweb/rest/RestController.php");

        try{

            params.put("classified_image1", file1);
            params.put("classified_image2", file2);
            params.put("classified_image3", file3);
            params.put("classified_image4", file4);
            params.put("classified_image5", file5);

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

                        android.app.AlertDialog alert = new android.app.AlertDialog.Builder(AddProductScreen.this).create();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(AddProductScreen.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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
}