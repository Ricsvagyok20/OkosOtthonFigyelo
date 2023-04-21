package com.example.okosotthonfigyelo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> implements Filterable{

    private ArrayList<Device> deviceArrayList;
    private ArrayList<Device> deviceArrayListAll;
    private Context context;
    private int lastPosition = -1;

    DeviceListAdapter(Context context, ArrayList<Device> devices){
        this.deviceArrayList = devices;
        this.deviceArrayListAll = devices;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_device, parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceListAdapter.ViewHolder holder, int position) {
        Device currentDevice = deviceArrayList.get(position);

        holder.bindTo(currentDevice);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.default_animation);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return deviceArrayList.size();
    }

    private Filter deviceFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Device> filteredDevices = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = deviceArrayListAll.size();
                results.values = deviceArrayListAll;
            } else {
                String filter = charSequence.toString().toLowerCase().trim();

                for (Device tmp : deviceArrayListAll) {
                    if (tmp.getName().toLowerCase().contains(filter)) {
                        filteredDevices.add(tmp);
                    }
                }
                results.count = filteredDevices.size();
                results.values = filteredDevices;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            deviceArrayList = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return deviceFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView deviceImage;
        private TextView deviceName;
        private TextView deviceManufacturer;
        private TextView description;
        private ImageView active;
        private boolean isActive = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.deviceImage = itemView.findViewById(R.id.deviceImage);
            this.deviceName = itemView.findViewById(R.id.deviceName);
            this.deviceManufacturer = itemView.findViewById(R.id.deviceManufacturer);
            this.description = itemView.findViewById(R.id.description);
            this.active = itemView.findViewById(R.id.active);

            itemView.findViewById(R.id.modify).setOnClickListener(view -> Log.d("modify", "Modify button has been clicked!"));

            itemView.findViewById(R.id.delete).setOnClickListener(view -> Log.d("delete", "Delete button has been clicked!"));

            itemView.findViewById(R.id.active).setOnClickListener(view -> {
                //Log.d("setActive", "Turn on button has been clicked!");
                isActive = !isActive;
            });
        }

        public void bindTo(Device currentDevice) {
            deviceName.setText(currentDevice.getName());
            description.setText(currentDevice.getDescription());
            deviceManufacturer.setText(currentDevice.getManufacturer());
            Glide.with(context).load(currentDevice.getImageResource()).into(deviceImage);
            currentDevice.setActive(isActive);
            if(currentDevice.isActive()){
                Glide.with(context).load(R.drawable.green_circle).into(active);
            }
            else{
                Glide.with(context).load(R.drawable.red_circle).into(active);
            }
        }
    }
}