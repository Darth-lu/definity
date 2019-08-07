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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fire.definity.entidades.Docente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText campoNom;
    TextView  txtNombre, txtApellido,txtApellidoM,txtDireccion,txtTelefono,txtCorreo,txtPlantel,txtPerfil;
    Button Buscar;
    ProgressDialog progres;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public ConsultaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaFragment newInstance(String param1, String param2) {
        ConsultaFragment fragment = new ConsultaFragment();
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
        View view =  inflater.inflate(R.layout.fragment_consulta, container, false);
        campoNom = view.findViewById(R.id.editID);
       txtNombre = view.findViewById(R.id.consuNom);
       txtApellido = view.findViewById(R.id.consuApel);
       txtApellidoM =view.findViewById(R.id.consuApelM);
       txtDireccion = view.findViewById(R.id.consuDirec);
       txtTelefono = view.findViewById(R.id.consuTel);
       txtCorreo = view.findViewById(R.id.consuCorreo);
       txtPlantel = view.findViewById(R.id.consuPlan);
       txtPerfil =view.findViewById(R.id.consuPerfil);
        Buscar = view.findViewById(R.id.Buscar);


        request = Volley.newRequestQueue(getContext());

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscraWeb();
            }
        });


        return view;
    }

    private void BuscraWeb() {
        progres = new ProgressDialog(getContext());
        progres.setMessage("Consultando...");
        progres.show();


        String url ="https://chvd.000webhostapp.com/consluta.php?id="+campoNom.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

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
        Toast.makeText(getContext(),"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {
    progres.hide();


    Docente docente = new Docente();

        JSONArray json = response.optJSONArray("personal");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            docente.setId(jsonObject.optInt("id"));
            docente.setNombre(jsonObject.optString("nombre"));
            docente.setApellido(jsonObject.optString("apellidoP"));
            docente.setApellidoM(jsonObject.optString("apellidoM"));
            docente.setDireccion(jsonObject.optString("direccion"));
            docente.setTelefono(jsonObject.optString("telefono"));
            docente.setCorreo(jsonObject.optString("correo"));
            docente.setPlantel(jsonObject.optString("plantel"));
            docente.setPerfil(jsonObject.optString("perfil"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNombre.setText(docente.getNombre());
        txtApellido.setText(docente.getApellido());
        txtApellidoM.setText(docente.getApellidoM());
        txtDireccion.setText(docente.getDireccion());
        txtTelefono.setText(docente.getTelefono());
        txtCorreo.setText(docente.getCorreo());
        txtPlantel.setText(docente.getPlantel());
        txtPerfil.setText(docente.getPerfil());



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
