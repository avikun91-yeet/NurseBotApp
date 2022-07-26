package com.ra.nursebot.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ra.nursebot.R;
import com.ra.nursebot.data.room.MedicineInfoTable;
import com.ra.nursebot.data.room.Prescription;
import com.ra.nursebot.data.room.RoomKey;
import com.ra.nursebot.databinding.ActivityPrescriptionAddBinding;

import java.util.ArrayList;
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
                MedicineInfoTable medicine = new MedicineInfoTable();

                medicine.setMedicineName(binding.tietMedName.getText().toString());
                medicine.setBoxNo(Integer.parseInt(binding.actvBoxNo.getText().toString()));
                medicine.setUnixTime(RoomKey.MED_TIME.get(binding.actvMedTime.getText().toString()));

                //Add the medicine
                mViewModel.addMedicine(medicine);

                //clear the fields
                clearAddMedFields();
            }
        });

        binding.mbSave.setOnClickListener(v -> {
            List<MedicineInfoTable> medicineList = mViewModel.getMedicineList().getValue();
            if(medicineList.isEmpty()) {
                Toast.makeText(this, "No medicines", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(binding.tietPatientName.getText())){
                Toast.makeText(this, "Patient name isn't entered", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(binding.tietBedNo.getText())){
                Toast.makeText(this, "Bed number isn't entered", Toast.LENGTH_SHORT).show();
                return;
            }
            Prescription prescription = new Prescription();

            prescription.setPatientName(binding.tietPatientName.getText().toString());
            prescription.setBedNo(Integer.parseInt(binding.tietBedNo.getText().toString()));
            prescription.setMedicines(medicineList);
            Timber.d("Med list sent to save:%s", medicineList);

            mViewModel.insertPrescription(prescription);

            //clear fields
            clearAllFields();
            finish();
        });
    }

    private void clearAddMedFields() {
        binding.tietMedName.setText("");
        binding.actvMedTime.setText("");
        binding.actvBoxNo.setText("");
    }

    private void clearAllFields() {
        clearAddMedFields();
        binding.tietPatientName.setText("");
        binding.tietBedNo.setText("");
        mViewModel.clearMedicines();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prescription_add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_discard) {
            clearAllFields();
            finish();
        }
        return super.onOptionsItemSelected(item);
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
        medTimeAdapter.add(RoomKey.TIMES[0]);
        medTimeAdapter.add(RoomKey.TIMES[1]);
        medTimeAdapter.add(RoomKey.TIMES[2]);


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

    public class FormMedItemAdapter extends ArrayAdapter<MedicineInfoTable> {
        private final int resource;

        public FormMedItemAdapter(@NonNull Context context, int resource, @NonNull List<MedicineInfoTable> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            }
            MedicineInfoTable med = getItem(position);
            String text = String.format(Locale.getDefault(), "%s;%d;%s", med.getMedicineName(), med.getBoxNo(), RoomKey.TIME_MED.get(med.getUnixTime()));
            Timber.d("text:%s", text);
            ((TextView) convertView.findViewById(R.id.tv_med_desc)).setText(text);
            ((ImageButton) convertView.findViewById(R.id.ib_remove)).setOnClickListener(v -> mViewModel.removeMedicine(med));

            return convertView;
        }
    }
}