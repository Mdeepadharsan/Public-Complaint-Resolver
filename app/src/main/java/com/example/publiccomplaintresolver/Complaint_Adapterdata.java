package com.example.publiccomplaintresolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Complaint_Adapterdata extends RecyclerView.Adapter<Complaint_Adapterdata.ViewHolder> {
    private Context context;
    private ArrayList<Complaint_Data> arrayList;

    public Complaint_Adapterdata(Context context, ArrayList<Complaint_Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Complaint_Adapterdata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inbox_msg_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Complaint_Adapterdata.ViewHolder holder, int position) {
            Complaint_Data data =arrayList.get(position);
            String id = data.getComplaint_id();
            String description = data.getComplaint_description();
            String location = data.getComplaint_location();
            holder.id.setText(id);
            holder.description.setText(description);
            holder.location.setText(location);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,description,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.reccomplaint_id);
            description=itemView.findViewById(R.id.recomplaint_description);
            description=itemView.findViewById(R.id.recomplaint_description);
            location=itemView.findViewById(R.id.recomplaint_location);
        }
    }
}
