package huflit.phn.dulichvietnam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginLayoutActivity extends AppCompatActivity {
    private Button btnback2;


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        //load fragment mặc định: LoginMainFragment
        //actionBar = getSupportActionBar();
        loadFragment(new LoginMainFragment());

        //nút quay lại
        btnback2 = (Button) findViewById(R.id.btn_back2);
        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (currentFragment instanceof LoginMainFragment) {
                    Intent intent = new Intent(LoginLayoutActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}