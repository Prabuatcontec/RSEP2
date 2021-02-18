package com.example.rsepphase2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rsepphase2.databinding.FragmentSecondBinding;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }




    private FragmentSecondBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] bankNames={"Choose Flat","Rajesh (L1-F1)","Arun Rajesj (L1-F2)","L1-S1","L1-S2","L2-F1","L2-F2","L2-S1","L2-S2","L1-F1","L1-F2","L1-S1","L1-S2"};
        Spinner editFlatId = (Spinner) getActivity().findViewById(R.id.et_flat_id);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,bankNames);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editFlatId.setAdapter(myadapter);

        EditText editTextUserName = (EditText) getActivity().findViewById(R.id.et_user_name);
        editTextUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }

            public void openDatePickerDialog(final View v) {
                Calendar cal = Calendar.getInstance();

                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            switch (v.getId()) {
                                case R.id.et_user_name:
                                    ((EditText)v).setText(selectedDate);
                                    break;
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });




//
//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });






        binding.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editTextUserName = (EditText) getActivity().findViewById(R.id.et_user_name);
                EditText editTextRoom = (EditText) getActivity().findViewById(R.id.et_room);
                EditText editTextComment = (EditText) getActivity().findViewById(R.id.et_comment);
                Spinner editFlatId = (Spinner) getActivity().findViewById(R.id.et_flat_id);



                final ProgressDialog loading = ProgressDialog.show(getActivity(),
                        "Adding Flat",
                        "Please wait");
                final String id = editFlatId.getSelectedItem().toString().trim();
                final String date = editTextUserName.getText().toString().trim();
                final String amount = editTextRoom.getText().toString().trim();
                final String comment = editTextComment.getText().toString().trim();




                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxhiR3V3BM0CRUpOw995rqW_ukXT9yLPWrkl_lPi_ewOD4ubx7OIjX7/exec",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("resp",response);
                                loading.dismiss();
                                Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
//                                startActivity(intent);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parmas = new HashMap<>();

                        //here we pass params
                        parmas.put("action","addExpense");
                        parmas.put("id",id);
                        parmas.put("date",date);
                        parmas.put("amount",amount);
                        parmas.put("comment",comment);



                        return parmas;
                    }
                };

                int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                queue.add(stringRequest);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}