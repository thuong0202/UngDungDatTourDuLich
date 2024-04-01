package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import huflit.phn.dulichvietnam.AdTour.AdTour;
import huflit.phn.dulichvietnam.AdTour.AdTour_Activity;
import sanpham.bamien;

public class Oder extends AppCompatActivity {
    EditText edtsdt,edtSoL;
    Button btndat,btnback;
    private final String url = "http://192.168.3.189/server/";
    private String phone,ngayDat,tentour;
    private int soLuong,id_pro,or_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        edtsdt = findViewById(R.id.edt_sdtdattour);
        edtSoL = findViewById(R.id.edt_slngdi);
        btndat = findViewById(R.id.btn_orderTour);
        Intent intent = getIntent();
        id_pro = intent.getIntExtra("proId",-1);
        tentour = intent.getStringExtra("TenTour");
        btnback = findViewById(R.id.back_dattour);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btndat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHang();
    //            Hienthi();

            }
        });
    }

    private void gioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(Oder.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "gioHang.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(Oder.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        // Thực hiện hành động tiếp theo chỉ khi thêm thành công
                        Intent intent = new Intent(Oder.this,ThanhtoanActivity.class);
                        intent.putExtra("ngayDat",ngayDat);
                        startActivity(intent);
                    } else {
                        String errorMessage = jsonResponse.getString("message");
                        showErrorDialog(errorMessage);
                    }
                } catch (JSONException e) {
                    showErrorDialog("Lỗi xử lý dữ liệu JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Lỗi kết nối: " + error.getMessage();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Calendar calendar = Calendar.getInstance();
                Map<String, String> params = new HashMap<>();
                ngayDat = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                phone = edtsdt.getText().toString();
                soLuong = Integer.parseInt(edtSoL.getText().toString());
                params.put("Phone", phone);
                params.put("Pro_id", String.valueOf(id_pro)); // Truyền ProId vào params
                params.put("BookingTour", ngayDat);
                params.put("orderNumber", String.valueOf(soLuong));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Oder.this);
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void Hienthi() {
        RequestQueue requestQueue = Volley.newRequestQueue(Oder.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+ "orders.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        or_id = object.getInt("Or_id");
                        Intent intent= new Intent(Oder.this,OderDetailActivity.class);
                        intent.putExtra("Or_id",or_id);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        // Thêm request vào hàng đợi
        requestQueue.add(jsonArrayRequest);
    }

}
