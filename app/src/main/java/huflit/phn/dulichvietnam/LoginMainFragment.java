package huflit.phn.dulichvietnam;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginMainFragment extends Fragment {
    private Button btngust,btnUser,btndk;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginMainFragment newInstance(String param1, String param2) {
        LoginMainFragment fragment = new LoginMainFragment();
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
        View view= inflater.inflate(R.layout.fragment_login_main, container, false);

        btngust = view.findViewById(R.id.login_no_register);
        btngust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        btnUser = view.findViewById(R.id.login_phone_number);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, loginFragment) // fragment_container là ID của layout container trong activity chứa fragment
                        .addToBackStack(null) // (Optional) cho phép người dùng nhấn nút back để quay lại fragment trước đó
                        .commit();
            }
        });

        btndk = view.findViewById(R.id.register);
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, registerFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Lưu ý");
        builder.setMessage("Khi đăng nhập không cần đăng ký,một số tính năng không được hỗ trợ")
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Tạo AlertDialog object và hiển thị
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}