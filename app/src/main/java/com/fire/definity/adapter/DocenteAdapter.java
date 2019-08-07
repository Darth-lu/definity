package com.fire.definity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fire.definity.AsistenciasFragment;
import com.fire.definity.ListaFragment;
import com.fire.definity.MainActivity;
import com.fire.definity.R;
import com.fire.definity.entidades.Docente;
import com.fire.definity.entidades.LisAsis;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DocenteAdapter extends RecyclerView.Adapter<DocenteAdapter.DocentesHolder> implements Response.Listener<JSONObject>, Response.ErrorListener {

    List<Docente> listaDocente;
Context ctx;
 ArrayList<Docente> doceAd;
String checado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button btn;
    AsistenciasFragment main;
    ListaFragment lis;
    public DocenteAdapter(List<Docente> listaDocentes) {
        this.listaDocente = listaDocentes;

    }


    public DocentesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.docentes,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);






        return new DocentesHolder(view);



    }




    public void onBindViewHolder(final DocentesHolder holder, final int position) {
        final int pos = position;
        final Docente doc = listaDocente.get(position);
        holder.txtNombre.setText(doc.getNombre());
        holder.txtApellido.setText(doc.getApellido());

        holder.asis.setChecked(listaDocente.get(position).isSelected());
        holder.asis.setTag(listaDocente.get(position));


            holder.asis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  CheckBox cb = (CheckBox) v;
                  Docente docente = (Docente) cb.getTag();

                  docente.setSelected(cb.isChecked());
                  listaDocente.get(pos).setSelected(cb.isChecked());
                  listaDocente.get(pos).setPosition(position);





                  Toast.makeText(v.getContext(),"Click en CheckBox"+cb.getText()+
                          " es "+cb.isChecked()+doc.getId(),Toast.LENGTH_SHORT).show();
                }
            });


    }

     public int getItemCount() {
        return listaDocente.size();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(ctx,"mensaje" + response,Toast.LENGTH_SHORT).show();





    }

    public class DocentesHolder extends RecyclerView.ViewHolder{

        TextView txtApellido,txtNombre;
        CheckBox asis;
        Button btn;
        public DocentesHolder(View itemView) {
            super(itemView);
            txtNombre= (TextView) itemView.findViewById(R.id.NombreRec);
            txtApellido= (TextView) itemView.findViewById(R.id.ApellidoRec);
            asis = itemView.findViewById(R.id.checar);
            btn = itemView.findViewById(R.id.bntAsis);





            //checkbox click event handling


        }


    }


        public List<Docente> getDocenteList(){
        return listaDocente;
        }

}
