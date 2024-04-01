package huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdminFragment;
import huflit.phn.dulichvietnam.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdTravelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdTravelFragment extends Fragment {

    //Khai báo
    private Button btnAdd, btnEdit, btnDelete, btnBack;
    private EditText edtFull_name, edtPhone, edtDate, medtGender;
    private String hoten, sdt, gender, ngaysinh;

    private static final String url = "http://192.168.3.189/server/";

    private TextView tvSL;
    private ListView lvAd_travel;
    private ArrayList<AdTravel> alTravel;
    private int id;
    private ArrayAdapter<AdTravel> arrayAdapter;
//    public static AdTravelDatabase database;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int sl;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdTravelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdTravelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdTravelFragment newInstance(String param1, String param2) {
        AdTravelFragment fragment = new AdTravelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //gọi layout huong dan vien
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ad_travel, container, false);

        //Ánh xạ
        btnAdd = view.findViewById(R.id.btn_add);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnBack = view.findViewById(R.id.btn_back);

        edtFull_name = view.findViewById(R.id.edt_fullName);
        edtPhone = view.findViewById(R.id.edt_phone);
        edtDate = view.findViewById(R.id.edt_date);
        medtGender = view.findViewById(R.id.edtGioiTinh);

        tvSL = view.findViewById(R.id.tv_sl);
        lvAd_travel = view.findViewById(R.id.lvAd_travel);

        // nhập ngày tháng
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        Hienthi();
        //Thao tác với nút back để quay lại fragment trước đó
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một instance của fragment_admin
                AdminFragment adminFragment = new AdminFragment();

                // Thay thế fragment hiện tại bằng fragment_admin
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, adminFragment)
                        .commit();
            }
        });
        tvSL.setText(sl + " người.");
        //Tạo ListView hướng dẫn viên
        alTravel = new ArrayList<AdTravel>();
        arrayAdapter = new AdTravelAdapter(getContext(), alTravel);
        lvAd_travel.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoten = edtFull_name.getText().toString();
                sdt = edtPhone.getText().toString();
                ngaysinh = edtDate.getText().toString();
                gender = medtGender.getText().toString();
                if (hoten.isEmpty() || sdt.isEmpty() || ngaysinh.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if(sdt.length()!=10)
                {
                    Toast.makeText(getContext(), "Số điện thoại phảo có 10 chữ số", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    AdTravel travel = new AdTravel(hoten, sdt, ngaysinh, gender);
                    alTravel.add(travel);
                    arrayAdapter.notifyDataSetChanged();
                    edtFull_name.setText("");
                    edtPhone.setText("");
                    edtDate.setText("");
                    tvSL.setText(sl+1+ " người.");
                    them();
                    Hienthi();
                }
            }
        });
        lvAd_travel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item from the adapter
                AdTravel clickItem = alTravel.get(position);

                // Populate EditText fields with the clicked item's information
                edtFull_name.setText(clickItem.getHovaTen());
                edtPhone.setText(clickItem.getSdt());

                // Chuyển đổi định dạng ngày tháng từ cơ sở dữ liệu (yyyy-MM-dd) sang định dạng dd/MM/yyyy trước khi hiển thị
                String databaseDate = clickItem.getNgsinh();
                String[] parts = databaseDate.split("-");
                if (parts.length == 3) {
                    String formattedDate = String.format(Locale.getDefault(), "%s/%s/%s", parts[2], parts[1], parts[0]);
                    edtDate.setText(formattedDate);
                } else {
                    edtDate.setText(databaseDate); // Trường hợp không thành công, hiển thị nguyên gốc
                }

                medtGender.setText(clickItem.getGioiTinh());
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoten = edtFull_name.getText().toString();
                sdt = edtPhone.getText().toString();
                ngaysinh = edtDate.getText().toString();
                gender = medtGender.getText().toString();
                AdTravel travel = new AdTravel(hoten, sdt, ngaysinh, gender);

                if (hoten.isEmpty() || sdt.isEmpty() || ngaysinh.isEmpty() || gender.isEmpty() ) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if(sdt.length()!=10)
                {
                    Toast.makeText(getContext(), "Số điện thoại phải có 10 chữ số", Toast.LENGTH_SHORT).show();
                    return;
                } else
                // Kiểm tra xem có đối tượng nào được chọn từ danh sách hay không
                if (travel != null) {
                    // Cập nhật thông tin của đối tượng được chọn
                    travel.setHovaTen(hoten);
                    travel.setSdt(sdt);
                    travel.setNgsinh(ngaysinh);
                    travel.setGioiTinh(gender);

                    // Thông báo cho adapter rằng dữ liệu đã thay đổi
                    arrayAdapter.notifyDataSetChanged();

                    // Xóa các trường nhập liệu
                    edtFull_name.setText("");
                    edtPhone.setText("");
                    medtGender.setText("");
                    edtDate.setText("");

                    // Cập nhật lại số lượng và hiển thị lên TextView
                    tvSL.setText(alTravel.size() + " người.");

                    // Gọi phương thức cập nhật trong cơ sở dữ liệu
                    CapNhat(id);
                    Hienthi();
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn một đối tượng để cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSL.setText(sl + " người.");
                KtraTruocKhiXoa(id);
                arrayAdapter.notifyDataSetChanged();

            }
        });
        return view;
    }
    private void them() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"Add_coach.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Hienthi();
                        Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message"); // Lấy thông điệp lỗi từ server
                        if (errorMessage.equals("Số điện thoại đã tồn tại trong hệ thống.")) {
                            Toast.makeText(getActivity(),"Số điện thoại đã tồn tại trong hệ thống.",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Thêm không thành công: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
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
                params.put("Coach_Name", hoten);
                params.put("Phone", sdt);
                params.put("Birthday", ngaysinh); // Đảm bảo ngaysinh ở định dạng "dd/mm/yyyy"
                params.put("Gender", gender);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"coach.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                alTravel.clear();
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        if (!object.isNull("Coach_id") && !object.isNull("Coach_Name") && !object.isNull("Phone") && !object.isNull("Gender")) {
                            id = object.getInt("Coach_id");
                            String name = object.getString("Coach_Name");
                            String phone = object.getString("Phone");
                            String gender = object.getString("Gender");
                            String birthday = object.getString("Birthday");
                            Log.d("Coach ID", "ID: " + id);
                            // Tạo một đối tượng AdTravel từ dữ liệu JSON
                            AdTravel travel = new AdTravel();
                            travel.setHovaTen(name);
                            travel.setSdt(phone);
                            travel.setNgsinh(birthday);
                            travel.setGioiTinh(gender);
                            // Thêm đối tượng AdTravel vào danh sách
                            alTravel.add(travel);
                        }
                    }
                    // Cập nhật dữ liệu mới vào Adapter và thông báo cho ListView cập nhật
                    sl = response.length();
                    arrayAdapter.notifyDataSetChanged();
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

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Khi người dùng chọn ngày, tháng và năm
                // Lấy các giá trị và kết hợp chúng thành chuỗi có định dạng dd/mm/yyyy
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                // Hiển thị chuỗi đã định dạng trong EditText ngày sinh
                edtDate.setText(formattedDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void CapNhat(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"Update_Coach.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_LONG).show();
                        Hienthi();
                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message");
                        if(errorMessage.equals("Số điện thoại đã được sử dụng.")){
                            showErrorDialog("Số điện thoại đã tồn tại.");
                        } else {
                            showErrorDialog(errorMessage);
                        }
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
                params.put("Coach_id", String.valueOf(id));
                params.put("Coach_Name", hoten);
                params.put("Phone", sdt);
                params.put("Birthday", ngaysinh);
                params.put("Gender", gender);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void KtraTruocKhiXoa(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có muốn xóa khônga ");
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayAdapter.notifyDataSetChanged();

                dialog.dismiss(); // Đóng dialog
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSL.setText(sl + "người ");
               Delete(id);
               Hienthi();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void Delete(int id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"Del_Coach.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        Hienthi();
                        Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_LONG).show();
                        tvSL.setText(sl + " người.");
                        // Cập nhật lại ArrayAdapter
                        arrayAdapter.notifyDataSetChanged();
                    } else if (status.equals("error")) {
                        String errorMessage = jsonResponse.getString("message"); // Lấy thông điệp lỗi từ server
                    }
                } catch (JSONException e) {
                    showErrorDialog(e.getMessage());
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Loi khong xoa duoc",Toast.LENGTH_LONG).show();
                showErrorDialog(error.getMessage());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Coach_id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
