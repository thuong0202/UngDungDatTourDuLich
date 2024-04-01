package sanpham;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.ReflectionAccessFilter.FilterResult;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import huflit.phn.dulichvietnam.AdTour.AdTour;
import huflit.phn.dulichvietnam.R;

public class sanphamAdapter extends ArrayAdapter<bamien> implements Filterable {
    private Activity context;
    private int resource;
    private List<bamien> sanphamList;
    private List<bamien> sanphamListOld;

    public sanphamAdapter(@NonNull Activity context, int resource,List<bamien> sanphamList )  {
        super(context, resource,sanphamList);
        this.context = context;
        this.resource = resource;
        this.sanphamList = sanphamList;
        this.sanphamListOld= sanphamList;
    }

    public void updateData(List<bamien> listProduct) {
        this.sanphamList = listProduct;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customView = layoutInflater.inflate(resource, parent, false);
        if (sanphamList.size()>0 && position>=0)
        {
            ImageView ivHinh = customView.findViewById(R.id.tour_mienbac);
            TextView tvSp = customView.findViewById(R.id.tv_sanpham);
            TextView tvDg = customView.findViewById(R.id.tv_hienthigia);
            TextView tvgia = customView.findViewById(R.id.tv_gia);

            bamien bm = sanphamList.get(position);
            String imageUrl = bm.getPro_image();

            tvSp.setText(bm.getPro_name());
            tvDg.setText(bm.getAddress());
            if (bm.getPrice() != 0) {
                tvgia.setText(String.valueOf(bm.getPrice()));
            }

            Glide.with(context)
                    .load(imageUrl)
                    .into(ivHinh);



        }
        return customView;
    }



    // tìm kiếm
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<bamien> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // Nếu không có ràng buộc hoặc độ dài của ràng buộc là 0,
                    // trả về danh sách sản phẩm ban đầu
                    filterResults.count = sanphamListOld.size();
                    filterResults.values = sanphamListOld;
                } else {
                    String filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim();
                    for (bamien item : sanphamListOld) {
                        // Tìm kiếm pro_name chứa chuỗi ràng buộc
                        if (item.getPro_name().toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Cập nhật cả danh sách sản phẩm và danh sách gốc
                sanphamList.clear();
                sanphamList.addAll((List<bamien>) results.values);
                notifyDataSetChanged();
            }
        };
    }



    private void HienThi(bamien bamien) {
        if (bamien == null) {
            return; // Đảm bảo rằng ad không null trước khi sử dụng nó
        }

        // Đảm bảo rằng mContext không null
        if (context == null) {
            return;
        }

        Activity activity = (Activity) context;
        View itemView = activity.findViewById(android.R.id.content);

        TextView edtDiaDiem = itemView.findViewById(R.id.tv_diaDiem);
        TextView edtGia = itemView.findViewById(R.id.tv_gia);
        TextView edtDiaChi = itemView.findViewById(R.id.tv_diaChi);
        TextView edtNgayDi = itemView.findViewById(R.id.tv_ngaydi);
        TextView edtNgayVe = itemView.findViewById(R.id.tv_ngayve);
        TextView edtKhach = itemView.findViewById(R.id.tv_khachsan);
        TextView edtMoTa = itemView.findViewById(R.id.tv_Mota);
        TextView edtNguoiDi = itemView.findViewById(R.id.tv_nguoidi);
        TextView tv_HuongDanVien = itemView.findViewById(R.id.tv_HDV);
        if (bamien.getPro_name() != null) {
            edtDiaDiem.setText(bamien.getPro_name());
        }
        if (bamien.getPrice() != 0) {
            edtGia.setText(String.valueOf(bamien.getPrice()));
        }
        if (bamien.getAddress() != null) {
            edtDiaChi.setText(bamien.getAddress());
        }
        if (bamien.getNgay_di() != null) {
            edtNgayDi.setText(bamien.getNgay_di());
        }
        if (bamien.getNgay_ve() != null) {
            edtNgayVe.setText(bamien.getNgay_ve());
        }
        if (bamien.getHotel() != null) {
            edtKhach.setText(bamien.getHotel());
        }
        if (bamien.getDes() != null) {
            edtMoTa.setText(bamien.getDes());
        }
        if (bamien.getNumber() != 0) {
            edtNguoiDi.setText(String.valueOf(bamien.getNumber()));
        }if (bamien.getTenHuongDanVien()!= null)
        {
            tv_HuongDanVien.setText(bamien.getTenHuongDanVien());
        }
    }

}
