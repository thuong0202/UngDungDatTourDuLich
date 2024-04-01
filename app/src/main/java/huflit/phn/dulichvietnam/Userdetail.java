package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Userdetail extends AppCompatActivity {

    Button btnback,btncapnhap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);

        btnback = findViewById(R.id.btn_back5);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btncapnhap = findViewById(R.id.btn_capnhap);
        btncapnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtHoTen = findViewById(R.id.edt_Suafullname);
                EditText edtSoDienThoai = findViewById(R.id.edt_suaSdt);
                EditText edtGioiTinh = findViewById(R.id.edt_Suagioitinh);
                EditText edtNgaySinh = findViewById(R.id.edt_Suangaysinh);

                String hoTen = edtHoTen.getText().toString();
                String soDienThoai = edtSoDienThoai.getText().toString();
                String gioiTinh = edtGioiTinh.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();

                if (soDienThoai.length() != 10) {
                    Toast.makeText(Userdetail.this, "Số điện thoại phải có đúng 10 số", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hoTen.length() < 8) {
                    Toast.makeText(Userdetail.this, "Họ tên phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!gioiTinh.equalsIgnoreCase("nam") && !gioiTinh.equalsIgnoreCase("nữ")) {
                    Toast.makeText(Userdetail.this, "Giới tính chỉ được nhập 'nam' hoặc 'nữ'", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ngaySinh.matches("\\d{2}/\\d{2}/\\d{4}")) {

                    Toast.makeText(Userdetail.this, "Ngày sinh phải nhập theo định dạng dd/mm/yyyy.Ví Dụ : 20/06/2004", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(Userdetail.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                Fragment newFragment = new caidat();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}