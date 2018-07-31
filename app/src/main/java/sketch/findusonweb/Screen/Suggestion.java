package sketch.findusonweb.Screen;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sketch.findusonweb.R;

/**
 * Created by developer on 11/6/18.
 */

public class Suggestion extends AppCompatActivity {
    TextView txt_back;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion);
        txt_back=findViewById(R.id.back_img);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
