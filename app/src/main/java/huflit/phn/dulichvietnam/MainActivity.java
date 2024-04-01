package huflit.phn.dulichvietnam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import catagory.catagoryAdapter;
import catagory.catagory;
import layout.Anhbamien;
import layout.AnhbamienAdapter;
import sanpham.SharedPreferencesManager;
import sanpham.bamien;


public class MainActivity extends AppCompatActivity  {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private Button btndn2;
    public static final int YOUR_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mViewPager = findViewById(R.id.chinh);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.add(R.id.chinh,new hanhtrinh());
        fragmentTransaction1.commit();

        View_chinh adapter = new View_chinh(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.hanhtrinh).setChecked(true);

                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tbao).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.caidat).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    mViewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.hanhtrinh) {
                    if (SharedPreferencesManager.getInstance(MainActivity.this).isLogin) {
                        mViewPager.setCurrentItem(1);
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_LONG).show();
                    }
                } else if (item.getItemId() == R.id.tbao) {
                    if (SharedPreferencesManager.getInstance(MainActivity.this).isLogin) {
                        mViewPager.setCurrentItem(2);
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_LONG).show();
                    }
                } else if (item.getItemId() == R.id.caidat) {
                    if (SharedPreferencesManager.getInstance(MainActivity.this).isLogin)
                    {
                        mViewPager.setCurrentItem(3);
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chinh,new trangchu());
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            boolean showHelloMessage = data.getBooleanExtra("hello_message", false);
            if (showHelloMessage) {
                btndn2.setText("Xin chào");
            }
        }
    }
    private void setupLoginButton() {
        btndn2 = findViewById(R.id.btndangnhap);
        btndn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ MainActivity sang LoginActivity
                Intent intent = new Intent(MainActivity.this, trangchu.class);
                // Gọi startActivityForResult với mã yêu cầu của bạn
                startActivityForResult(intent, YOUR_REQUEST_CODE);
            }
        });
    }
}