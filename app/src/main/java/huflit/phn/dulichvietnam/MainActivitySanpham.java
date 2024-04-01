package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.List;
import java.util.Locale;

import huflit.phn.dulichvietnam.AdTour.AdTour;
import huflit.phn.dulichvietnam.AdTour.AdTour_Activity;
import sanpham.SharedPreferencesManager;
import sanpham.bamien;
import sanpham.sanphamAdapter;


public class MainActivitySanpham extends AppCompatActivity implements View.OnClickListener {
    private ListView rcvsp;
    private Button btnmb, btnmt, btnmn;
    private sanphamAdapter SanphamAdapter;
    private MainActivity mainActivity;
    private ImageButton btn_thoat;
    private SearchView searchView;
    private ArrayList<bamien> sanphamList;
    private ArrayList<String> search;

    private final String url = "http://192.168.3.189/server/";
    private int id_cate, id_pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sanpham);
        addControls();


        btnmb = findViewById(R.id.btn_mienbac);
        btnmt = findViewById(R.id.btn_mientrung);
        btnmn = findViewById(R.id.btn_miennam);
        btn_thoat = findViewById(R.id.btn_home);

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySanpham.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set adapter to ListView
        rcvsp = findViewById(R.id.Lv_sanpham);

        // Set event listeners
        addEvents();
        // Load data initially
        LoadData();

        // Set click listener for buttons
        btnmb.setOnClickListener(this);
        btnmt.setOnClickListener(this);
        btnmn.setOnClickListener(this);
        Hienthi();
        sanphamList = new ArrayList<bamien>();
        search = new ArrayList<String>();
        SanphamAdapter = new sanphamAdapter(MainActivitySanpham.this, R.layout.item_sanpham, sanphamList);
        rcvsp.setAdapter(SanphamAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {
        Hienthi();
    }

    private void addControls() {
        rcvsp = findViewById(R.id.Lv_sanpham);
        SanphamAdapter = new sanphamAdapter(this, R.layout.item_sanpham, new ArrayList<bamien>());
        rcvsp.setAdapter(SanphamAdapter);
        searchView = findViewById(R.id.search_view);
    }

    // tìm kiếm
    private void addEvents() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                timKiem(query); // Gọi hàm tìm kiếm khi người dùng nhấn nút tìm kiếm trên bàn phím
                Toast.makeText(MainActivitySanpham.this, query, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                timKiem(newText);
                return true;
            }
        });
//

        rcvsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bamien selectedBamien = SanphamAdapter.getItem(position);
                if (selectedBamien != null) {
                    id_pro = selectedBamien.getProId();
                    Intent intent = new Intent(MainActivitySanpham.this, MainActivityHanhTrinhDetail.class);
                    // Truyền dữ liệu cần thiết tới MainActivityHanhTrinhDetail nếu cần
                    intent.putExtra("selected_bamien", selectedBamien);
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_mientrung) {
            id_cate = 1; // Cập nhật id_cate cho miền Trung
        } else if (v.getId() == R.id.btn_mienbac) {
            id_cate = 2; // Cập nhật id_cate cho miền Bắc
        } else if (v.getId() == R.id.btn_miennam) {
            id_cate = 3; // Cập nhật id_cate cho miền Nam
        }
        Hienthi();
    }

    private void Hienthi() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivitySanpham.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "SanPham.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách sanphamList
                sanphamList.clear();
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int _id = object.getInt("Cate_id");
                        if (id_cate == 0 || _id == id_cate) {
                            {
                                // Lấy giá trị của trường Pro_id
                                id_pro = object.getInt("Pro_id");
                                String tentour = object.getString("Pro_Name");
                                String diaChi = object.getString("Location");
                                String moTa = object.getString("Desciption");
                                int slNguoiDi = object.getInt("Number_Of_People_On_Tour");
                                double gia = object.getDouble("Price");
                                String path = object.getString("Pro_Path");
                                String ngayDi = object.getString("Departure_Dates");
                                String ngayVe = object.getString("Return_Date");
                                String khacSan = object.getString("Hotel");
                                String huongDanVien = object.getString("Coach_Name");

                                bamien baMien = new bamien();
                                baMien.setPro_name(tentour);
                                baMien.setAddress(diaChi);
                                baMien.setDes(moTa);
                                baMien.setId_Cate(_id);
                                baMien.setNumber(slNguoiDi);
                                baMien.setPro_image(path);
                                baMien.setNgay_di(ngayDi);
                                baMien.setNgay_ve(ngayVe);
                                baMien.setPrice(gia);
                                baMien.setHotel(khacSan);
                                baMien.setTenHuongDanVien(huongDanVien);
                                baMien.setProId(id_pro);
                                Log.e("JSON", "onResponse: " + baMien.toString());
                                search.add(tentour);
                                sanphamList.add(baMien);
                            }
                        }
                    }
                    // Notify the adapter about the changes in data
                    SanphamAdapter.updateData(sanphamList);
                } catch (JSONException e) {
                    showAlertDialog("Loi", e.getMessage());
                    Log.e("JSONError", "Error parsing JSON response: " + e.getMessage());
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


    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void timKiem(String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivitySanpham.this);
        // Định dạng URL cho việc tìm kiếm dựa trên từ khóa query
        // Định dạng URL cho việc tìm kiếm dựa trên từ khóa query
        String searchUrl = url + "Search.php?search=" + query;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, searchUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Xóa dữ liệu cũ trong danh sách sản phẩm
                            sanphamList.clear();
                            // Duyệt qua từng sản phẩm trả về từ API
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                id_pro = object.getInt("Pro_id");

                                String proName = object.getString("Pro_Name");
                                String diaChi = object.getString("Location");
                                String moTa = object.getString("Desciption");
                                int slNguoiDi = object.getInt("Number_Of_People_On_Tour");
                                double gia = object.getDouble("Price");
                                String path = object.getString("Pro_Path");
                                String ngayDi = object.getString("Departure_Dates");
                                String ngayVe = object.getString("Return_Date");
                                String khacSan = object.getString("Hotel");
                                String huongDanVien = object.getString("Coach_Name");

                                bamien baMien = new bamien();
                                baMien.setPro_name(proName);
                                baMien.setAddress(diaChi);
                                baMien.setDes(moTa);
                                baMien.setNumber(slNguoiDi);
                                baMien.setPro_image(path);
                                baMien.setNgay_di(ngayDi);
                                baMien.setNgay_ve(ngayVe);
                                baMien.setPrice(gia);
                                baMien.setHotel(khacSan);
                                baMien.setTenHuongDanVien(huongDanVien);

                                // Thêm sản phẩm vào danh sách sản phẩm
                                sanphamList.add(baMien);
                            }

                            // Hiển thị các sản phẩm tìm kiếm lên đầu danh sách
                            List<bamien> searchedItems = new ArrayList<>();
                            List<bamien> remainingItems = new ArrayList<>();
                            for (bamien item : sanphamList) {
                                if (item.getPro_name().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                                    searchedItems.add(item);
                                } else {
                                    remainingItems.add(item);
                                }
                            }
                            searchedItems.addAll(remainingItems);
                            sanphamList.clear();
                            sanphamList.addAll(searchedItems);

                            // Cập nhật dữ liệu lên adapter
                            SanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }


}