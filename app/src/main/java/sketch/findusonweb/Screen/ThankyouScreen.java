package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import sketch.findusonweb.R;

/**
 * Created by developer on 19/6/18.
 */

public class ThankyouScreen extends AppCompatActivity{
    TextView Thankz;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyouo_screen);
        Thankz=findViewById(R.id.thanks);
       /* Intent intent = getIntent();
        String result = intent.getStringExtra("One");
        Thankz.setText(result);*/
    }
}