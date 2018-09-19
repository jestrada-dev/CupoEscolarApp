package co.jestrada.cupoescolarapp.school.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.ViewHolder> {

    private List<SchoolOrderedByRefPositionBO> schools;
    private int layout;
    private OnItemClickListener itemClickListener;

    public SchoolsAdapter(List<SchoolOrderedByRefPositionBO> schools, int layout,
                          OnItemClickListener itemClickListener) {
        this.schools = schools;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(schools.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        if (schools != null && !schools.isEmpty()){
            return schools.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvDuration;
        public TextView tvDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
        }

        public void bind(final SchoolOrderedByRefPositionBO school,
                         final OnItemClickListener listener){
            this.tvName.setText(school.getName());
            this.tvDuration.setText("Tiempo para llegar " + school.getDurationText());
            this.tvDistance.setText("Distancia " + school.getDistanceText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(school, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SchoolOrderedByRefPositionBO school, int position);
    }

}
