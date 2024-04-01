// OrderAdapter.java

package OrderDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import huflit.phn.dulichvietnam.R;

public class OrderAdapter extends ArrayAdapter<Order> {
    TextView tvFullName,tvGender,tvAge,tvPhoneNumber;

    private ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_order, parent, false);
        }
        tvFullName = convertView.findViewById(R.id.tv_ten);
        tvGender = convertView.findViewById(R.id.tv_gioitinh);
        tvAge = convertView.findViewById(R.id.tv_tuoi);
        tvPhoneNumber = convertView.findViewById(R.id.tv_Sđt);

        tvFullName.setText(order.getFullName());
        tvGender.setText(order.getGender());
        tvAge.setText(String.valueOf(order.getAge()));
        tvPhoneNumber.setText(order.getPhoneNumber());

        return convertView;
    }
}
