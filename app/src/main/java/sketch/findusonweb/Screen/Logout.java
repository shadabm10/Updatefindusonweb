package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 12/5/18.
 */

public class Logout extends AppCompatActivity {
    private Button logout;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_screen);
        logout=findViewById(R.id.logout);
        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(Logout.this);
        prefrence.loadPrefrence();
        pd=new ProgressDialog(Logout.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrence.clearPrefrence();
                globalClass.setLogin_status(false);
                Toasty.success(Logout.this,"You are now logged out.", Toast.LENGTH_SHORT,true).show();
                Intent intent = new Intent(Logout.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}