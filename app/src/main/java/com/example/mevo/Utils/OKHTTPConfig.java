package com.example.mevo.Utils;

import static com.example.mevo.Utils.RetrofitConfig.BASE_URL;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class OKHTTPConfig {
    String responseString = "";
    JSONObject jsonObject = new JSONObject();
    public JSONObject CreateCall(String endpoint, JSONObject jsonData) throws JSONException {
        OkHttpClient client = new OkHttpClient();

        String URL = BASE_URL;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonData.toString());
        Request request = new Request.Builder()
                .url(URL + endpoint)
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
                    responseString = responseBody;

                    try {
                        jsonObject = new JSONObject(responseBody);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("------->",responseBody);


                } else {
                    Log.e("ERROR","No Response");
                }
            }
        });

        return new JSONObject(responseString);
    }
}
