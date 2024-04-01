package huflit.phn.dulichvietnam.Admin_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdminFragment;
import huflit.phn.dulichvietnam.MainActivity;
import huflit.phn.dulichvietnam.MainActivityHanhTrinhDetail;
import huflit.phn.dulichvietnam.R;
import sanpham.SharedPreferencesManager;


public class AdminActivity extends AppCompatActivity {

    public static final int ROLE_USER = 1;
    Button btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_layout);

        //load fragment admin
        loadFragment(new AdminFragment());

        //Thử nghiệm truyền role_user
//        Bundle bundle = new Bundle();
//        bundle.putInt("1");
//        intent.putExtras(bundle);
        //Kết thúc thử nghiệm (chưa hoàn thành)
        // Kiểm tra vai trò và load fragment tương ứng
        Intent intent = getIntent();
        int role = intent.getIntExtra("role", -1);


        //Thao tác đăng xuất trở về trang chủ
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.getInstance(AdminActivity.this).isLogin = false;
                showConfirmationDialog();//thực hiện hàm mở hộp thoại xác nhận đăng xuất
            }
        });

    }
    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ad_frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn chắc chắn muốn đăng xuất?")
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tạo Intent để chuyển đến MainActivity
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        // Thêm cờ để xóa các activity trên đỉnh của MainActivity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Khởi động MainActivity
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng hộp thoại
                        dialog.dismiss();
                    }
                });
        // Tạo AlertDialog object và hiển thị
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}