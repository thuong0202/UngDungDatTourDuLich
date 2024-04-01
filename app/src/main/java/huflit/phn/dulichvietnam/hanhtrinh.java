package huflit.phn.dulichvietnam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import sanpham.bamien;
import sanpham.sanphamAdapter;



public class hanhtrinh extends Fragment {

    private RecyclerView rcvsp;
    private Button btnmb,btnmt,btnmn;
    private GridLayoutManager gridLayoutManager;
    private MainActivity mainActivity;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private final String url = "http://192.168.3.189/server/orders.php";

    public hanhtrinh() {
        // Required empty public constructor
    }
    public static hanhtrinh newInstance(String param1, String param2) {
        hanhtrinh fragment = new hanhtrinh();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hanhtrinh, container, false);
        return view;
    }



    public void onResume() {
        super.onResume();
    }


}
