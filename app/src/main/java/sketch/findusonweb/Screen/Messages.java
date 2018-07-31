package sketch.findusonweb.Screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sketch.findusonweb.R;

/**
 * Created by developer on 6/6/18.
 */

public class Messages extends AppCompatActivity {
    TextView back_button;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        back_button=findViewById(R.id.back_img);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
