package com.example.database1;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextNis;
    private EditText editTextName;
    private EditText editTextAddress;
    private Spinner spinnerCity;
    private RadioGroup chooseGender;
    private EditText editTextAge;
    private Button buttonAdd;

    private String[] Item = {"Jakarta", "Bogor", "Depok", "Tanggerang", "Bekasi", "Bandung"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner List = findViewById(R.id.spinnerCity);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Item);
        List.setAdapter(adapter);

        editTextNis = (EditText) findViewById(R.id.editTextNis);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        chooseGender = (RadioGroup) findViewById(R.id.chooseGender);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener((View.OnClickListener) this);
    }

    private void addPersonalData() {

        final String nis = editTextNis.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String city = spinnerCity.toString().trim();
        final String gender = chooseGender.toString().trim();
        final String age = editTextAge.getText().toString().trim();

        class AddPersonalDate extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(configuration.KEY_EMP_NIS, nis);
                params.put(configuration.KEY_EMP_NAME, name);
                params.put(configuration.KEY_EMP_ADDRESS, address);
                params.put(configuration.KEY_EMP_CITY, city);
                params.put(configuration.KEY_EMP_GENDER, gender);
                params.put(configuration.KEY_EMP_AGE, age);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_ADD, params);
                return res;
            }
        }
        AddPersonalDate ae = new AddPersonalDate();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v==buttonAdd) {
            addPersonalData();
        }
    }
}
