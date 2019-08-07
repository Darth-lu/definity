package com.fire.definity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fire.definity.entidades.User;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final int userId = MainActivity.idus;
    EditText matricula, nombre, apellido, apellidoM,direccion,telefono,correo,plantel,perfil;
    Button registrar;
    ProgressDialog progres;

    RequestQueue request3;
    JsonObjectRequest jsonObjectRequest3;

    private OnFragmentInteractionListener mListener;

    public RegistrarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarFragment newInstance(String param1, String param2) {
        RegistrarFragment fragment = new RegistrarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registrar, container, false);
        matricula = view.findViewById(R.id.regediMatri);
        nombre = view.findViewById(R.id.regediNom);
        apellido = view.findViewById(R.id.regApellidoP);
        apellidoM = view.findViewById(R.id.regApellidoM);
        direccion = view.findViewById(R.id.regDirec);
        telefono = view.findViewById(R.id.regTel);
        correo = view.findViewById(R.id.regEmail);
        plantel = view.findViewById(R.id.regPlan);
        perfil = view.findViewById(R.id.regPerfil);





        registrar = view.findViewById(R.id.registrar);

        request3 = Volley.newRequestQueue(getContext());

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                webService1();
            }
        });




        return view;
    }

    private void webService1() {

        progres = new ProgressDialog(getContext());
        progres.setMessage("Cargando...");
        progres.show();



        String url = "https://chvd.000webhostapp.com/ins.php?matricula="+matricula.getText().toString()+"&nombre="+nombre.getText().toString()+"&apellidoP="+apellido.getText().toString()
                +"&apellidoM="+apellidoM.getText().toString()+"&direccion="+direccion.getText().toString()+"&telefono="+telefono.getText().toString()
                +"&correo="+correo.getText().toString()+"&plantel="+plantel.getText().toString()+"&perfil="+perfil.getText().toString()+"&user_id="+
                userId;

       url = url.replace(" ","%20");


       jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request3.add(jsonObjectRequest3);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progres.hide();
        Toast.makeText(getContext(),"No se pudo registrar",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Registro Exitoso",Toast.LENGTH_SHORT).show();
        progres.hide();

        matricula.setText("");
        nombre.setText("");
        apellido.setText("");
        apellidoM.setText("");
        direccion.setText("");
        telefono.setText("");
        correo.setText("");
        plantel.setText("");
        perfil.setText("");





    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
