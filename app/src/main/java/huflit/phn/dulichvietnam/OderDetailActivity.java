package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import OrderDetail.Order;
import OrderDetail.OrderAdapter;
import huflit.phn.dulichvietnam.AdTour.AdTour;
import huflit.phn.dulichvietnam.AdTour.AdTour_Activity;
import sanpham.bamien;

public class OderDetailActivity extends AppCompatActivity {

    private EditText edtFullName, edtGender, edtAge, edtPhoneNumber;
    private Button btnAdd,btnEdit,btnDelete,btnback,btndattour,medtOrder;
    private ListView lvAdngdi;
    private ArrayList<Order> orderList;
    private OrderAdapter orderAdapter;
    private String url  ;
    private int or_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_detail);

        edtFullName = findViewById(R.id.edt_ten);
        edtGender = findViewById(R.id.edt_gt);
        edtAge = findViewById(R.id.edt_tuoi);
        edtPhoneNumber = findViewById(R.id.edt_sdthoai);
        btnAdd = findViewById(R.id.btn_them);
        lvAdngdi = findViewById(R.id.lvAd_ngdi);

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        lvAdngdi.setAdapter(orderAdapter);
        btnback = findViewById(R.id.btn_back3);
        btndattour = findViewById(R.id.btn_dattour4);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString();
                String gender = edtGender.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());
                String phoneNumber = edtPhoneNumber.getText().toString();
                Intent intent = getIntent();
                int soLuong = intent.getIntExtra("SO_LUONG", 0);
                int maLichTrinh = intent.getIntExtra("Ma_LicH_Trinh",0);
                // Kiểm tra số lượng đơn hàng
//                if (orderList.size() < soLuong) {
//                    Order newOrder = new Order(fullName, gender, age, phoneNumber);
//                    orderList.add(newOrder);
//                    orderAdapter.notifyDataSetChanged();
//                } else {
//                    // Hiển thị thông báo hoặc xử lý khi vượt quá số lượng cho phép
//                    Toast.makeText(getApplicationContext(), "Đã đạt tới số lượng tối đa cho phép.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btndattour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OderDetailActivity.this, ThanhtoanActivity.class);
                startActivity(intent);
            }
        });
    }


}