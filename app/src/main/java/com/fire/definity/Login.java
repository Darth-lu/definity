package com.fire.definity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.fire.definity.adapter.DocenteAdapter;
import com.fire.definity.entidades.Docente;
import com.fire.definity.entidades.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    public static final String DATA_ID = "id";
    public static final String NOMBRE = "nombreUs";


    RecyclerView recyclerDocentes;

    ArrayList<User> ListaUser;
    ProgressDialog progres;

    RequestQueue request;
    JsonRequest jRequest;

    ArrayAdapter<String> AdpaterUser;

    Button botonlog;
    EditText usu, pass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);






        usu = findViewById(R.id.loginUsu);
        pass = findViewById(R.id.loginPass);
        botonlog = findViewById(R.id.loginBoton);
        request = Volley.newRequestQueue(getApplicationContext());




        botonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                consultar();


            }
        });



    }

    private void consultar() {

        String url = "https://chvd.000webhostapp.com/sesion.php?nombreusu="+usu.getText().toString()+"&pass="+pass.getText().toString();

        jRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jRequest);
}

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"Contrasena o Usuario no valido",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onResponse(JSONObject response) {

        usu = findViewById(R.id.loginUsu);
        pass = findViewById(R.id.loginPass);


        User usarioNuevo = new User();

        JSONArray json=response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);

                usarioNuevo.setNombreusu(jsonObject.optString("nombreusu"));
                usarioNuevo.setPass(jsonObject.optString("pass"));
                usarioNuevo.setId(jsonObject.optInt("id"));

            Bundle caja = new Bundle();
            caja.putInt(DATA_ID,usarioNuevo.getId());

            ListaFragment fragment = new ListaFragment();
            fragment.setArguments(caja);


        }
        catch (JSONException e) {
            e.printStackTrace();
            progres.hide();
            Toast.makeText(getApplicationContext(),"No se pudo consultar"+response,Toast.LENGTH_SHORT).show();
        }



        if ((usu.getText().toString().equals(usarioNuevo.getNombreusu())&& (pass.getText().toString().equals(usarioNuevo.getPass())))){


                Intent intencion = new Intent(getApplicationContext(),MainActivity.class);
                intencion.putExtra(DATA_ID,usarioNuevo.getId());
                intencion.putExtra(NOMBRE,usarioNuevo.getNombreusu());
                startActivity(intencion);
                finish();




    }else if(usu.getText() == null && pass.getText() == null) {
            Toast.makeText(getApplicationContext(),"Campos vacios",Toast.LENGTH_SHORT).show();
        }


    }


}
