package huflit.phn.dulichvietnam;


import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import huflit.phn.dulichvietnam.Admin_layout.AdminActivity;
import sanpham.SharedPreferencesManager;


public class caidat extends Fragment {

    Button btnuser,btndmk,btnlsdt,btndx;
    TextView tvTen;
    TextView tvSdt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String url = "http://192.168.3.189/server/Login.php";

    private String mParam1;
    private String mParam2;

    public caidat() {}


    public static caidat newInstance(String param1, String param2) {
        caidat fragment = new caidat();
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
        View view = inflater.inflate(R.layout.fragment_caidat, container, false);
        btnuser = view.findViewById(R.id.btn_user_detail);
        btndmk = view.findViewById(R.id.btn_doimk);
        btnlsdt = view.findViewById(R.id.btn_lsdt);


        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Userdetail.class);
                startActivity(intent);
            }
        });
        btndmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPassWord.class);
                startActivity(intent);
            }
        });
        btndx = view.findViewById(R.id.btn_dangxuat);
        btndx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.getInstance(getContext()).isLogin = false;
                Toast.makeText(getContext(),"Bạn đã đăng xuất ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}