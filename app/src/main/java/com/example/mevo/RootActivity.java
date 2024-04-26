package com.example.mevo;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import static com.example.mevo.Utils.RetrofitConfig.BASE_URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.Utils.ImageUtil;
import com.example.mevo.Utils.RetrofitConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.mevo.databinding.ActivityRootBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Callback;
import okhttp3.RequestBody;
import retrofit2.Response;
//import retrofit2.Callback;


public class RootActivity extends AppCompatActivity {
    FloatingActionButton mAddAlarmFab, mAddPersonFab;
    ExtendedFloatingActionButton mAddFab;
    TextView addAlarmActionText, addPersonActionText, greetings;
    Boolean isAllFabsVisible;
    OkHttpClient client = new OkHttpClient();
    ProgressDialog loading;
    protected static final int CAMERA_REQUEST = 100;
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    File captureMediaFile;
    private ActivityRootBinding binding;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.root_logout){
            Toast.makeText(binding.getRoot().getContext(),"WIP",Toast.LENGTH_SHORT).show();
        } else if(id == R.id.root_administration){
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("Message","Hello Moto");
                //login_admin(jsonData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if(id == R.id.root_exit){
            finishAndRemoveTask();
        } else if(id == R.id.root_reload){
            Toast.makeText(binding.getRoot().getContext(),"Reloading...",Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        } else if (id == R.id.root_scan) {
            dispatchTakePictureIntent();
        }
        return super.onOptionsItemSelected(item);

    }

    private void login_admin(JSONObject jsonData) {
        String URL = BASE_URL + "/test";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonData.toString());
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        //Log.e("Message",jsonObject.getString("message"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("------>",responseBody);
                } else {
                    Log.e("ERROR","NO resoinse");
                }
            }
        });

    }

    private void sendImageToServer(File imageFile) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();
        String url = BASE_URL + "/recog";


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("reference_image", "Capture.jpg",
                        RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to send image to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                loading.dismiss();
                                showPatientDetail(getApplicationContext(),jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                   // Log.e("------>",responseData);
                } else {
                    runOnUiThread(() -> Toast.makeText(RootActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    public void showPatientDetail(Context context, JSONObject patient) throws JSONException {
        View view = LayoutInflater.from(context).inflate(R.layout.view_patient_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(RootActivity.this);
        builder.setTitle("Patient Details");
        builder.setView(view);

        TextView id = view.findViewById(R.id.viewPatientID);
        TextView name = view.findViewById(R.id.viewPatientName);
        TextView age = view.findViewById(R.id.viewPatientAge);
        TextView gender = view.findViewById(R.id.viewPatientGender);
        TextView contact = view.findViewById(R.id.viewPatientContact);
        TextView address = view.findViewById(R.id.viewPatientAddress);
        ImageView image = view.findViewById(R.id.viewPatientImage);

        id.setText(patient.getString("_id").toString());
        name.setText(patient.getString("PatientName"));
        age.setText(patient.getString("PatientAge"));
        gender.setText(patient.getString("PatientGender"));
        contact.setText(patient.getString("PatientContact"));
        address.setText(patient.getString("PatientAddress"));
        image.setImageBitmap(ImageUtil.convert(patient.getString("PatientImage")));

        builder.setCancelable(true);
        builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void dispatchTakePictureIntent() {
        captureMediaFile = ImageUtil.getOutputMediaFile(getApplicationContext());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", captureMediaFile);
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        } else {
            Toast.makeText(getApplicationContext(),"Camera Unavailable",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            String filePath = captureMediaFile.getAbsolutePath();
            ImageUtil.downscaleBitmap(filePath,500,500);
            loading = new ProgressDialog(RootActivity.this);
            loading.setCancelable(true);
            loading.setMessage("Fetching details from Server");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.show();
            sendImageToServer(captureMediaFile);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRootBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_patients, R.id.navigation_notifications, R.id.navigation_Rooms).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_root);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        greetings = findViewById(R.id.greetings_home);
        //greetings.setText("Welcome, " + username);
        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);
        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);

        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);
        isAllFabsVisible = false;
        mAddFab.shrink();
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {
                            mAddAlarmFab.show();
                            mAddPersonFab.show();
                            addAlarmActionText.setVisibility(View.VISIBLE);
                            addPersonActionText.setVisibility(View.VISIBLE);
                            mAddFab.extend();
                            isAllFabsVisible = true;
                        } else {
                            mAddAlarmFab.hide();
                            mAddPersonFab.hide();
                            addAlarmActionText.setVisibility(View.GONE);
                            addPersonActionText.setVisibility(View.GONE);
                            mAddFab.shrink();
                            isAllFabsVisible = false;
                        }
                    }
                });
        mAddPersonFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RootActivity.this, CreatePatientActivity.class);
                        startActivity(intent);
                    }
                });
        mAddAlarmFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RootActivity.this,PanjikaranaActivity.class);
                        startActivity(intent);
                    }
                });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}