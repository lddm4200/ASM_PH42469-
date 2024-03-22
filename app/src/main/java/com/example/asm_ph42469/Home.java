package com.example.asm_ph42469;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_ph42469.Adapter.SanPhamAdapter;
import com.example.asm_ph42469.Api.ApiService;
import com.example.asm_ph42469.Modal.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    TextView txt;
    List<SanPham> list = new ArrayList<>();
    SanPhamAdapter adapter;
    RecyclerView rcvSV;
    FloatingActionButton fltadd;
    ImageView imagePiker;
    private  Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcvSV = findViewById(R.id.rcvSV);
        fltadd = findViewById(R.id.fltadd);
        txt = findViewById(R.id.txt);

        loadData();






        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Home.this,new SanPham(),1,list);
            }
        });
    }


    public void showDialog (Context context, SanPham sanPham, Integer type,List<SanPham> list){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_sanpham,null);
        builder.setView(view);
        Dialog dialog=builder.create();
        dialog.show();

        EditText edtMaSp = view.findViewById(R.id.edtMaSp);
        EditText edtNameSp = view.findViewById(R.id.edtNameSp);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);
        EditText edtAvatar = view.findViewById(R.id.edtAvatar);
//        Button btnChonAnh =view.findViewById(R.id.btnChonAnh);
        Button btnSave =view.findViewById(R.id.btnSave);

        if (type == 0){
            edtMaSp.setText(sanPham.getMaSp());
            edtNameSp.setText(sanPham.getTenSp());
            edtGia.setText(sanPham.getGia()+"");
            edtSoLuong.setText(sanPham.getSoLuong()+"");
            edtAvatar.setText(sanPham.getAnh());
//            Glide.with(view).load(sanPham.getAnh()).into(imagePiker);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = edtMaSp.getText().toString().trim();
                String tensp = edtNameSp.getText().toString().trim();
                String gia = edtGia.getText().toString();
                String soluong = edtSoLuong.getText().toString();
                String avatar = edtAvatar.getText().toString().trim();
                if (masp.isEmpty() || tensp.isEmpty()|| gia.isEmpty()|| soluong.isEmpty()){
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else if (!isDouble(gia)) {
                    Toast.makeText(context, "Gia phải là số", Toast.LENGTH_SHORT).show();
                }else if (!isDouble(soluong)) {
                    Toast.makeText(context, "So luong phải là số", Toast.LENGTH_SHORT).show();
                } else {
                    Double coin = Double.parseDouble(gia);
                    int so = Integer.parseInt(soluong);
                        SanPham sv = new SanPham(avatar,masp,tensp,coin,so);

                        Call<SanPham> call = ApiService.apiService.addSanPham(sv);

                        if (type == 0){
                            call = ApiService.apiService.updateSanPham(sanPham.get_id(), sv);
                        }

                        call.enqueue(new Callback<SanPham>() {
                            @Override
                            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                if (response.isSuccessful()){
                                    String msg = "Add success";
                                    if (type == 0){
                                        msg = "Update success";
                                    }
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    loadData();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<SanPham> call, Throwable t) {
                                String msg = "Add fail";
                                if (type == 0){
                                    msg = "update fail";
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        });


//                        RequestBody rMasv = RequestBody.create(MediaType.parse("multipart/form-data"), masv);
//                        RequestBody rName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
//                        RequestBody rPoint = RequestBody.create(MediaType.parse("multipart/form-data"), diemTB);
//
//                        String strRealPart = RealPathUtil.getRealPath(Home.this,mUri);
//                        Log.e("a", strRealPart);
//                        edtAvatar.setText(strRealPart);
//                        File file = new File(strRealPart);
//                        RequestBody requestBodyAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                        MultipartBody.Part multiAvatar = MultipartBody.Part.createFormData("avatar", file.getName(),requestBodyAvatar);
//
//                        ApiService.apiService.addStudentPicker(rMasv,rName,rPoint,multiAvatar).enqueue(new Callback<SinhVien>() {
//                            @Override
//                            public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
//                                if (response.isSuccessful()){
//                                    Toast.makeText(Home.this, "Add success", Toast.LENGTH_SHORT).show();
//                                    loadData();
//                                    dialog.dismiss();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<SinhVien> call, Throwable t) {
//                                Toast.makeText(Home.this, "Add fail", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        });


                }
            }
        });

//        if (type == 1){
//            btnChonAnh.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    requestPermission();
//                }
//            });
//        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void loadData (){
        Call<List<SanPham>> call = ApiService.apiService.getData();

        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful()){

                    Toast.makeText(Home.this, "Call API", Toast.LENGTH_SHORT).show();
                    list = response.body();
                    adapter = new SanPhamAdapter(Home.this, list);
                    rcvSV.setLayoutManager(new LinearLayoutManager(Home.this));
                    rcvSV.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(Home.this, "Call API Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Home.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("\n" +
                                "Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n\nVui lòng bật quyền tại [Cài đặt] > [Quyền]")
                        .setPermissions(Manifest.permission.READ_MEDIA_IMAGES)
                        .check();
            } else {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("\n" +
                                "Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n\nVui lòng bật quyền tại [Cài đặt] > [Quyền]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        }
    }

    private ActivityResultLauncher<Intent> mActivityRequestLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK){
                        Intent data = o.getData();
                        if (data == null){
                            return;
                        }

                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imagePiker.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    public void openImagePicker(){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mActivityRequestLauncher.launch(intent);
    }
}