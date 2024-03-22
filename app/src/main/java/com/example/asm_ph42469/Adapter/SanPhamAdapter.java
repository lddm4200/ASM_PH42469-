package com.example.asm_ph42469.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_ph42469.Api.ApiService;
import com.example.asm_ph42469.Home;
import com.example.asm_ph42469.Modal.SanPham;
import com.example.asm_ph42469.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.viewHolder> {
    private final Context context;
    private List<SanPham> list;
    Home trangChu;

    public SanPhamAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
        trangChu=(Home) context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sanpham,parent,false);
        return new viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (position >= 0 && position <= list.size()){
            SanPham sv = list.get(position);

            holder.txtMaSp.setText("Mã SP: "+sv.getMaSp());
            holder.txtTenSp.setText("Tên SP: "+sv.getTenSp());
            holder.txtGia.setText("Giá: "+sv.getGia());
            holder.txtSoLuong.setText("So Lượng: "+sv.getSoLuong());

            Glide.with(holder.itemView.getContext())
                    .load(sv.getAnh())
                    .into(holder.imgAvatar);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idSanPham = sv.get_id();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn có muốn xóa không?");
                    builder.setNegativeButton("No",null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiService.apiService.deleteSanPham(idSanPham).enqueue(new Callback<SanPham>() {
                                @Override
                                public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                                        list.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SanPham> call, Throwable t) {
                                    Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trangChu.showDialog(context,sv,0,list);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0){
            return list.size();
        }
        return 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSp, txtGia, txtSoLuong,txtMaSp;
        ImageView imgAvatar;
        ImageButton btnUpdate,btnDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSp = itemView.findViewById(R.id.txtMaSp);
            txtTenSp = itemView.findViewById(R.id.txtTenSp);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
