package huflit.phn.dulichvietnam.AdTour;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel.AdTravel;
import huflit.phn.dulichvietnam.MainActivity;
import huflit.phn.dulichvietnam.MainActivityHanhTrinhDetail;
import huflit.phn.dulichvietnam.Oder;
import huflit.phn.dulichvietnam.OderDetailActivity;
import huflit.phn.dulichvietnam.Order;
import huflit.phn.dulichvietnam.R;
import sanpham.SharedPreferencesManager;

public class AdTour_Activity extends AppCompatActivity {

    private EditText edtDestinationName, edtPrice, edtAddress,medtThemAnh,medt_mota,medt_nguoidi,medt_ngaydi,medt_ngayve,medt_khachsan,medtLichTrinh;
    private Button btnAdd,btnback,mbtnedit,mbtnxoa;
    private ListView lvAdTour;
    private ArrayList<AdTour> adTourList;
    private ArrayList<AdTour> scheduleList;
    private AdTourAdapater adTourAdapter;
    private ImageView img;
    private String name,image,location,address,des,date_di,date_ve,hotel;
    private double price;
    private int number,id_pro,id_Sc,intMaLichTinh,id_Coach,id_cate;
    private Spinner spinnerGuideNames,spinnerCate;
    private List<Integer> guideNames = new ArrayList<>();
    private ArrayAdapter<Integer> adapter;
    private List<Integer> category = new ArrayList<>();
    private ArrayAdapter<Integer> mangCate;
    private final static String url = "http://192.168.3.189/server/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_tour);

