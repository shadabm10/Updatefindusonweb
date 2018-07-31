package sketch.findusonweb.Screen;

import android.Manifest;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class MyProfile extends AppCompatActivity{
    ImageView imageView2;
    TextView tv_edit,tv_phone,tv_email,tv_name,toolbar_back,logout;
    RelativeLayout rl_change_pic,rl_edit_details,rl_update_profile,rl_cancel,rl_customer_detail,
            rl_change_password;
    EditText input_mobile,input_fname,input_lname,input_email;

    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    String TAG = "my_profile";

    File p_image;
    GlobalClass globalClass;
    ProgressDialog pd;
    Shared_Preference prefrence;
    ImageLoader loader;
    View view8;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass)getApplicationContext();

        if(globalClass.login_status.equals(false)){

            Intent intent = new Intent(MyProfile.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.my_profile);
        //checkLogin();

        prefrence = new Shared_Preference(MyProfile.this);

        prefrence.loadPrefrence();

        pd=new ProgressDialog(MyProfile.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
/*
        if(globalClass.getLogin_status()){

            Intent intent = new Intent(MyProfile.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
*/

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                //  .showImageOnLoading(R.mipmap.loading_black128px)
                //  .showImageForEmptyUri(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.img_failed)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyProfile.this.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MyProfile.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MyProfile.this,
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


        toolbar_back =  findViewById(R.id.back_img);


        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });


        imageView2 =findViewById(R.id.imageView2);
        tv_edit = findViewById(R.id.tv_edit);
        ////edit
        rl_change_pic = findViewById(R.id.rl_change_pic);
        rl_edit_details = findViewById(R.id.rl_edit_details);
        rl_update_profile = findViewById(R.id.rl_update_profile);
        rl_cancel = findViewById(R.id.rl_cancel);
        input_mobile = findViewById(R.id.input_mobile);
        input_fname = findViewById(R.id.input_fname);
        input_lname = findViewById(R.id.input_lname);
        input_email =findViewById(R.id.input_email);

        ////not editable
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);
        logout=findViewById(R.id.profile_logout);
        rl_customer_detail = findViewById(R.id.rl_customer_detail);
        rl_change_password = findViewById(R.id.rl_change_password);
        view8 = findViewById(R.id.view8);

        rl_change_pic.setVisibility(View.GONE);
        rl_edit_details.setVisibility(View.GONE);
        rl_customer_detail.setVisibility(View.VISIBLE);


        tv_name.setText(globalClass.getFname()+" "+globalClass.getLname());
        tv_phone.setText(globalClass.getPhone_number());
        tv_email.setText(globalClass.getName());

    /*    input_fname.setText(globalClass.getFname());
        input_lname.setText(globalClass.getLname());
        input_email.setText(globalClass.getEmail());
        input_mobile.setText(globalClass.getPhone_number());
*/
        input_email.setEnabled(false);

      /*  if(globalClass.getProfil_pic().isEmpty()){
            imageView2.setImageResource(R.mipmap.no_image);
        }else{
           *//* Picasso.with(MyProfile.this).load(globalClass.getProfil_pic()).fit().centerCrop()
                    .placeholder(R.mipmap.no_image)
                    .error(R.mipmap.error64)
                    .into(imageView2);*//*
            loader.displayImage(globalClass.getProfil_pic(), imageView2 , defaultOptions);
        }*/

/*
        if(globalClass.getPhone_number().isEmpty()){

            tv_phone.setText(getResources().getString(R.string.add_phone_number));
        }
*/


       /* if (globalClass.getLogin_from().equals("social")){

            rl_change_password.setVisibility(View.GONE);

            input_lname.setVisibility(View.GONE);
            view8.setVisibility(View.GONE);
        }else{
            rl_change_password.setVisibility(View.VISIBLE);
            input_lname.setVisibility(View.VISIBLE);
            view8.setVisibility(View.VISIBLE);
        }*/



        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_change_pic.setVisibility(View.VISIBLE);
                rl_edit_details.setVisibility(View.VISIBLE);
                rl_customer_detail.setVisibility(View.GONE);

            }
        });
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_change_pic.setVisibility(View.GONE);
                rl_edit_details.setVisibility(View.GONE);
                rl_customer_detail.setVisibility(View.VISIBLE);

            }
        });

