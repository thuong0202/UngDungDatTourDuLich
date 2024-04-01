package huflit.phn.dulichvietnam;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;

import catagory.TourAdapter;
import catagory.TourItem;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel.AdTravel;
import sanpham.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thongbao#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thongbao extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String url ="http://192.168.3.189/server/ThongBao.php";
    ArrayList<TourItem> tourList ;
    TourAdapter adapter ;
    ListView listView;
    TextView tvstg,tvTen;
    private String phoneNumber;

    public thongbao() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thongbao.
     */
    // TODO: Rename and change types and number of parameters
    public static thongbao newInstance(String param1, String param2) {
        thongbao fragment = new thongbao();
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
        View rootView = inflater.inflate(R.layout.fragment_thongbao, container, false);
        listView = rootView.findViewById(R.id.Lv_tb);
        ArrayList<TourItem> tourList = new ArrayList<>();
        phoneNumber = SharedPreferencesManager.getInstance(getContext()).getPhoneNumber();
        tvstg = rootView.findViewById(R.id.tv_thoiginan);
        tvTen = rootView.findViewById(R.id.tv_tentour);
        TourAdapter adapter = new TourAdapter(getActivity(), R.layout.item_thongbao, tourList);
        Hienthi();
        adapter.notifyDataSetChanged();
         // Thêm dữ liệu vào danh sách
        return rootView;

    }
    public void onResume() {
        super.onResume();
    }
    private void Hienthi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Xóa dữ liệu cũ trong danh sách coachList
                try {
                    // Duyệt qua từng phần tử trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        if (phoneNumber == object.getString("user_id"))
                        {
                            String name = object.getString("Pro_Name");
                            String bookingTour = object.getString("BookingTour");
                            ArrayList<TourItem> tourList = new ArrayList<>();
                            tvstg.setText(name);
                            tvTen.setText(bookingTour);
                            tourList.add(new TourItem(name, bookingTour));
                        }
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