        edtDestinationName = findViewById(R.id.edt_diadiem);
        edtPrice = findViewById(R.id.edt_gia);
        edtAddress = findViewById(R.id.edt_diachi);
        medtThemAnh = findViewById(R.id.edt_themanh);
        medt_mota = findViewById(R.id.edt_mota);
        medt_nguoidi = findViewById(R.id.edt_nguoidi);
        medt_ngaydi = findViewById(R.id.edt_ngaydi);
        medt_ngayve = findViewById(R.id.edt_ngayve);
        medt_khachsan = findViewById(R.id.edt_khachsan);
        medtLichTrinh = findViewById(R.id.edtLichTrinh);
        btnAdd = findViewById(R.id.btn_add);
        mbtnedit = findViewById(R.id.btn_edit);
        mbtnxoa = findViewById(R.id.btn_delete);
        lvAdTour = findViewById(R.id.lvAd_tour);
        spinnerGuideNames = findViewById(R.id.spinner_guide_names);
        spinnerCate = findViewById(R.id.spinner_category);
        AdTour ad = new AdTour();
        medt_ngaydi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(medt_ngaydi);
            }
        });
        medt_ngayve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(medt_ngayve);
            }
        });
        // Initialize ArrayList and Adapter
        adTourList = new ArrayList<>();
        adTourAdapter = new AdTourAdapater(this, R.layout.item_sanpham, adTourList);
        spinnerGuideNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy coach_id từ mục được chọn
                id_Coach = (int) parent.getItemAtPosition(position);
                // Hiển thị thông báo (có thể thay thế bằng xử lý khác)
                Toast.makeText(getApplicationContext(), "Bạn đã chọn coach_id: " + id_Coach, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mangCate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        mangCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCate.setAdapter(mangCate);

        spinnerCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_cate = category.get(position); // Lấy ID của danh mục đã chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });
        lvAdTour.setAdapter(adTourAdapter);
        btnback= findViewById(R.id.btn_back4);
        Hienthi_Coach();
        Hienthi_Cate();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdTour_Activity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferencesManager.getInstance(AdTour_Activity.this).isLogin = false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                name = edtDestinationName.getText().toString().trim();
                String priceStr = edtPrice.getText().toString().trim();
                location = edtAddress.getText().toString().trim();
                des = medt_mota.getText().toString().trim();
                String numberStr = medt_nguoidi.getText().toString().trim();
                image = medtThemAnh.getText().toString().trim();
                date_di = medt_ngaydi.getText().toString().trim();
                date_ve = medt_ngayve.getText().toString().trim();
                hotel = medt_khachsan.getText().toString().trim();
                showDatePickerDialog(medt_ngaydi);
                showDatePickerDialog(medt_ngayve);
                Hienthi_Coach();
                Hienthi_Cate();
                String intMaLichTinhStr = medtLichTrinh.getText().toString().trim();

                // Kiểm tra các trường nhập liệu có rỗng không
                if (name.isEmpty() || priceStr.isEmpty() || location.isEmpty() || des.isEmpty() ||
                        numberStr.isEmpty() || image.isEmpty() || date_di.isEmpty() || date_ve.isEmpty() ||
                        hotel.isEmpty() || intMaLichTinhStr.isEmpty()) {
                    // Hiển thị thông báo nếu có trường nhập liệu rỗng
                    Toast.makeText(AdTour_Activity.this,    "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Thoát khỏi phương thức nếu có trường rỗng
                }

                // Chuyển đổi chuỗi thành số
                try {
                    price = Double.parseDouble(priceStr);
                    number = Integer.parseInt(numberStr);
                    intMaLichTinh = Integer.parseInt(intMaLichTinhStr);
                } catch (NumberFormatException e) {
                    // Xử lý nếu không thể chuyển đổi thành số
                    showErrorDialog("Invalid input for price, number of people, or schedule ID");
                    return; // Thoát khỏi phương thức nếu có lỗi chuyển đổi
                }
                if (isDepartureBeforeReturn(date_di, date_ve)) {
                    themLichTrinh(url);
                    them_sanPham(url);
                    Hienthi(url);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdTour_Activity.this);
                    builder.setMessage("Ngày đi phải trước ngày về")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Đóng dialog nếu người dùng nhấn OK
                                    dialog.dismiss();
                                }
                            });
                    // Tạo và hiển thị dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


                // Reset các trường nhập liệu sau khi đã thêm thành công

                edtDestinationName.setText("");
                edtPrice.setText("");
                edtAddress.setText("");
                medt_mota.setText("");
                medt_nguoidi.setText("");
                medtThemAnh.setText("");
                medt_ngaydi.setText("");
                medt_ngayve.setText("");
                medt_khachsan.setText("");
                medtLichTrinh.setText("");
            }
        });
        Hienthi(url);
        Hienthi_Coach();
        Hienthi_Cate();
        // Trong hàm onCreate của lớp AdTour_Activity
        mbtnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_Pro(id_pro);
                Delete_Sc(id_Sc);
                Hienthi(url);
                edtDestinationName.setText("");
                edtPrice.setText("");
                edtAddress.setText("");
                medt_mota.setText("");
                medt_nguoidi.setText("");
                medtThemAnh.setText("");
                medt_ngaydi.setText("");
                medt_ngayve.setText("");
                medt_khachsan.setText("");
                adTourAdapter.notifyDataSetChanged();
            }

        });
        mbtnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                name = edtDestinationName.getText().toString().trim();
                String priceStr = edtPrice.getText().toString().trim();
                location = edtAddress.getText().toString().trim();
                des = medt_mota.getText().toString().trim();
                String numberStr = medt_nguoidi.getText().toString().trim();
                image = medtThemAnh.getText().toString().trim();
                date_di = medt_ngaydi.getText().toString().trim();
                date_ve = medt_ngayve.getText().toString().trim();
                hotel = medt_khachsan.getText().toString().trim();
                String intMaLichTinhStr = medtLichTrinh.getText().toString().trim();

                // Kiểm tra các trường nhập liệu có rỗng không
                if (name.isEmpty() || priceStr.isEmpty() || location.isEmpty() || des.isEmpty() ||
                        numberStr.isEmpty() || image.isEmpty() || date_di.isEmpty() || date_ve.isEmpty() ||
                        hotel.isEmpty() || intMaLichTinhStr.isEmpty()) {
                    // Hiển thị thông báo nếu có trường nhập liệu rỗng
                    showErrorDialog("Please fill in all fields");
                    return; // Thoát khỏi phương thức nếu có trường rỗng
                }

                // Chuyển đổi chuỗi thành số
                try {
                    price = Double.parseDouble(priceStr);
                    number = Integer.parseInt(numberStr);
                    intMaLichTinh = Integer.parseInt(intMaLichTinhStr);
                } catch (NumberFormatException e) {
                    // Xử lý nếu không thể chuyển đổi thành số
                    String errorMessage = "Invalid input for price, number of people, or schedule ID: " + e.getMessage();
                    showErrorDialog(errorMessage);
                    return; // Thoát khỏi phương thức nếu có lỗi chuyển đổi
                }

                if (isDepartureBeforeReturn(date_di, date_ve)) {
                    CapNhat("product", id_pro);
                    CapNhat("schedule", id_Sc);
                    Hienthi(url);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdTour_Activity.this);
                    builder.setMessage("Ngày đi phải trước ngày về")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Đóng dialog nếu người dùng nhấn OK
                                    dialog.dismiss();
                                }
                            });
                    // Tạo và hiển thị dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        lvAdTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Lấy đối tượng AdTour tương ứng với vị trí được click
                AdTour adTour = adTourAdapter.getItem(position);
                // Hiển thị thông tin từ AdTour được chọn
                medtThemAnh.setText(adTour.getPro_image());
                medtLichTrinh.setText(adTour.getId_SC());
                edtDestinationName.setText(adTour.getPro_name());
                edtPrice.setText(String.valueOf(adTour.getPrice()));
                edtAddress.setText(adTour.getAddress());
                medt_mota.setText(adTour.getDes());
                medt_nguoidi.setText(String.valueOf(adTour.getNumber()));
                medt_ngaydi.setText(adTour.getNgay_di());
                medt_ngayve.setText(adTour.getNgay_ve());
                medt_khachsan.setText(adTour.getHotel());

                Hienthi(url);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, guideNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuideNames.setAdapter(adapter);
        spinnerGuideNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy tên hướng dẫn viên được chọn
                String selectedGuide = parent.getItemAtPosition(position).toString();
                // Hiển thị thông báo (có thể thay thế bằng xử lý khác)
                Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + selectedGuide, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có gì được chọn
            }
        });
    }

    //thêm sản phẩm
    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdTour_Activity.this);
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

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AdTour_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Khi người dùng chọn ngày, tháng và năm
                // Lấy các giá trị và kết hợp chúng thành chuỗi có định dạng dd/mm/yyyy
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);

                // Kiểm tra xem ngày nào đang được chọn (ngày đi hoặc ngày về)
                if (editText.getId() == R.id.edt_ngaydi) {
                    // Nếu là EditText ngày đi, chỉ cập nhật ngày đi
                    editText.setText(formattedDate);
                } else if (editText.getId() == R.id.edt_ngayve) {
                    // Nếu là EditText ngày về, kiểm tra ngày về phải sau ngày đi
                    String departureDateStr = medt_ngaydi.getText().toString().trim();
                    if (!departureDateStr.isEmpty()) {
                        // Chuyển đổi chuỗi ngày đi sang đối tượng Calendar
                        Calendar departureDate = Calendar.getInstance();
                        departureDate.set(year, month, dayOfMonth);

                        // Chuyển đổi chuỗi ngày về sang đối tượng Calendar
                        Calendar returnDate = Calendar.getInstance();
                        String[] returnDateParts = formattedDate.split("/");
                        returnDate.set(Integer.parseInt(returnDateParts[2]), Integer.parseInt(returnDateParts[1]) - 1, Integer.parseInt(returnDateParts[0]));

                        // So sánh ngày về với ngày đi
                        if (returnDate.before(departureDate)) {
                            // Nếu ngày về trước ngày đi, hiển thị thông báo và không cập nhật EditText ngày về
                            Toast.makeText(AdTour_Activity.this, "Ngày về phải sau ngày đi", Toast.LENGTH_SHORT).show();
                        } else {
                            // Ngược lại, cập nhật EditText ngày về
                            editText.setText(formattedDate);
                        }
                    } else {
                        // Nếu ngày đi chưa được chọn, yêu cầu người dùng chọn ngày đi trước
                        Toast.makeText(AdTour_Activity.this, "Vui lòng chọn ngày đi trước", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private boolean isDepartureBeforeReturn(String departureDateStr, String returnDateStr) {
        // Kiểm tra xem cả ngày đi và ngày về có giá trị không rỗng
        if (!departureDateStr.isEmpty() && !returnDateStr.isEmpty()) {
            // Chuyển đổi chuỗi ngày đi sang đối tượng Calendar
            Calendar departureDate = Calendar.getInstance();
            String[] departureDateParts = departureDateStr.split("/");
            departureDate.set(Integer.parseInt(departureDateParts[2]), Integer.parseInt(departureDateParts[1]) - 1, Integer.parseInt(departureDateParts[0]));

            // Chuyển đổi chuỗi ngày về sang đối tượng Calendar
            Calendar returnDate = Calendar.getInstance();
            String[] returnDateParts = returnDateStr.split("/");
            returnDate.set(Integer.parseInt(returnDateParts[2]), Integer.parseInt(returnDateParts[1]) - 1, Integer.parseInt(returnDateParts[0]));

            // So sánh ngày về với ngày đi
            return returnDate.after(departureDate);
        }
        // Trả về false nếu có bất kỳ ngày nào là rỗng
        return false;
    }

    private void themLichTrinh(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "Add_Sc.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(AdTour_Activity.this, "Them thanh cong", Toast.LENGTH_LONG).show();

                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message"); // Lấy thông điệp lỗi từ server
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
                showErrorDialog(errorMessage);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("SC_id", String.valueOf(intMaLichTinh));
                params.put("Location", location);
                params.put("Departure_Dates", date_di);
                params.put("Return_Date", date_ve);
                params.put("Hotel", hotel);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void them_sanPham(String url) {
        // Tiếp theo, tiếp tục với yêu cầu thêm sản phẩm
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "Add_ProDuct.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(AdTour_Activity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    } else if (status.equals("error")) {
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
                showErrorDialog(errorMessage);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Pro_Name", name);
                params.put("Price", String.valueOf(price));
                params.put("Location", location);
                params.put("Schedule_id", String.valueOf(intMaLichTinh)); // Sử dụng mã lịch trình đã lấy được
                params.put("Desciption", des);
                params.put("Coach_id", String.valueOf(id_Coach));
                params.put("Number_Of_People_On_Tour", String.valueOf(number));
                params.put("Cate_id", String.valueOf(id_cate));
                params.put("Pro_path", image);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Hienthi(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+ "SanPham.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                adTourList.clear();
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        id_pro = object.getInt("Pro_id");
                        String name = object.getString("Pro_Name");
                        double price = object.getDouble("Price");
                        String Location = object.getString("Location");
                        String moTa = object.getString("Desciption");
                        int Sl = object.getInt("Number_Of_People_On_Tour");
                        String path = object.getString("Pro_Path");
                        int id_Lich = object.getInt("SC_id");
                        id_Sc =id_Lich;
                        Intent intent = new Intent(AdTour_Activity.this, OderDetailActivity.class);
                        intent.putExtra("Ma_LicH_Trinh", id_Sc);
                        id_cate = object.getInt("Cate_id");
                        String ngayDi = object.getString("Departure_Dates");
                        String ngayVe = object.getString("Return_Date");
                        String hotel = object.getString("Hotel");
                        AdTour ad = new AdTour();

                        ad.setId_SC(id_Lich);
                        ad.setPro_name(name);
                        ad.setPrice(price);
                        ad.setAddress(Location);
                        ad.setDes(moTa);
                        ad.setNumber(Sl);
                        ad.setPro_image(path);
                        ad.setNgay_di(ngayDi);
                        ad.setNgay_ve(ngayVe);
                        ad.setHotel(hotel);
                        ad.setId_Cate(id_cate);
                        ad.setProId(id_pro);
                        adTourList.add(ad);
                    }
                    adTourAdapter.notifyDataSetChanged();
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
    private void CapNhat(String type, int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "Update_SanPham.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(AdTour_Activity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                        Hienthi(url);
                    } else if (status.equals("error")) {
                        Toast.makeText(AdTour_Activity.this, "Sửa Không thành công", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    showErrorDialog(e.getMessage());
                    Log.e("JSONException", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorDialog("Lỗi kết nối đến máy chủ");
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (type.equals("product")) {
                    params.put("Pro_id", String.valueOf(id_pro));
                    params.put("Pro_Name", name);
                    params.put("Price", String.valueOf(price));
                    params.put("Location", location);
                    params.put("Schedule_id", String.valueOf(intMaLichTinh));
                    params.put("Desciption", des);
                    params.put("Coach_id", String.valueOf(id_Coach));
                    params.put("Number_Of_People_On_Tour", String.valueOf(number));
                    params.put("Cate_id", String.valueOf(id_cate));
                    params.put("Pro_path", image);
                } else {
                    params.put("SC_id", String.valueOf(id_Sc));
                    params.put("Location", location);
                    params.put("Departure_Dates", date_di);
                    params.put("Return_Date", date_ve);
                    params.put("Hotel", hotel);
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Delete_Pro(int id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"Del_Pro.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(AdTour_Activity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                        Hienthi(url);
                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message"); // Lấy thông điệp lỗi từ server
                    }
                } catch (JSONException e) {
                    showErrorDialog(e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorDialog(error.getMessage());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Pro_id", String.valueOf(id_pro));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Delete_Sc(int id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"Del_Sc.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(AdTour_Activity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                        Hienthi(url);
                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message"); // Lấy thông điệp lỗi từ server
                    }
                } catch (JSONException e) {
                    //showErrorDialog(e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorDialog(error.getMessage());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("SC_id", String.valueOf(id_Sc));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Hienthi_Coach() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"coach.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                guideNames.clear();
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        if (!object.isNull("Coach_id") && !object.isNull("Coach_Name") && !object.isNull("Phone") && !object.isNull("Gender")) {
                            id_Coach = object.getInt("Coach_id");
                            guideNames.add(id_Coach);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    // Cập nhật dữ liệu mới vào Adapter và thông báo cho ListView cập nhật
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
    private void Hienthi_Cate() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdTour_Activity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"category.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                category.clear();
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        if (!object.isNull("Cate_id")){
                            id_cate = object.getInt("Cate_id");
                           category.add(id_cate);
                        }
                        mangCate.notifyDataSetChanged();
                    }
                    // Cập nhật dữ liệu mới vào Adapter và thông báo cho ListView cập nhật
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
    public int getIdPro() {
        return id_pro;
    }
    private int someMethod() {
        // Gọi phương thức Hienthi() để gán giá trị Pro_id
        Hienthi(url);
        // Sử dụng phương thức getter để lấy giá trị Pro_id
        return getIdPro();
    }
}