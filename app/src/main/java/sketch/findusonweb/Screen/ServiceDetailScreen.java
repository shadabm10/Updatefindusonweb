package sketch.findusonweb.Screen;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.HashMap;
import java.util.Map;


import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;




public class ServiceDetailScreen extends AppCompatActivity  {
    TextView tv_back,tv_name,tv_des,tv_category,detail_about_us,location_list,txt_download,txt_suggestion;
    ProgressDialog pd;
    String TAG = "ServiceDetail";
    GoogleMap map;
    PdfPCell cell10,cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8;
    RelativeLayout rl_unfavorite,rl_review;
    LatLng latLng;
    PdfPTable table = new PdfPTable(2);
    String address,city,state,country,zip,f_address;
    String title,login;
    Bitmap bitmap;
    double lat;
    RelativeLayout rl_pdflayout,rl_favorites,rl_share;
    double lng;
    GlobalClass global;
    File cacheDir;
    MapView mapView;
    Marker marker;
    String id;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.service_details);
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Findusonweb");
        else
            cacheDir=ServiceDetailScreen.this.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
       // fn_permission();
        global = (GlobalClass) this.getApplication();

        pd = new ProgressDialog(ServiceDetailScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
       // location_list=findViewById(R.id.location);
        rl_favorites=findViewById(R.id.rl_favorites);
        rl_unfavorite=findViewById(R.id.rl_unfavorites);
        rl_share=findViewById(R.id.rl_share);
        txt_suggestion=findViewById(R.id.txt_suggestion);
        rl_pdflayout=findViewById(R.id.rl_map);
        txt_download=findViewById(R.id.txt_download);
        tv_back = findViewById(R.id.tv_back);
        tv_name = findViewById(R.id.tv_name);
        tv_des = findViewById(R.id.tv_des);
       // rl_message=findViewById(R.id.rl_message);
        rl_review=findViewById(R.id.rl_review);
        tv_category = findViewById(R.id.tv_category);
        detail_about_us = findViewById(R.id.detail_about_us);
        mapView = findViewById(R.id.map_view);

        id = getIntent().getExtras().getString("id");
        Log.d(TAG, "id service: "+id);

        if (mapView != null) {
            // Initialise the MapView
            mapView.onCreate(null);
            // Set the map ready callback to receive the GoogleMap object
          //  mapView.getMapAsync();
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                 //   googleMap.setMyLocationEnabled(true);
                    // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                    try {
                        MapsInitializer.initialize(getApplicationContext());
                        map = googleMap;
                        ViewList(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });
            // bindView();
        }
        txt_download.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {
                    // TODO Auto-generated method stub

                address=location_list.getText().toString();

                    String FILE = Environment.getExternalStorageDirectory().toString() + "/Findusonweb/" + "report.pdf";

                    // Create New Blank Document
                    Document document = new Document(PageSize.A4);

                    // Create Pdf Writer for Writting into New Created Document
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream(FILE));
                        // Open Document for Writting into document
                        document.open();
                        // User Define Method
                        addTitlePage(document);
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    } catch (DocumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // Close Document after writting all content
                    document.close();

                    Toast.makeText(ServiceDetailScreen.this, "PDF File is Created."+FILE, Toast.LENGTH_LONG).show();


            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.d(TAG, "onCreate:deatail "+ getIntent().getStringExtra("id"));

        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rl_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.login_status.equals(false)) {

                    Intent intent = new Intent(ServiceDetailScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ServiceDetailScreen.this, ReviewScreen.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
        txt_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ServiceDetailScreen.this,Suggestion.class);
                startActivity(intent);
            }
        });
        rl_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.login_status.equals(false)) {

                    Intent intent = new Intent(ServiceDetailScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    favorites();
                    rl_unfavorite.setVisibility(View.VISIBLE);
                    rl_favorites.setVisibility(View.GONE);

                }
            }
        });
        rl_unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfavorites();
                rl_favorites.setVisibility(View.VISIBLE);
                rl_unfavorite.setVisibility(View.GONE);

            }
        });

    }



    public void favorites() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    Toasty.success(ServiceDetailScreen.this, message, Toast.LENGTH_SHORT, true).show();



                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ServiceDetailScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }
                setMapLocation(lat,lng);



            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("listing_id", id);
                params.put("user_id", global.getId());
                params.put("view", "addtofav");
                params.put("action", "addtofavorites");
                Log.i(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    public void Unfavorites() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);
                    Toasty.success(ServiceDetailScreen.this, message, Toast.LENGTH_SHORT, true).show();



                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ServiceDetailScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }
                setMapLocation(lat,lng);



            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("listing_id", id);
                params.put("user_id", global.getId());
                params.put("view", "addtofav");
                params.put("action", "removefromfavorites");
                Log.i(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    public void addTitlePage(Document document) throws DocumentException
    {
        // Font Style for Document
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD| Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Start New Paragraph
        Paragraph prHead = new Paragraph();
        // Set Font in this Paragraph
        prHead.setFont(titleFont);
        // Add item into Paragraph
        prHead.add("Findusonweb\n");
        //prHead.add("\n");
        prHead.setAlignment(Element.ALIGN_CENTER);

        Paragraph cat = new Paragraph();
        cat.setFont(catFont);
        cat.add("\n");
        cat.add("Report\n");
        cat.add("\n");
        cat.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        document.add(prHead);
        document.add(cat);
        document.add(table);

            /* Header values*/
        table = new PdfPTable(2);
        cell1 = new PdfPCell(new Phrase("Category"));
        cell2 = new PdfPCell(new Phrase("Values"));
        cell1.setVerticalAlignment(Element.ALIGN_LEFT);
        cell2.setVerticalAlignment(Element.ALIGN_LEFT);
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setPadding(5);

        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setPadding(5);

        cell1.setBackgroundColor(BaseColor.GRAY);
        cell2.setBackgroundColor(BaseColor.GRAY);

            /*Table values*/
        cell3 = new PdfPCell(new Phrase("Title"));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell3.setPadding(5);

        cell4 = new PdfPCell(new Phrase(title));
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setBorder(Rectangle.NO_BORDER);
        cell4.setPadding(5);


        cell5 = new PdfPCell(new Phrase("Address"));
        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell5.setBorder(Rectangle.NO_BORDER);
        cell5.setPadding(5);

        cell6 = new PdfPCell(new Phrase(f_address));
        cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell6.setBorder(Rectangle.NO_BORDER);
        cell6.setPadding(5);

        cell7 = new PdfPCell(new Phrase("Name"));
        cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell7.setBorder(Rectangle.NO_BORDER);
        cell7.setPadding(5);

        cell8 = new PdfPCell(new Phrase(login));
        cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell8.setBorder(Rectangle.NO_BORDER);
        cell8.setPadding(5);

        cell10 = new PdfPCell(new Phrase(address));
        cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell10.setBorder(Rectangle.NO_BORDER);
        cell10.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);

        table.addCell(cell10);

        // add table into document
        document.add(table);
        //Toast.makeText(this, "PDF File is Created.", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }



    private void setMapLocation(double lat, double lng) {
        if (map == null) return;

         latLng =new LatLng(lat,lng);
        Log.d(TAG, "latLnggdsdgs: "+latLng);

        // Add a marker for this item and set the camera
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        marker = map.addMarker(new MarkerOptions()
                .position(latLng)
               .title(address)
                  //.snippet("Snippet")
                .draggable(true)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.map1)));

        getLocation(latLng.latitude,latLng.longitude);
    }
    public  void getLocation(final double lat, final double lng){

        String URL ="https://maps.googleapis.com/maps/api/geocode/json?latlng="
                +lat+","+lng+"&key="+getResources().getString(R.string.GEO_CODE_API_KEY);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //Here response will be received in form of JSONObject

                Log.d(TAG,"Server response - "+response );

                try {

                    address = " ";


                    JSONArray results = response.getJSONArray("results");

                    for(int i = 0; i<results.length(); i++){
                        JSONObject obj = results.getJSONObject(i);

                        JSONArray address_components = obj.getJSONArray("address_components");
                        //  Log.d(TAG, "address_components: "+address_components.toString());
                        for(int j = 0; j<address_components.length(); j++){
                            JSONObject object = address_components.getJSONObject(j);

                            JSONArray types = object.getJSONArray("types");
                            // Log.d(TAG, "types: "+types.toString());
                            for (int k =0 ; k<types.length();k++ ){
                                String object_k = types.getString(k);

                                if(object_k.equals("establishment") ||
                                        object_k.equals("point_of_interest") ||
                                        object_k.equals("premise")){
                                    address = object.getString("long_name");
                                    Log.d(TAG, "address1: "+address);
                                    break;


                                }else if(object_k.equals("route")){
                                    if(address.isEmpty()){
                                        address = object.getString("long_name");
                                    }else
                                    {
                                        address = address + ", " + object.getString("long_name");
                                    }
                                    Log.d(TAG, "address2: "+address);
                                    break;
                                }else if(object_k.equals("sublocality") ||
                                        object_k.equals("sublocality_level_1")){
                                    if(address.isEmpty()){
                                        address = object.getString("long_name");
                                    }else {
                                        address = address + ", " + object.getString("long_name");
                                    }
                                    Log.d(TAG, "address3: "+address);
                                    break;
                                }else if(object_k.equals("locality")){
                                    city = object.getString("long_name");
                                    Log.d(TAG, "city: "+city);
                                    break;
                                }else if(object_k.equals("administrative_area_level_1")){
                                    state = object.getString("long_name");
                                    Log.d(TAG, "state: "+state);
                                    break;
                                }
                                else if(object_k.equals("country")){
                                    country = object.getString("long_name");
                                    Log.d(TAG, "country: "+country);
                                    break;
                                }
                                else if(object_k.equals("postal_code")){
                                    zip = object.getString("long_name");
                                    Log.d(TAG, "zip: "+zip);
                                    break;
                                }


                            }


                        }
                        f_address = obj.getString("formatted_address");
                        Log.d(TAG, "f_address: "+f_address);
                        location_list.setText(address);
                        JSONObject geometry = obj.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        Log.d(TAG, "location: "+location);
                        Log.d(TAG, "lat: "+lat);
                        Log.d(TAG, "lng: "+lng);

                        break;
                    }






                    latLng = new LatLng(lat, lng);
                    //  tv_show_add.setText(address);
                    // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,16.0f));


                    marker = map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(address)
                            //  .snippet("Snippet")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.mipmap.map1)));



                    marker.showInfoWindow();


                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0f));
                }catch (Exception e){

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
    public void ViewList(final String id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: "+jobj);
                    JsonObject offerObject = jobj.getAsJsonObject();
                    String id = offerObject.get("id").toString().replaceAll("\"", "");
                    String user_id = offerObject.get("user_id").toString().replaceAll("\"", "");
                    title = offerObject.get("title").toString().replaceAll("\"", "");
                    String description_short = offerObject.get("description_short").toString().replaceAll("\"", "");
                    String description = offerObject.get("description").toString().replaceAll("\"", "");
                     login = offerObject.get("login").toString().replaceAll("\"", "");
                    String latitude = offerObject.get("latitude").toString().replaceAll("\"", "");
                    String longitude = offerObject.get("longitude").toString().replaceAll("\"", "");

                    Log.d(TAG, "title: "+title);
                    Log.d(TAG, "description_short: "+description_short);
                    Log.d(TAG, "description: "+description);
                    Log.d(TAG, "login: "+login);
                    Log.d(TAG, "latitude: "+latitude);
                    Log.d(TAG, "longitude: "+longitude);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("title", title);
                    hashMap.put("description", description_short);
                   // hashMap.put("primary_category_name", primary_category_name);
                    //hashMap.put("logo_url", logo_url);
                    hashMap.put("id", id);
                   // hashMap.put("location_name",location_name);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    //list_names.add(hashMap);
                    Log.d(TAG, "id: " + id);
                    lat= Double.parseDouble(latitude);
                    lng= Double.parseDouble(longitude);
                    Log.d(TAG, "lat: "+lat);
                    Log.d(TAG, "lng: "+lng);

                    tv_name.setText(title);
                    tv_category.setText(login);
                    tv_des.setText(description_short);
                    detail_about_us.setText(Html.fromHtml(description));



                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ServiceDetailScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }
                setMapLocation(lat,lng);



            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("listing_id", id);
                params.put("view", "viewListing");
                Log.i(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
