package sketch.findusonweb.Screen;

/**
 * Created by developer on 7/5/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


import com.rampo.updatechecker.UpdateChecker;

import sketch.findusonweb.R;


public class SplashScreen extends Activity {
    public static final String MyPREFERENCES1 = "somthing";
    SharedPreferences sharedpreferences;
    private static int SPLASH_TIME_OUT = 3000;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        UpdateChecker checker = new UpdateChecker(this);
        // If you are in a Activity or a FragmentActivity
        checker.start();

      
        sharedpreferences = getSharedPreferences(MyPREFERENCES1,
                Context.MODE_PRIVATE);
        String value = sharedpreferences.getString("password", "");

        if (value.equalsIgnoreCase("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent intent = new Intent(SplashScreen.this,
                            HomeScreen.class);


                    startActivity(intent);
                    finish();

                }
            }, SPLASH_TIME_OUT);
        }
    }
}
