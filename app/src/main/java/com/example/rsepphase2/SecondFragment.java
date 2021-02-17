package com.example.rsepphase2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

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
                EditText editFlatId = (EditText) getActivity().findViewById(R.id.et_flat_id);
                EditText editTextUserName = (EditText) getActivity().findViewById(R.id.et_user_name);
                EditText editTextRoom = (EditText) getActivity().findViewById(R.id.et_room);
                final ProgressDialog loading = ProgressDialog.show(getActivity(),
                        "Adding Flat",
                        "Please wait");
                final String id = editFlatId.getText().toString().trim();
                final String name = editTextUserName.getText().toString().trim();
                final String room = editTextRoom.getText().toString().trim();




                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxhiR3V3BM0CRUpOw995rqW_ukXT9yLPWrkl_lPi_ewOD4ubx7OIjX7/exec",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                loading.dismiss();
                                Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                                startActivity(intent);

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
                        parmas.put("action","addFlat");
                        parmas.put("id",id);
                        parmas.put("name",name);
                        parmas.put("bedRoom",room);

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