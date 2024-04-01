    package huflit.phn.dulichvietnam;

    import static java.security.AccessController.getContext;


    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import android.annotation.SuppressLint;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.provider.ContactsContract;
    import android.text.Html;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonArrayRequest;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.bumptech.glide.Glide;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;

    import huflit.phn.dulichvietnam.AdTour.AdTour;
    import huflit.phn.dulichvietnam.AdTour.AdTour_Activity;
    import huflit.phn.dulichvietnam.Admin_layout.AdminActivity;
    import sanpham.SharedPreferencesManager;
    import sanpham.bamien;


    public class MainActivityHanhTrinhDetail extends AppCompatActivity {
        ImageView imgSp;
        TextView tvSp, tvgia;
        Intent intent = null;
        private ImageButton btn_thoat;
        Button btndattour;
        SaveState saveState;
        LoginFragment lg;
        private String phone;
        private final String url = "http://192.168.3.189/server/";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_hanh_trinh_detail);
            saveState = new SaveState(this);
            addControls();
            bamien bm = new bamien();
            btn_thoat = findViewById(R.id.btn_home);
            lg = new LoginFragment();
            btndattour = findViewById(R.id.btn_dattour);
            btndattour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmationDialog();
                }
            });
            btn_thoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivityHanhTrinhDetail.this, MainActivitySanpham.class);
                    startActivity(intent);
                    finish();
                }
            });

        }

        private void addControls() {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("selected_bamien")) {
                bamien selectedBamien = (bamien) intent.getSerializableExtra("selected_bamien");
                ImageView imgSp = findViewById(R.id.anhsanpham);
                Glide.with(this)
                        .load(selectedBamien.getPro_image())
                        .into(imgSp);
                TextView tvDiaDiem = findViewById(R.id.tv_diaDiem);
                TextView tvGia = findViewById(R.id.tv_gia);
                TextView tvDiaChi = findViewById(R.id.tv_diaChi);
                TextView tvNgayDi = findViewById(R.id.tv_ngaydi);
                TextView tvNgayVe = findViewById(R.id.tv_ngayve);
                TextView tvKhacSan = findViewById(R.id.tv_khachsan);
                TextView tvMoTa = findViewById(R.id.tv_Mota);
                TextView tv_Hdv = findViewById(R.id.tv_HDV);
                TextView tv_soLuongNguoi = findViewById(R.id.tv_nguoidi);
                tvDiaDiem.setText(selectedBamien.getPro_name());
                tvGia.setText(String.valueOf(selectedBamien.getPrice()));
                tvDiaChi.setText(selectedBamien.getAddress());
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    // Lấy ngày dưới dạng chuỗi từ đối tượng bamien
                    String ngayDiStr = selectedBamien.getNgay_di();
                    String ngayVeStr = selectedBamien.getNgay_ve();

                    // Chuyển đổi chuỗi thành đối tượng Date
                    Date ngayDiDate = inputFormat.parse(ngayDiStr);
                    Date ngayVeDate = inputFormat.parse(ngayVeStr);

                    // Chuyển đổi đối tượng Date sang chuỗi theo định dạng mới
                    String ngayDiFormatted = outputFormat.format(ngayDiDate);
                    String ngayVeFormatted = outputFormat.format(ngayVeDate);

                    // Đặt giá trị mới cho TextView
                    tvNgayDi.setText(ngayDiFormatted);
                    tvNgayVe.setText(ngayVeFormatted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvKhacSan.setText(selectedBamien.getHotel());
                tvMoTa.setText(selectedBamien.getDes());
                tv_Hdv.setText(selectedBamien.getTenHuongDanVien());
                tv_soLuongNguoi.setText(String.valueOf(selectedBamien.getNumber()));
            }
        }

        private void showConfirmationDialog() {
            //if (saveState.Check())
            {
                //boolean ktra = saveState.Check();

                // Người dùng đã đăng nhập, tiến hành đặt tour
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xác nhận đặt tour");
                builder.setMessage(Html.fromHtml(
                        "1. Phải cọc trước 1.000.000<br>"
                ));
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (SharedPreferencesManager.getInstance(MainActivityHanhTrinhDetail.this).isLogin) {
                            //Toast.makeText(MainActivityHanhTrinhDetail.this, "true", Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            Intent od = new Intent(MainActivityHanhTrinhDetail.this, Oder.class);
                            if (intent != null && intent.hasExtra("selected_bamien")) {
                                bamien selectedBamien = (bamien) intent.getSerializableExtra("selected_bamien");
                                od.putExtra("proId",selectedBamien.getProId());
                                od.putExtra("TenTour",selectedBamien.getPro_name());
                            }
                            startActivity(od);

                        } else {
                            Toast.makeText(MainActivityHanhTrinhDetail.this, "Vui lòng đăng nhập", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivityHanhTrinhDetail.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }


