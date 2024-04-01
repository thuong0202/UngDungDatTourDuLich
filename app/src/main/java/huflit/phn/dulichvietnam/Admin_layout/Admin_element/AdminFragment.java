package huflit.phn.dulichvietnam.Admin_layout.Admin_element;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.customview.widget.Openable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import huflit.phn.dulichvietnam.AdTour.AdTour_Activity;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdCategory.CategoryFragment;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdGuest.GuestFragment;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdOrder.OrderFragment;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdProduct.ProductFragment;
import huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel.AdTravelFragment;
import huflit.phn.dulichvietnam.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {
    private Button btnCategory, btnProduct, btnOrder, btnAd_tour, btnAd_travel, btnGuest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Openable AdminActivity;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        FragmentActivity fragmentActivity = getActivity(); // Lấy Activity chứa Fragment
        Intent intent = fragmentActivity.getIntent(); // Nhận Intent từ Activity
        int role = intent.getIntExtra("role", -1); // Lấy dữ liệu role từ Intent
        btnCategory = view.findViewById(R.id.category);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment categoryFragment =new CategoryFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, categoryFragment) // fragment_container là ID của layout container trong activity chứa fragment
                        .commit();
            }
        });

        btnProduct = view.findViewById(R.id.product);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdTour_Activity.class);
                startActivity(intent);
            }
        });

        btnOrder = view.findViewById(R.id.order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment orderFragment = new OrderFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, orderFragment) // fragment_container là ID của layout container trong activity chứa fragment
                        .commit();
            }
        });
        // Trong Fragment của bạn
        btnGuest = view.findViewById(R.id.guest);
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestFragment guestFragment = new GuestFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, guestFragment) // fragment_container là ID của layout container trong activity chứa fragment
                        .commit();
            }
        });
        btnAd_travel = view.findViewById(R.id.ad_travel);
        btnAd_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTravelFragment adTravelFragment = new AdTravelFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, adTravelFragment) // R.id.ad_frame_container là ID của layout container trong FragmentActivity chứa Fragment
                        .commit();
            }
        });
        if (fragmentActivity != null) {

            // Kiểm tra role và thực hiện việc chuyển Fragment nếu cần
            if (role == 2) {
                AdTravelFragment adTravelFragment = new AdTravelFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.ad_frame_container, adTravelFragment) // R.id.ad_frame_container là ID của layout container trong FragmentActivity chứa Fragment
                        .commit();
            } else if (role == 3)
            {
                Intent adTourIntent = new Intent(getActivity(), AdTour_Activity.class);
                startActivity(adTourIntent);
            } else if (role == 4)
            {

            }
        }

        return view;
    }
}