/*
        rl_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, ChangePasswordScreen.class);
                startActivity(intent);

            }
        });
*/


        rl_change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        rl_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrence.clearPrefrence();
                globalClass.setLogin_status(false);
                Toasty.success(MyProfile.this,"You are now logged out.", Toast.LENGTH_SHORT,true).show();
                Intent intent = new Intent(MyProfile.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    public void checkLogin() {
        if (globalClass.getLogin_status() == null) {
            Intent i = new Intent(this, LoginScreen.class);
            startActivity(i);
        }
    }

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {
                        getResources().getString(R.string.take_photo),
                        getResources().getString(R.string.choose_from_gallery),
                        getResources().getString(R.string.cancel),
                       };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyProfile.this);
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
                Toasty.error(MyProfile.this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MyProfile.this,  getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            p_image = new File(getRealPathFromURI(uri));


            Log.d(TAG, "image = "+p_image);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView2.setImageBitmap(bitmap);

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

                Log.d(TAG, "bitmap: "+bitmap);

                imageView2.setImageBitmap(bitmap);

                String path = Environment.getExternalStorageDirectory()+File.separator;
                // + File.separator
                //   + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {

                    p_image = file;

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


    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(MyProfile.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    public void user_profile_pic_update_url(){

       // pd.show();

        String url = WebserviceUrl.user_profile_pic_update;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_id",globalClass.getId());

        try{

            params.put("profile_pic", p_image);

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
                    Log.d(TAG, "user_profile_pic_update- " + response.toString());
                    try {

                        //JSONObject result = response.getJSONObject("result");

                        int status = response.getInt("status");
                        String message = response.getString("message");

                        if (status == 1) {

                            // Log.d(TAG, "name: "+name)

                            JSONObject data = response.getJSONObject("data");

                            String profile_pic = data.optString("profile_pic");

                            globalClass.setProfil_pic(profile_pic);
                            prefrence.savePrefrence();




                            Toasty.success(MyProfile.this, getResources().getString(R.string.profile_pic_updated), Toast.LENGTH_SHORT, true).show();
                            rl_change_pic.setVisibility(View.GONE);
                            rl_edit_details.setVisibility(View.GONE);
                            rl_customer_detail.setVisibility(View.VISIBLE);

                        }else{


                            Toasty.warning(MyProfile.this, message, Toast.LENGTH_SHORT, true).show();
                        }

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
*/

/*
    public void user_profile_update_url(){

        pd.show();

        String url = WebserviceUrl.user_profile_update;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_id",globalClass.getId());
        params.put("first_name",input_fname.getText().toString());
        params.put("last_name",input_lname.getText().toString());
        params.put("mobile",input_mobile.getText().toString());


        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "user_profile_update_url- " + response.toString());
                    try {

                     ///   JSONObject result = response.getJSONObject("result");

                        int status = response.getInt("status");
                        String message = response.getString("message");

                        if (status == 1) {

                            // Log.d(TAG, "name: "+name)

                            JSONObject data = response.getJSONObject("data");


                                String first_name = data.optString("first_name");
                                String last_name = data.optString("last_name");
                                String mobile = data.optString("mobile");


                                globalClass.setFname(first_name);
                                globalClass.setLname(last_name);
                                globalClass.setPhone_number(mobile);
                                prefrence.savePrefrence();



                            tv_name.setText(first_name+" "+last_name);
                            tv_phone.setText(mobile);
                          //  tv_email.setText(globalClass.getEmail());


                            Toasty.success(MyProfile.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT, true).show();
                            rl_change_pic.setVisibility(View.GONE);
                            rl_edit_details.setVisibility(View.GONE);
                            rl_customer_detail.setVisibility(View.VISIBLE);

                        }else{


                            Toasty.warning(MyProfile.this, message, Toast.LENGTH_SHORT, true).show();
                        }

                        pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }
*/



}
