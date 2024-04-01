package huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import huflit.phn.dulichvietnam.R;

public class AdTravelAdapter extends ArrayAdapter {

    Context context = null;
    ArrayList<AdTravel> arrayList = null;


    public AdTravelAdapter(Context context, ArrayList<AdTravel> users) {
        super(context, 0, users);
        this.context = context;
        this.arrayList = users;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdTravel user = (AdTravel) getItem(position);
        if (convertView == null) {
            //Lấy id layout của customtiem
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customitem, parent, false);
        }

        if(arrayList.size() > 0 && position >=0)
        {
            TextView tv = (TextView) convertView.findViewById(R.id.tvitem);
            ImageView img = (ImageView) convertView.findViewById(R.id.imgnam);
            AdTravel emp = arrayList.get(position);
            tv.setText(emp.toString());
            if(arrayList.size() > 0 && position >=0)
            {
                tv = (TextView) convertView.findViewById(R.id.tvitem);
                img = (ImageView) convertView.findViewById(R.id.imgnam);
                emp = arrayList.get(position);
                String name = String.valueOf((EditText) convertView.findViewById(R.id.edt_fullName));
                EditText phone = (EditText) convertView.findViewById(R.id.edt_phone);
                EditText ngsinh = (EditText) convertView.findViewById(R.id.edt_date);
                EditText gt = (EditText) convertView.findViewById(R.id.edtGioiTinh);

                tv.setText(emp.toString());
                if (Objects.equals(emp.getGioiTinh(), "Nam")){
                    img.setImageResource(R.drawable.nam);
                }
                else {
                    img.setImageResource(R.drawable.nu);
                }
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event here
                    // You can perform actions based on the clicked item
                    // For example, you can show a dialog with item details
                    populateEditTextFields(user);
                }
            });
        }
        return convertView;
    }
    private void populateEditTextFields(AdTravel user) {
        // Get references to EditText fields
        EditText edtFullName = ((Activity)context).findViewById(R.id.edt_fullName);
        EditText edtPhone = ((Activity)context).findViewById(R.id.edt_phone);
        EditText edtGender = ((Activity)context).findViewById(R.id.edtGioiTinh);
        EditText edtDate = ((Activity)context).findViewById(R.id.edt_date);

        // Set the text of EditText fields with the details of the clicked item
        edtFullName.setText(user.getHovaTen());
        edtPhone.setText(user.getSdt());
        edtGender.setText(user.getGioiTinh());
        edtDate.setText(user.getNgsinh());
    }
}