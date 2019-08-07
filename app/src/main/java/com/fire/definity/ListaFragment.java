package com.fire.definity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fire.definity.adapter.DocenteAdapter;
import com.fire.definity.entidades.Docente;
import com.fire.definity.entidades.LisAsis;
import com.fire.definity.entidades.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Login l = new Login();
   public  static final Integer q = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerDocentes;

    ArrayList<Docente> ListaDocentes;
    public ArrayList<String> asistencia;
    ProgressDialog progres;
    DocenteAdapter docenteAdapter;
    RequestQueue request;
    RequestQueue request2;
    JsonObjectRequest jsonObjectRequest;
    JsonObjectRequest jsonObjectRequest2;
     Button boton;

     CheckBox ch1;
     RecyclerView.Adapter miAdaptador;

    private OnFragmentInteractionListener mListener;

    public ListaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaFragment newInstance(String param1, String param2) {
        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    int datos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        int datofianl = MainActivity.idus;

        if (datofianl != 0){

            Toast.makeText(getContext(),"Seleccionado: \n"+" id es: " + datofianl,Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getContext(),"No hay" ,Toast.LENGTH_LONG).show();
        }


    }

    List<Docente> docenteList;
    private Button btnSelection;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista, container, false);


        ListaDocentes = new ArrayList<>();

        asistencia = new ArrayList<>();




        recyclerDocentes = view.findViewById(R.id.idrecycle);
        recyclerDocentes.setHasFixedSize(true);
        recyclerDocentes.setLayoutManager(new LinearLayoutManager(this.getContext()));


        request = Volley.newRequestQueue(getContext());

        cargarLista();

            final int idfin = MainActivity.idus;
        btnSelection = view.findViewById(R.id.bntAsis);


            btnSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreV = "";

                   String matricula = "";
                    int id2 = 0;


                    DocenteAdapter adapter= new DocenteAdapter(ListaDocentes);
                    recyclerDocentes.setAdapter(adapter);
                    List<Docente> dcList = adapter.getDocenteList();
                    request2 = Volley.newRequestQueue(getContext());
                    for (int i =0;i < dcList.size();i++){
                        Docente unDocente = dcList.get(i);
                        if (unDocente.isSelected()==true){

                            nombreV = nombreV + "\n" + unDocente.getNombre();
                            matricula = matricula+ "\n"+ unDocente.getMatricula();
                            id2 = id2 + unDocente.getId();


                        }
                        enviarAsistencia("https://chvd.000webhostapp.com/insertasis.php?nombre="+nombreV+"&asistencia="+matricula+"&personal_id="+id2);
                        Toast.makeText(getContext(),"Seleccionado: \n"+nombreV+" "+ matricula ,Toast.LENGTH_LONG).show();
                      nombreV = "";
                      matricula = "";
                      id2 = 0;


                    }





                    //request2 = Volley.newRequestQueue(getContext());
                }
            });



        return view;
    }

    private void cargarLista() {

        progres = new ProgressDialog(getContext());
        progres.setMessage("Consultando...");
        progres.show();
        int userId = MainActivity.idus;
        String url = "https://chvd.000webhostapp.com/lista.php?user_id="+userId;

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
        Toast.makeText(getContext(),"No se pudo consultar",Toast.LENGTH_SHORT).show();

    }


    String datito;

    @Override
    public void onResponse(JSONObject response) {
        Docente docente = null;

        JSONArray json=response.optJSONArray("personal");
        try {
        for (int i=0;i < json.length();i++){
                docente = new Docente();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                docente.setId(jsonObject.optInt("id"));
                docente.setMatricula(jsonObject.optString("matricula"));
                docente.setNombre(jsonObject.optString("nombre"));
                docente.setApellido(jsonObject.getString("apellidoP"));
                ListaDocentes.add(docente);

            }


           DocenteAdapter adapter= new DocenteAdapter(ListaDocentes);
            recyclerDocentes.setAdapter(adapter);
            progres.hide();






        }
        catch (JSONException e) {
            e.printStackTrace();
            progres.hide();
            Toast.makeText(getContext(),"No se pudo consultar"+response,Toast.LENGTH_SHORT).show();
        }







    }
    private void enviarAsistencia(final String url2){



        jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(),"Enviado",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request2.add(jsonObjectRequest2);






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
