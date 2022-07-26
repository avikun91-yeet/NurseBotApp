package com.ra.nursebot.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ra.nursebot.R;
import com.ra.nursebot.data.model.Medicine;
import com.ra.nursebot.data.model.Prescription;
import com.ra.nursebot.data.room.RoomKey;
import com.ra.nursebot.databinding.ActivityPrescriptionAddBinding;
import com.ra.nursebot.databinding.FormMedicineItemBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import timber.log.Timber;

public class PrescriptionAddActivity extends AppCompatActivity {

    private ActivityPrescriptionAddBinding binding;
    private PrescAddViewModel mViewModel;

    private FormMedItemAdapter medItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(PrescAddViewModel.class);
        mViewModel.getMedicineList().observe(this, medicines -> {
            Timber.d("Observer called,med:%s", medicines);
            medItemAdapter.clear();
            medItemAdapter.addAll(medicines);
        });

        medItemAdapter = new FormMedItemAdapter(this, R.layout.form_medicine_item, new ArrayList<>());
        binding.lvMedicineList.setAdapter(medItemAdapter);

        initAutoCompleteOptions();
        binding.mbAddMed.setOnClickListener(v -> {
            Timber.d("Timber:checkFilledAddBox():%s", checkFilledAddBox());
            if (checkFilledAddBox()) {
                Medicine medicine = new Medicine(
                        Objects.requireNonNull(binding.tietMedName.getText()).toString(),
                        binding.actvMedTime.getText().toString(),
                        Integer.parseInt(binding.actvBoxNo.getText().toString()));
                //Add the medicine
                mViewModel.addMedicine(medicine);

                //clear the fields
                binding.tietMedName.setText("");
                binding.actvMedTime.setText("");
                binding.actvBoxNo.setText("");
            }
        });

//        binding.mbSave.setOnClickListener(v -> {
//            List<Medicine> medicineList = mViewModel.getMedicineList().getValue();
//            if(medicineList.isEmpty()) {
//                Toast.makeText(this, "No medicines", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if(TextUtils.isEmpty(binding.tietPatientName.getText())){
//                Toast.makeText(this, "Patient name isn't entered", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(binding.tietBedNo.getText())){
//                Toast.makeText(this, "Bed number isn't entered", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Prescription prescription = new Prescription(
//                    binding.tietPatientName.getText().toString(),
//                    binding.tietBedNo.getText().toString(), medicineList);
//
//        });
        binding.mbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("%s", mViewModel.getAllByPatient().getValue());
            }
        });
    }




    private void initAutoCompleteOptions() {
        ArrayAdapter<String> boxNoAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line
        );
        boxNoAdapter.add("1");
        boxNoAdapter.add("2");
        boxNoAdapter.add("3");

        ArrayAdapter<String> medTimeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line
        );
        medTimeAdapter.add("Breakfast");
        medTimeAdapter.add("Lunch");
        medTimeAdapter.add("Dinner");


        binding.actvBoxNo.setAdapter(boxNoAdapter);
        binding.actvMedTime.setAdapter(medTimeAdapter);
    }

    private boolean checkFilledAddBox() {
        if (TextUtils.isEmpty(binding.actvBoxNo.getText())) {
            Timber.d("checkFilledAddBox:returned from 1");
            Toast.makeText(this, "Enter box no.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.actvMedTime.getText())) {
            Timber.d("checkFilledAddBox:returned from 2");
            Toast.makeText(this, "Enter time of medicine.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.tietMedName.getText())) {
            Timber.d("checkFilledAddBox:returned from 3");
            Toast.makeText(this, "Enter medicine name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Timber.d("checkFilledAddBox:true");
        return true;
    }

    public static class FormMedItemAdapter extends ArrayAdapter<Medicine> {
        private final int resource;

        public FormMedItemAdapter(@NonNull Context context, int resource, @NonNull List<Medicine> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            }
            Medicine med = getItem(position);
            String text = String.format(Locale.getDefault(), "%s;%d;%s", med.getName(), med.getBoxNo(), med.getTime());
            Timber.d("text:%s", text);
            ((TextView) convertView.findViewById(R.id.tv_med_desc)).setText(text);
            ((ImageButton) convertView.findViewById(R.id.ib_remove)).setOnClickListener(v -> remove(med));

            return convertView;
        }
    }
}