package com.example.rsepphase2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rsepphase2.databinding.FragmentSecondBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private static final String APPLICATION_NAME = "RSEP2";

    Button testButton;
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
        Bundle bundle = this.getArguments();
        String myValue = bundle.getString("message");
        testButton = (Button) getActivity().findViewById(R.id.btn_add_item);
        Spinner editFlatId = (Spinner) getActivity().findViewById(R.id.et_flat_id);
        if(myValue == "addExpense") {
            testButton.setText("Add Expense");
            String[] bankNames={"Choose Reason","Lift", "STP", "Commom", "Others"};
            ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,bankNames);
            myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editFlatId.setAdapter(myadapter);
        } else {
            testButton.setText("Add Income");
            String[] bankNames={"Choose Owner","Rajesh (L1-F1)","Arun Rajesh (L1-F2)","Prabu L1-S1","Muthu L1-S2","Stalin L2-F1","Srini L2-F2","Mani L2-S1","Ramesh L2-S2","L3-F1","L3-F2","L3-S1","L3-S2"};
            ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,bankNames);
            myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editFlatId.setAdapter(myadapter);
        }






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

        binding.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editTextUserName = (EditText) getActivity().findViewById(R.id.et_user_name);
                EditText editTextRoom = (EditText) getActivity().findViewById(R.id.et_room);
                EditText editTextComment = (EditText) getActivity().findViewById(R.id.et_comment);
                Spinner editFlatId = (Spinner) getActivity().findViewById(R.id.et_flat_id);



                final ProgressDialog loading = ProgressDialog.show(getActivity(),
                        "Adding...",
                        "Please wait");
                final String id = editFlatId.getSelectedItem().toString().trim();
                final String date = editTextUserName.getText().toString().trim();

                final String amount = editTextRoom.getText().toString().trim();
                final String comment = editTextComment.getText().toString().trim();
                String actionvalue = "";
                if(myValue == "addExpense") {
                      actionvalue = "Add Expense";
                } else {
                      actionvalue = "Add Income";
                }
                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirm Order")
                        .setMessage("Are you sure "+ actionvalue +" ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxhiR3V3BM0CRUpOw995rqW_ukXT9yLPWrkl_lPi_ewOD4ubx7OIjX7/exec",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("resp",response);
                                        loading.dismiss();
                                        editFlatId.setSelection(0);
                                        editTextUserName.setText("");
                                        editTextRoom.setText("");
                                        editTextComment.setText("");

//                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl()));
//                                    startActivity(intent);

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

                                String amt = amount;
                                if(myValue == "addExpense") {
                                    amt = '-' + amt;
                                }
                                //here we pass params
                                parmas.put("action","addExpense");
                                parmas.put("id",id);
                                parmas.put("date",date);
                                parmas.put("amount",amt);
                                parmas.put("comment",comment);



                                return parmas;
                            }
                        };

                        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

                        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(retryPolicy);

                        RequestQueue queue = Volley.newRequestQueue(getActivity());

                        queue.add(stringRequest);

                        // Con
                    }
                })
    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        loading.dismiss();
                    }
                })
    .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }
        });
    }



    public static String whatsappUrl(){

        final String BASE_URL = "https://api.whatsapp.com/";
        final String WHATSAPP_PHONE_NUMBER = "917401767525";    //'62' is country code for Indonesia
        final String PARAM_PHONE_NUMBER = "phone";
        final String PARAM_TEXT = "text";
        final String TEXT_VALUE = "Hello, How are you ?";

        String newUrl = BASE_URL + "send";

        Uri builtUri = Uri.parse(newUrl).buildUpon()
                .appendQueryParameter(PARAM_PHONE_NUMBER, WHATSAPP_PHONE_NUMBER)
                .appendQueryParameter(PARAM_TEXT, TEXT_VALUE)
                .build();

        return buildUrl(builtUri).toString();
    }

    public static URL buildUrl(Uri myUri){

        URL finalUrl = null;
        try {
            finalUrl = new URL(myUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        return finalUrl;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}