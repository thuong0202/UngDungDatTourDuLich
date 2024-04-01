package layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import catagory.catagory;
import huflit.phn.dulichvietnam.R;

public class AnhbamienAdapter extends RecyclerView.Adapter<AnhbamienAdapter.AnhHolder> {
    private List<Anhbamien> manhbamien;
    private IclickitemListerner mClickItem;

    public AnhbamienAdapter() {

    }

    public interface IclickitemListerner{
        void onClickitem(Anhbamien anhbamien );
    }

    public AnhbamienAdapter(List<Anhbamien>manhbamien,IclickitemListerner listerner) {
        this.manhbamien = manhbamien;
        this.mClickItem = listerner;
    }

    public void setData(List<Anhbamien> list){
        this.manhbamien = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AnhHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diadiem,parent,false);
        return new AnhHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhHolder holder, int position) {
        Anhbamien anh = manhbamien.get(position);
        if (anh == null){
            return;
        }
        holder.imgAnh.setImageResource(anh.getResourceID());
        holder.tvTitle.setText(anh.getTitle());
        if (position == 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.setMarginStart(0);
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        if(manhbamien!= null){
            return manhbamien.size();
        }
        return 0;
    }

    public class AnhHolder extends RecyclerView.ViewHolder{
        public ImageView imgAnh;
        public TextView  tvTitle;

        public AnhHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.anhmien);
            tvTitle = itemView.findViewById(R.id.tenmien);
        }
    }
}
