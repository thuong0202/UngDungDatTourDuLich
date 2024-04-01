package huflit.phn.dulichvietnam.AdTour;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import huflit.phn.dulichvietnam.R;

public class AdTourAdapater extends ArrayAdapter<AdTour> {
    private Context mContext;
    private int mResource;

    public AdTourAdapater(Context context, int resource, ArrayList<AdTour> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdTour adTour = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.tour_mienbac);
        TextView tvDestinationName = convertView.findViewById(R.id.tv_sanpham);
        TextView tvPrice = convertView.findViewById(R.id.tv_gia);
        TextView tvAddress = convertView.findViewById(R.id.tv_hienthigia);

        // Đặt tên điểm đến
        tvDestinationName.setText(adTour.getPro_name());
        // Đặt địa chỉ
        tvAddress.setText(adTour.getAddress());
        if (adTour.getPrice() != 0) {
            tvPrice.setText(String.valueOf(adTour.getPrice()));
        }

        String imageUrl = adTour.getPro_image();
        Glide.with(mContext)
                .load(imageUrl)
                .into(imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HienThi(adTour);
            }
        });

        return convertView;
    }

    private void HienThi(AdTour ad) {
        if (ad == null) {
            return; // Đảm bảo rằng ad không null trước khi sử dụng nó
        }

        // Đảm bảo rằng mContext không null
        if (mContext == null) {
            return;
        }

        Activity activity = (Activity) mContext;
        View itemView = activity.findViewById(android.R.id.content);

        EditText edtThemAnh = itemView.findViewById(R.id.edt_themanh);
        EditText edtDiaDiem = itemView.findViewById(R.id.edt_diadiem);
        EditText edtGia = itemView.findViewById(R.id.edt_gia);
        EditText edtDiaChi = itemView.findViewById(R.id.edt_diachi);
        EditText edtNgayDi = itemView.findViewById(R.id.edt_ngaydi);
        EditText edtNgayVe = itemView.findViewById(R.id.edt_ngayve);
        EditText edtKhach = itemView.findViewById(R.id.edt_khachsan);
        EditText edtMoTa = itemView.findViewById(R.id.edt_mota);
        EditText edtNguoiDi = itemView.findViewById(R.id.edt_nguoidi);
        EditText edtma = itemView.findViewById(R.id.edtLichTrinh);
        // Truy cập vào Spinner thứ nhất
        Spinner hdv = itemView.findViewById(R.id.spinner_guide_names);

// Lấy ra Adapter của Spinner thứ nhất
        ArrayAdapter<AdTour> adapter1 = (ArrayAdapter<AdTour>) hdv.getAdapter();

        int position1 = adapter1.getPosition(ad);

        hdv.setSelection(position1);

        Spinner cate = itemView.findViewById(R.id.spinner_category);

        ArrayAdapter<AdTour> adapter2 = (ArrayAdapter<AdTour>) cate.getAdapter();

        int position2 = adapter2.getPosition(ad);

        cate.setSelection(position2);


        // Kiểm tra nếu các trường của đối tượng ad null trước khi đặt giá trị cho EditText
        if (ad.getPro_image() != null) {
            edtThemAnh.setText(ad.getPro_image());
        }
        if (ad.getId_SC() != 0) {
            edtma.setText(String.valueOf(ad.getId_SC()));
            //edtma.setText(ad.getId_SC());
        }
        if (ad.getPro_name() != null) {
            edtDiaDiem.setText(ad.getPro_name());
        }
        if (ad.getPrice() != 0) {
            edtGia.setText(String.valueOf(ad.getPrice()));
        }
        if (ad.getAddress() != null) {
            edtDiaChi.setText(ad.getAddress());
        }
        if (ad.getNgay_di() != null) {
            edtNgayDi.setText(ad.getNgay_di());
        }
        if (ad.getNgay_ve() != null) {
            edtNgayVe.setText(ad.getNgay_ve());
        }
        if (ad.getHotel() != null) {
            edtKhach.setText(ad.getHotel());
        }
        if (ad.getDes() != null) {
            edtMoTa.setText(ad.getDes());
        }
        if (ad.getNumber() != 0) {
            edtNguoiDi.setText(String.valueOf(ad.getNumber()));
        }
    }
}
