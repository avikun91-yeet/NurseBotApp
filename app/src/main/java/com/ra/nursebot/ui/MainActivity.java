package com.ra.nursebot.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ra.nursebot.data.model.MedicineWithPatient;
import com.ra.nursebot.data.room.RoomKey;
import com.ra.nursebot.databinding.ActivityMainBinding;
import com.ra.nursebot.databinding.RcvUpcmngItemBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel mViewModel;
    private UpcomingItemAdapter adapter;
    private LiveData<List<MedicineWithPatient>> mwpListLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, PrescriptionAddActivity.class)));

        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.rcvUpcoming.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UpcomingItemAdapter(new ArrayList<>());
        binding.rcvUpcoming.setAdapter(adapter);

        getList();
        mwpListLD.observe(this, medicineWithPatients -> {
            Timber.d("Observer called:%s", medicineWithPatients);
            adapter.clear();
            adapter.addAll(medicineWithPatients);
        });
    }

    private void getList() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteNow = calendar.get(Calendar.MINUTE);
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hourNow);
        calendar.set(Calendar.MINUTE, minuteNow);
        Timber.d("currentTimeInMillis:%s, hour:%s, minute:%s", calendar.getTimeInMillis(), hourNow, minuteNow);
        if (calendar.getTimeInMillis() < RoomKey.MED_TIME.get(RoomKey.TIMES[0])) {
            Timber.d("Breakfast time:%s", RoomKey.MED_TIME.get(RoomKey.TIMES[0]));
            mwpListLD = mViewModel.getMedicineWithPatientList(RoomKey.MED_TIME.get(RoomKey.TIMES[0]));
        } else if (calendar.getTimeInMillis() < RoomKey.MED_TIME.get(RoomKey.TIMES[1])) {
            Timber.d("Lunch time:%s", RoomKey.MED_TIME.get(RoomKey.TIMES[1]));
            mwpListLD = mViewModel.getMedicineWithPatientList(RoomKey.MED_TIME.get(RoomKey.TIMES[1]));
        } else if (calendar.getTimeInMillis() < RoomKey.MED_TIME.get(RoomKey.TIMES[2])) {
            Timber.d("Dinner time:%s", RoomKey.MED_TIME.get(RoomKey.TIMES[2]));
            mwpListLD = mViewModel.getMedicineWithPatientList(RoomKey.MED_TIME.get(RoomKey.TIMES[2]));
        }
    }

    public static class UpcomingItemAdapter extends RecyclerView.Adapter<UpcomingItemAdapter.ViewHolder> {

        private List<MedicineWithPatient> mwpList;

        public UpcomingItemAdapter(List<MedicineWithPatient> mwpList) {
            this.mwpList = mwpList;
        }

        public void clear() {
            mwpList.clear();
        }

        public void addAll(List<MedicineWithPatient> list) {
            mwpList.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(RcvUpcmngItemBinding.inflate(LayoutInflater.from(parent.getContext())));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MedicineWithPatient mwp = mwpList.get(position);
            String text = String.format(Locale.getDefault(),
                    "Patient:%s, Bed:%d, Med:%s, Box:%d, time:%s",
                    mwp.getPatient().getPatientName(),
                    mwp.getPatient().getBedNo(),
                    mwp.getMed().getMedicineName(),
                    mwp.getMed().getBoxNo(),
                    RoomKey.TIME_MED.get(mwp.getMed().getUnixTime()));

            holder.binding.textView1.setText(text);
        }

        @Override
        public int getItemCount() {
            return mwpList.size();
        }

        protected static class ViewHolder extends RecyclerView.ViewHolder {
            RcvUpcmngItemBinding binding;

            public ViewHolder(@NonNull RcvUpcmngItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}