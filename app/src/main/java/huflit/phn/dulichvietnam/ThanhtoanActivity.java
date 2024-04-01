package huflit.phn.dulichvietnam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ThanhtoanActivity extends AppCompatActivity {
    Button btnback,btnthanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        btnback = findViewById(R.id.btn_back4);
        btnthanhtoan = findViewById(R.id.btn_dattour3);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog();
            }
        });
    }
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đặt tour thành công")
                .setMessage("Cảm ơn bạn đã đặt tour,chúc bạn và gia đình có 1 chuyến đi thật vui vẻ ^.^")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        Intent intent = new Intent(ThanhtoanActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.tickthanhtoan);
        builder.setView(imageView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}