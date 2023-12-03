package com.example.pr_29_kopylov;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.content.ComponentName;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton buttonCamera, buttonInternet, buttonCalc, buttonMap, buttonCalendar;
    Context mContext;
    String UrlYandex = "https://gpt-chatbot.ru/";
    String geooo = "geo:01.000001, 01.000001";
    private static final int PICK_CONTACT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);buttonCamera = findViewById(R.id.imageButton_camera);
        buttonCamera.setOnClickListener(this);
        buttonInternet = findViewById(R.id.imageButton_internet);
        buttonInternet.setOnClickListener(this);
        buttonCalc = findViewById(R.id.imageButton_calc);
        buttonCalc.setOnClickListener(this);
        buttonMap = findViewById(R.id.imageButton_map);
        buttonMap.setOnClickListener(this);
        buttonCalendar = findViewById(R.id.sms);
        buttonCalendar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton_camera){
            if(checkCameraHardware(this) == true){
                startActivity(new Intent(this, CameraActivity.class));
            }
            else {
                Toast.makeText(mContext, "На устройстве нет камеры :(", Toast.LENGTH_LONG).show();
            }
        }
        else if(view.getId() == R.id.imageButton_internet){
            openWebPage(UrlYandex);
        }
        else if(view.getId() == R.id.imageButton_calc){
            pickContact();
        }
        else if(view.getId() == R.id.imageButton_map){
            startMap(geooo);
        }
        else if(view.getId() == R.id.sms){
            Sms();
        }
    }
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
    private void openWebPage(String url) {
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(url));
        startActivity(myWebLink);
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            String contactId = data.getData().getLastPathSegment();
        }
    }
    protected void startMap(String geo){
        Uri intentUri = Uri.parse(geo);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); //пакет Google Maps
        if (mapIntent.resolveActivity(getPackageManager()) != null)
            startActivity(mapIntent); //если есть Google Maps
        else {
            startActivity(new Intent(Intent.ACTION_VIEW, intentUri));
        }
    }
    protected void Sms(){
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {

        }*/
        Intent calculatorIntent = new Intent();
        calculatorIntent.setAction(Intent.ACTION_MAIN);
        calculatorIntent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
        startActivity(calculatorIntent);
    }
}