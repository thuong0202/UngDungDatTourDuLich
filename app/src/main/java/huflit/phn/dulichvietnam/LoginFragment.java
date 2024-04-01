package huflit.phn.dulichvietnam;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import huflit.phn.dulichvietnam.Admin_layout.AdminActivity;
import sanpham.SharedPreferencesManager;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    public Boolean trangthai = false;
    private EditText edtphone;
    private Button btndn;
    private TextInputEditText edtmk;
    private TextInputLayout etPasswordLayout;

    private static final String url = "http://192.168.3.189/server/Login.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }
    private static String phoneNumber;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btndn = view.findViewById(R.id.login);
        edtphone = view.findViewById(R.id.edtphone);
        edtmk = view.findViewById(R.id.edtpassword);
        edtphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneNumber = s.toString(); // Lấy số điện thoại từ EditText
            }
        });
        etPasswordLayout = view.findViewById(R.id.etPasswordLayout);

        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(url);
            }
        });



        return view;

    }
    public static String getPhoneNumber() {
        return phoneNumber;
    }
    private void login(String url)
    {
        phoneNumber = edtphone.getText().toString();
        SharedPreferencesManager.getInstance(getContext()).savePhoneNumber(phoneNumber);
        Order order = new Order();
        order.setPhone(phoneNumber);
        String mk = edtmk.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
            edtphone.setError("Số điện thoại phải có đúng 10 số");
            return;
        }

        if (TextUtils.isEmpty(mk)) {
            etPasswordLayout.setError("Vui lòng nhập mật khẩu");
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        JSONObject jsonObject = new JSONObject();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    boolean isLoggedIn = false;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String phone = object.getString("Phone");
                        String password = object.getString("Passwords");
                        int role = object.getInt("Role_user");
                        if (phoneNumber.equals(phone) && mk.equals(password) ) {
                            if(role == 1)
                            {
                                Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                SharedPreferencesManager.getInstance(getContext()).isLogin = true;
                                isLoggedIn = true;
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("isLoggedIn", true);
                                intent.putExtra("Phone",phone);
                                intent.putExtra("role", role);
                                startActivity(intent);
                                getActivity().finish();
                                break;
                            } else
                            {
                                Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                SharedPreferencesManager.getInstance(getContext()).isLogin = true ;
                                SharedPreferencesManager.getInstance(getContext()).savePhoneNumber(phoneNumber); // Lưu số điện thoại vào SharedPreferences
                                isLoggedIn = true;
                                Intent intent = new Intent(getActivity(),AdminActivity.class);
                                intent.putExtra("role", role);
                                startActivity(intent);
                                break;
                            }
                        }
                    }

                    if (!isLoggedIn) {
                        Toast.makeText(getActivity(),"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_LONG).show();
                        SharedPreferencesManager.getInstance(getContext()).isLogin = false ;
                        isLoggedIn = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    SharedPreferencesManager.getInstance(getContext()).isLogin = false ;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferencesManager.getInstance(getContext()).isLogin = false ;
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}