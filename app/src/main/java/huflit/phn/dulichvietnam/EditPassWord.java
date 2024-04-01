package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditPassWord extends AppCompatActivity {

    private TextInputEditText edt_mkmoi, edt_nlmkmoi;
    private Button btn_doimatkhau,btnback;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass_word);

        edt_mkmoi = findViewById(R.id.edt_mkmoi);
        edt_nlmkmoi = findViewById(R.id.edt_nlmkmoi);
        btn_doimatkhau = findViewById(R.id.btn_doimatkhau);

        btn_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkMoi = edt_mkmoi.getText().toString().trim();
                String nlMkMoi = edt_nlmkmoi.getText().toString().trim();
                if (!mkMoi.isEmpty() && mkMoi.equals(nlMkMoi)) {
                    doiMatKhau(mkMoi);
                } else {
                    Toast.makeText(EditPassWord.this, "Mật khẩu mới không trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnback = findViewById(R.id.btn_back6);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void doiMatKhau(String mkMoi) {
        Toast.makeText(EditPassWord.this, "Mật khẩu đã được thay đổi!", Toast.LENGTH_SHORT).show();
    }

}