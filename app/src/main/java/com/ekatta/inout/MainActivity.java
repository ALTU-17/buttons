package com.ekatta.inout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    protected LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button button;
    Button button2;
    TextView msgText;
    TextView lat;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        msgText=findViewById(R.id.msgtext);
        lat=findViewById(R.id.lat);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

//        initlizeBiometric();
        BiometricManager biometricManager= BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                //setmsg
                msgText.setText("You can able to use fingerprint");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgText.setText("This device has no fingerprint");
                button2.setVisibility(View.GONE);

                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgText.setText("Sensor is currently unavailable");
                button2.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgText.setText("No previous fingerprint, Please set a fingerprint in your security option");
                button2.setVisibility(View.GONE);
                break;
        }
        BiometricPrompt prompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(getApplicationContext()," User'OUT' ",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });
        final BiometricPrompt.PromptInfo promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prompt.authenticate(promptInfo);

            }
        });



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
                ){
                    getCurrentLocation();

                }else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                            100);
                }


            }
        });


//        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED)
//        {
//            return;
//        }
//
//
//        locationManager.requestLocationUpdates
//                (locationManager.GPS_PROVIDER,0,0, (android.location.LocationListener) this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();

            // Biometric able to work on click
            BiometricManager biometricManager= BiometricManager.from(this);
            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    //setmsg
                    msgText.setText("You can able to use fingerprint");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    button.setVisibility(View.GONE);

                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    button.setVisibility(View.GONE);
                    break;

                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    button.setVisibility(View.GONE);
                    break;
            }
            BiometricPrompt prompt = new BiometricPrompt(this,
                    ContextCompat.getMainExecutor(this),
                    new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Toast.makeText(getApplicationContext()," User'IN' ",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                        }
                    });
            final BiometricPrompt.PromptInfo promptInfo= new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Fingerprint")
                    .setNegativeButtonText("Cancel")
                    .build();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prompt.authenticate(promptInfo);

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
         LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         //check condition
         if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
         || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
             fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                 @Override
                 public void onComplete(@NonNull Task<Location> task) {
                     Location location = task.getResult();
                     if (location != null ){
                         lat.setText(String.valueOf(location.getLongitude()));
                     } else{
                         LocationRequest locationRequest = new LocationRequest()
                                 .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                 .setInterval(10000)
                                 .setNumUpdates(1);

                         LocationCallback locationCallback = new LocationCallback() {
                             @Override
                             public void onLocationResult(@NonNull LocationResult locationResult) {
                                 super.onLocationResult(locationResult);
                             }
                         };
                     }

                 }
             });

         } else{

             StartActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).
                     setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
         }
    }

    private void StartActivity(Intent setFlags) {
    }


}
