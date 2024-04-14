package com.example.mevo.APIs;

import com.example.mevo.DataModels.DoctorModel;
import com.example.mevo.DataModels.MedicineModel;
import com.example.mevo.DataModels.NotificationModel;
import com.example.mevo.DataModels.PatientModel;
import com.example.mevo.DataModels.Room;
import com.example.mevo.DataModels.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @POST("signup")
    Call<UserModel> signUp(@Body UserModel model);
    @POST("signin")
    Call<UserModel> signIn(@Body UserModel model);
    @GET("GetRooms")
    Call <List<Room>> GetRooms();
    @POST("AddRoom")
    Call<Room> AddRoom(@Body Room model);
    @POST("EditRoom")
    Call<Room> EditRoom(@Body Room model);
    @POST("AddPatient")
    Call<PatientModel> AddPatient(@Body PatientModel model);
    @GET("GetDoctors")
    Call <List<DoctorModel>> GetDoctors();
    @GET("GetNotifications")
    Call <List<NotificationModel>> GetNotifications();
    @POST("AddMedicine")
    Call<MedicineModel> AddMedicine(@Body MedicineModel model);
    @GET("GetMedicines")
    Call <List<MedicineModel>> GetMedicines();
    @GET("GetPatients")
    Call <List<PatientModel>> GetPatients();
    @Headers("Content-Type: application/json")
    @POST("DeletePatient")
    Call <PatientModel> DeletePatient(@Body Map<String, Object> body);
    @POST("EditPatient")
    Call<PatientModel> EditPatient(@Body PatientModel model);
    @POST("DeleteRoom")
    Call <Room> DeleteRoom(@Body Room room);
    @Headers("Content-Type: application/json")
    @POST("ScanPatient")
    Call <PatientModel> ScanPatient(@Body Map<String, Object> body);

}
