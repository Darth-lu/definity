package com.fire.definity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;



import com.fire.definity.R;
import com.fire.definity.entidades.Docente;
import com.fire.definity.entidades.LisAsis;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.AsistenciaHolder> {
    List<LisAsis> listaA;







    public AsistenciaAdapter(List<LisAsis> listaA) {
        this.listaA = listaA;

    }


    public AsistenciaAdapter.AsistenciaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.asistencia,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);






        return new AsistenciaAdapter.AsistenciaHolder(view);



    }




    public void onBindViewHolder(final AsistenciaAdapter.AsistenciaHolder holder, final int position) {
        final int pos = position;
        final LisAsis doc = listaA.get(position);


        holder.txtNombre.setText(doc.getNombre());
        holder.txtContador.setText(doc.getContador());








    }

    public int getItemCount() {
        return listaA.size();
    }



    public class AsistenciaHolder extends RecyclerView.ViewHolder{

        TextView txtContador,txtNombre;
        CheckBox asis;
        Button btn;
        public AsistenciaHolder(View itemView) {
            super(itemView);

            txtContador = itemView.findViewById(R.id.Asistencias);
            txtNombre = itemView.findViewById(R.id.NombreAsistencia);




            //checkbox click event handling


        }


    }
}
