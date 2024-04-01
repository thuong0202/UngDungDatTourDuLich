package huflit.phn.dulichvietnam;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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


import catagory.catagory;
import catagory.catagoryAdapter;
import huflit.phn.dulichvietnam.Admin_layout.AdminActivity;
import layout.Anhbamien;
import layout.AnhbamienAdapter;
import sanpham.SharedPreferencesManager;


public class trangchu extends Fragment {

    private RecyclerView rcvCategory;
    private catagoryAdapter CatagoryAdapter;
    private MainActivity mActivity;
    private Button btndn;
    private ImageButton btnmienB,btnmienTR,btnmienNa;
    private ViewPager mViewPager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String url = "http://192.168.3.189/server/Login.php";


    public trangchu() {
    }

    public static trangchu newInstance(String param1, String param2) {
        trangchu fragment = new trangchu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private List<catagory> getListCategory() {
        List<catagory> listCatagory = new ArrayList<>();
        List<Anhbamien> listanh = new ArrayList<>();
        listanh.add(new Anhbamien(R.drawable.hoguom, "MIỀN BẮC"));
        listanh.add(new Anhbamien(R.drawable.danag, "MIỀN TRUNG"));
        listanh.add(new Anhbamien(R.drawable.chomnbt, "MIỀN NAM"));
        listanh.add(new Anhbamien(R.drawable.nen, "CẢ NƯỚC"));
        listCatagory.add(new catagory("DU LỊCH VIỆT NAM - VIETNAM TRAVEL", listanh));
        List<Anhbamien> listAnh2 = new ArrayList<>();
        listAnh2.add(new Anhbamien(R.drawable.khachsan, "KHÁCH SẠN"));
        listAnh2.add(new Anhbamien(R.drawable.tourcuatoi, "TOUR CỦA TÔI"));
        listAnh2.add(new Anhbamien(R.drawable.dulich, "BA MIEN"));
        listCatagory.add(new catagory("DỊCH VỤ DU LỊCH", listAnh2));

        if (listCatagory.size() > 4) {
            listCatagory = listCatagory.subList(0, 4);
        }
        return listCatagory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        CatagoryAdapter = new catagoryAdapter(getActivity());
        mActivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvCategory.setAdapter(CatagoryAdapter);
        rcvCategory.setLayoutManager(linearLayoutManager);
        List<catagory> listCategory = getListCategory();
        CatagoryAdapter.setData(listCategory);
        btndn = view.findViewById(R.id.btndangnhap);
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginLayoutActivity.class);
                startActivityForResult(intent, 123);
            }
        });
        btnmienB = view.findViewById(R.id.btn_mienb);
        btnmienB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivitySanpham.class);

                startActivity(intent);
            }
        });

        // Trong TrangChuFragment
        if (SharedPreferencesManager.getInstance(getContext()).isLogin) {
            btndn.setText("Xin chào");
        } else {
            btndn.setText("Đăng nhập");
        }
        return view;
    }

    private List<Anhbamien> getListbamien() {
        List<Anhbamien> listanh = new ArrayList<>();
        listanh.add(new Anhbamien(R.drawable.mienbac, "MIỀN BẮC"));
        listanh.add(new Anhbamien(R.drawable.miennam, "MIỀN NAM"));
        listanh.add(new Anhbamien(R.drawable.chomnbt, "MIỀN TRUNG"));
        listanh.add(new Anhbamien(R.drawable.nen, "CẢ NƯỚC"));
        return listanh;
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 123 && resultCode == RESULT_OK) {
//            btndn.setText("Xin chào");
//        }
//    }

}