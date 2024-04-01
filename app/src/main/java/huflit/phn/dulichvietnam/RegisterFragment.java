package huflit.phn.dulichvietnam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    private Button btndk2;
    EditText edtsdt, edtname, edtngaysinh, edtgt, edtmk, edtnlmk;
    private static final String url="http://192.168.3.189/server/register.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edtsdt = view.findViewById(R.id.edt_sdtDk);
        edtname = view.findViewById(R.id.edt_fullname);
        edtgt = view.findViewById(R.id.edt_gioitinh);
        edtngaysinh = view.findViewById(R.id.edt_ngaysinh);
        edtmk = view.findViewById(R.id.edt_mk);
        edtnlmk = view.findViewById(R.id.edt_nlmk);
        btndk2 = view.findViewById(R.id.btn_dktk);
        btndk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    if (validateInput())
                    {
                        Regis(url);
                    }
                }
            }
        });
        return view;
    }

    private boolean validateInput() {
        String sdt = edtsdt.getText().toString().trim();
        String name = edtname.getText().toString().trim();
        String ngaysinh = edtngaysinh.getText().toString().trim();
        String mk = edtmk.getText().toString().trim();
        String nlmk = edtnlmk.getText().toString().trim();
        String gioitinh = edtgt.getText().toString().trim();

        if (TextUtils.isEmpty(sdt) || sdt.length() != 10 || !TextUtils.isDigitsOnly(sdt)) {
            showAlert("Lỗi", "Số điện thoại không hợp lệ (phải có 10 số)");
            return false;
        }

        if (TextUtils.isEmpty(name) || name.length() < 10) {
            showAlert("Lỗi", "Tên phải chứa ít nhất 10 kí tự");
            return false;
        }
        if (TextUtils.isEmpty(gioitinh)) {
            showAlert("Lỗi", "Vui lòng điền giới tính");
            return false;
        }

        // Kiểm tra định dạng ngày sinh (ở đây giả sử ngày sinh phải có định dạng dd/MM/yyyy)
        if (!isValidDate(ngaysinh)) {
            showAlert("Lỗi", "Ngày sinh không hợp lệ (định dạng: 20/06/2004)");
            return false;
        }

        // Kiểm tra mật khẩu
        if (!isValidPassword(mk)) {
            showAlert("Lỗi", "Mật khẩu không hợp lệ.Mật khẩu phải có kí tự in hoa,kí tự đặc biệt và số ,trên 8 kí tự");
            return false;
        }

        // Kiểm tra nhập lại mật khẩu
        if (!mk.equals(nlmk)) {
            showAlert("Lỗi", "Nhập lại mật khẩu không khớp");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private boolean isValidDate(String date) {
        String regex = "\\d{2}/\\d{2}/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public void Regis(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            if (status.equals("success")) {
                                // Xử lý khi đăng ký thành công
                                Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                if (validateInput())
                                {
                                    LoginMainFragment loginMainFragment = new LoginMainFragment();

                                    getParentFragmentManager().beginTransaction()
                                            .replace(R.id.frame_container, loginMainFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            } else if (status.equals("error")) {
                                String message = jsonResponse.getString("message");
                                // Xử lý khi đăng ký thất bại hoặc bất kỳ trạng thái nào khác
                                if (message.equals("Tài khoản đã tồn tại")) {
                                    // Hiển thị hộp thoại dialog khi tài khoản đã tồn tại
                                    showInputDialog("Lỗi: " + message);
                                } else {
                                    // Xử lý các thông báo lỗi khác (nếu có)
                                    Toast.makeText(getActivity(), "Đăng ký thất bại: " + message, Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Phone", edtsdt.getText().toString().trim());
                params.put("FullName",edtname.getText().toString().trim());
                params.put("Gender",edtgt.getText().toString().trim());
                params.put("Birthday",edtngaysinh.getText().toString().trim());
                params.put("Passwords", edtmk.getText().toString().trim());
                params.put("Role_user", String.valueOf(1));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void showInputDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
