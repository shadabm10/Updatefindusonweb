package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 5/6/18.
 */

public class BrowseJobCategory extends AppCompatActivity {
    TextView browse_jobs,browse_by_cat_by_services,text_back,browse_by_cat,browse_location,cart_img;
    ImageView profile_img,menu;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_job_category);
        cart_img=findViewById(R.id.cart_img);
        menu=findViewById(R.id.menu);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseJobCategory.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseJobCategory.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
               /* Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();*/
            }
        } else {
            Toasty.info(BrowseJobCategory.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }
        profile_img=findViewById(R.id.profile_img);
        browse_location=findViewById(R.id.browse_location);
        text_back=findViewById(R.id.cart_img);
        browse_by_cat=findViewById(R.id.browse_by_cat);
        browse_by_cat_by_services=findViewById(R.id.browse_by_cat_by_services);
        browse_jobs= findViewById(R.id.browse_jobs);
        browse_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseJobCategory.this,BrowseJobScreen.class));
            }
        });
        browse_by_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseJobCategory.this,BrowseCategory.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(BrowseJobCategory.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(BrowseJobCategory.this, DashboardScreen.class);
                    startActivity(intent);
                }
            }
        });
        browse_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu=new Intent(getApplicationContext(),BrowseLocation.class);
                startActivity(menu);
            }
        });
        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

/*
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyProfile.class));

            }
        });
*/
        browse_by_cat_by_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrowseJobCategory.this,BrowseProduct.class));
            }
        });
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}