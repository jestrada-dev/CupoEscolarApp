package co.jestrada.cupoescolarapp.student.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private List<StudentBO> students;
    private int layout;
    private OnItemClickListener itemClickListener;

    public StudentsAdapter(List<StudentBO> students, int layout, OnItemClickListener itemClickListener) {
        this.students = students;
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
        viewHolder.bind(students.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        if (students != null && !students.isEmpty()){
            return students.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvDocId;
        public TextView tvState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvDocId = (TextView) itemView.findViewById(R.id.tv_doc_id);
            this.tvState = (TextView) itemView.findViewById(R.id.tv_state);
        }

        public void bind(final StudentBO studentBO, final OnItemClickListener listener){
            this.tvName.setText(studentBO.getFirstName() + " " + studentBO.getLastName());
            this.tvDocId.setText(studentBO.getDocIdType() + " " + studentBO.getDocId());
            this.tvState.setText(studentBO.getState());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(studentBO, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(StudentBO studentBO, int position);
    }

}
