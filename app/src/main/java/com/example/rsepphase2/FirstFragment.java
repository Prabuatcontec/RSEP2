package com.example.rsepphase2;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rsepphase2.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {

    ListView lvProgram;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressDialog loading = ProgressDialog.show(getActivity(),
                "Loading...",
                "Please wait");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxhiR3V3BM0CRUpOw995rqW_ukXT9yLPWrkl_lPi_ewOD4ubx7OIjX7/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resp",response);
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response.toString());
                            JSONArray values= obj.getJSONArray("values");
                            ArrayList<String> pname = new ArrayList<String>();
                            ArrayList<String> mname = new ArrayList<String>();
                            ArrayList<String> sname = new ArrayList<String>();
                            ArrayList<String> oname = new ArrayList<String>();
                            for (int i=0;i<values.length();i++){
                                JSONArray jsonarray1 = (JSONArray) values.get(i);
                                String str = jsonarray1.toString();
                                str = str.replace("[","");
                                str = str.replace("\"", "");
                                str = str.replace("]","");
                                String[] arrOfStr = str.split(",", 4);
                                for (int o=0; o < arrOfStr.length; o++)
                                {
                                    if(arrOfStr[o]!="") {
                                        if (o == 0) {
                                            pname.add(arrOfStr[o]);
                                        }
                                        if (o == 1) {
                                            sname.add(arrOfStr[o]);
                                        }
                                        if (o == 2) {
                                            mname.add(arrOfStr[o]);
                                        }
                                        if (o == 3) {
                                            String s = "                                                                           ";
                                            oname.add(arrOfStr[o] + s);
                                        }
                                    }
                                }
                            }
                            loading.dismiss();

                            Collections.reverse(pname);
                            Collections.reverse(sname);
                            Collections.reverse(mname);
                            Collections.reverse(oname);

                            lvProgram = getActivity().findViewById(R.id.lvprogram);

                            ProgramAdapator programAdapator = new ProgramAdapator(getContext(), pname, sname, mname, oname);

                            lvProgram.setAdapter(programAdapator);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        for(int i=0; i < array.length(); i++)
//                        {
//                            JSONObject object = null;
//                            try {
//                                object = array.getJSONObject(i);
//                                Log.d("array", object.toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }




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


                parmas.put("action","getTransactions");



                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        queue.add(stringRequest);







    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}