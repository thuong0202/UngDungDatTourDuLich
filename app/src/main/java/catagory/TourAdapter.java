package catagory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import huflit.phn.dulichvietnam.R;

public class TourAdapter extends ArrayAdapter<TourItem> {
    private Context mContext;
    private int mResource;

    public TourAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TourItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String thoiGianDatTour = getItem(position).getThoiGianDatTour();
        String tenTour = getItem(position).getTenTour();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvThoiGian = convertView.findViewById(R.id.tv_thoiginan);
        TextView tvTenTour = convertView.findViewById(R.id.tv_tentour);

        tvThoiGian.setText(thoiGianDatTour);
        tvTenTour.setText(tenTour);

        return convertView;
    }
}

