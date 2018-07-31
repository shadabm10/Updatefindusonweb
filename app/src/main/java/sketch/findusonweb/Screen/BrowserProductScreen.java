package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sketch.findusonweb.R;

/**
 * Created by developer on 18/6/18.
 */

public class BrowserProductScreen extends AppCompatActivity {
   Button search_product;
   TextView search_by_name,search_by_cat,search_by_keyword,search_by_description;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_product);
        search_product=findViewById(R.id.search_product);
        search_by_name=findViewById(R.id.search_by_name);
        search_by_cat=findViewById(R.id.search_by_category);
        search_by_keyword=findViewById(R.id.search_by_keyword);
        search_by_description=findViewById(R.id.search_by_description);
        search_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String search = search_by_code.getText().toString().trim();
                if (!search_by_name.getText().toString().isEmpty()||!search_by_cat.getText().toString().isEmpty()||!search_by_keyword.getText().toString().isEmpty()||!search_by_description.getText().toString().isEmpty()) {

                    Intent intent =new Intent(BrowserProductScreen.this,BrowseProduct.class);
                    intent.putExtra("title",search_by_name.getText().toString());
                    intent.putExtra("category",search_by_cat.getText().toString());
                    intent.putExtra("description",search_by_description.getText().toString());
                    intent.putExtra("keywords",search_by_keyword.getText().toString());

                    startActivity(intent);
                    search_by_name.setText("");
                    search_by_cat.setText("");
                    search_by_description.setText("");
                    search_by_keyword.setText("");


                }else {

                    Intent intent =new Intent(BrowserProductScreen.this,BrowseProduct.class);
                    intent.putExtra("title",search_by_name.getText().toString());
                    intent.putExtra("category",search_by_cat.getText().toString());
                    intent.putExtra("description",search_by_description.getText().toString());
                    intent.putExtra("keywords",search_by_keyword.getText().toString());


                    startActivity(intent);

                }
            }
        });
        search_by_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String search = search_by_code.getText().toString().trim();
                if (!search_by_name.getText().toString().isEmpty()||!search_by_cat.getText().toString().isEmpty()||!search_by_keyword.getText().toString().isEmpty()||!search_by_description.getText().toString().isEmpty()) {

                    Intent intent =new Intent(BrowserProductScreen.this,BrowseCategory.class);
                    intent.putExtra("title",search_by_name.getText().toString());
                    intent.putExtra("category",search_by_cat.getText().toString());
                    intent.putExtra("description",search_by_description.getText().toString());
                    intent.putExtra("keywords",search_by_keyword.getText().toString());

                    startActivity(intent);
                    search_by_name.setText("");
                    search_by_cat.setText("");
                    search_by_description.setText("");
                    search_by_keyword.setText("");


                }else {

                    Intent intent =new Intent(BrowserProductScreen.this,BrowseProduct.class);
                    intent.putExtra("title",search_by_name.getText().toString());
                    intent.putExtra("category",search_by_cat.getText().toString());
                    intent.putExtra("description",search_by_description.getText().toString());
                    intent.putExtra("keywords",search_by_keyword.getText().toString());


                    startActivity(intent);

                }
            }
        });


    }
}
