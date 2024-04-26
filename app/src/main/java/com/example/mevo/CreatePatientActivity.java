package com.example.mevo;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import static com.example.mevo.Utils.RetrofitConfig.BASE_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageAnalysis;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mevo.APIs.API;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.DataModels.UserModel;
import com.example.mevo.Utils.ImageUtil;
import com.example.mevo.Utils.RetrofitConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreatePatientActivity extends AppCompatActivity {
    protected static final int CAMERA_REQUEST = 100;
    File captureMediaFile;
    private int CAMERA_PIC_REQUEST = 333;
    final Bitmap[] faceBitmap = new Bitmap[5];
    ImageView PatientImage;
    RadioButton genderSelection;
    ImageView imageview;
    OkHttpClient client = new OkHttpClient();
    String base64String;
    Gson gson = new Gson();
    API retrofitAPI = new RetrofitConfig().getRerofitAPI();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText Name, Age, Address, Contact;

        RadioGroup Gender;
        Button saveBtn;

        Name = findViewById(R.id.patientName);
        Age = findViewById(R.id.patientAge);
        Contact = findViewById(R.id.patientContact);
        Address = findViewById(R.id.patientAddress);
        Gender = findViewById(R.id.patientGender);
        PatientImage = findViewById(R.id.patientImage);
        saveBtn = findViewById(R.id.patientSaveBtn);
        imageview = findViewById(R.id.patientImage);

        saveBtn.setOnClickListener(v -> {

            genderSelection = findViewById(Gender.getCheckedRadioButtonId());
            PatientModel patientModel = new PatientModel("",Name.getText().toString(),Age.getText().toString(),Contact.getText().toString(),Address.getText().toString(),genderSelection.getText().toString(),base64String);
            AddPatient(patientModel);
/*
            Call<PatientModel> call = retrofitAPI.AddPatient(patientModel);
            call.enqueue(new Callback<PatientModel>() {
                @Override
                public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {

                    //Toast.makeText(getApplicationContext(), "Patient Added Successfully", Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.INVISIBLE);
                    finish();
                    //Intent intent = new Intent(CreatePatientActivity.this,PanjikaranaActivity.class);
                    //intent.putExtra("_id",patientModel.getPatientID());
                    //startActivity(intent);
                }

                @Override
                public void onFailure(Call<PatientModel> call, Throwable t) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Error Adding Patient", Toast.LENGTH_LONG).show();
                    Log.e("----------",t.getMessage());
                }
            });

*/

        });

        PatientImage.setOnClickListener(v -> {
            dispatchTakePictureIntent();
            //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        });
    }

    public void AddPatient(PatientModel patientModel){
        String URL = BASE_URL + "/AddPatient";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = gson.toJson(patientModel);
        RequestBody requestBody = RequestBody.create(JSON, json);
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
                        String _id = jsonObject.getString("insertedID");
                        if(!_id.isEmpty()){
                            Intent intent = new Intent(CreatePatientActivity.this,PanjikaranaActivity.class);
                            intent.putExtra("_id",_id);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(CreatePatientActivity.this,"Error adding to DB",Toast.LENGTH_SHORT).show();
                        }

                        //Log.e("Message",jsonObject.getString("message"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("------>",responseBody);
                } else {
                    Log.e("ERROR","NO Response");
                }
            }
        });
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
            Bitmap bitmap = BitmapFactory.decodeFile(captureMediaFile.getAbsolutePath());
            base64String = ImageUtil.convert(bitmap);
            imageview.setImageBitmap(bitmap);
            //sendImageToServer(captureMediaFile);
        }
    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                    .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                    .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                    .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                    .setMinFaceSize(0.1f)
                    .enableTracking().build();

            FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance()
                    .getVisionFaceDetector(options);

            FirebaseVisionImage inputImage = FirebaseVisionImage.fromBitmap(image);
            faceDetector.detectInImage(inputImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                @Override
                public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                    processDetectedFaces(firebaseVisionFaces,image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Face detection failed: " + e.getMessage());
                }
            });
        }
    }



    private void processDetectedFaces(List<FirebaseVisionFace> faces, Bitmap image) {
        for (int i=0;i<faces.size();i++) {
            Rect bounds = faces.get(i).getBoundingBox();
            faceBitmap[i] = Bitmap.createBitmap(image,bounds.left, bounds.top, bounds.width(), bounds.height());

        }
        ImageView imageview = (ImageView) findViewById(R.id.patientImage);
        base64String = ImageUtil.convert(image);
        imageview.setImageBitmap(image);
    }
 */
}