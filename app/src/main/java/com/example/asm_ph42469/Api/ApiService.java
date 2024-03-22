package com.example.asm_ph42469.Api;

import com.example.asm_ph42469.Modal.SanPham;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    String DOMAIN = "http://192.168.189.67:3000/api/";

    ApiService apiService  = new Retrofit.Builder()
            .baseUrl(ApiService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
    @GET("sanphams")
    Call<List<SanPham>> getData();
    @POST("sanphams/add")
    Call<SanPham> addSanPham(@Body SanPham sanPham);

    @Multipart
    @POST("sanphams/add")
    Call<SanPham> addSanPhamPicker(
            @Part("maSp") RequestBody maSp,
            @Part("tenSp") RequestBody tenSp,
            @Part("gia") RequestBody gia,
            @Part("soLuong") RequestBody soLuong,
            @Part MultipartBody.Part anh);


    @DELETE("sanphams/delete/{id}")
    Call<SanPham> deleteSanPham(@Path("id") String idSanPham);


    @PUT("sanphams/update/{id}")
    Call<SanPham> updateSanPham(@Path("id") String idSanPham,
                                @Body SanPham sanPham);
}
