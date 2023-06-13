package com.example.acn4av_jeronimo_lago.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.activities.ConcesionariaDetailActivity;
import com.example.acn4av_jeronimo_lago.activities.ConcesionariasActivity;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

import java.util.ArrayList;

public class ConcessionaryListAdapter extends RecyclerView.Adapter<ConcessionaryListAdapter.ConcessionaryViewHolder> {

    ArrayList<Concessionary> concessionaryArrayList;

    public ConcessionaryListAdapter(ArrayList<Concessionary> concessionaryArrayList) {
        this.concessionaryArrayList = concessionaryArrayList;
    }

    @NonNull
    @Override
    public ConcessionaryListAdapter.ConcessionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_concesionaria,null,false);
        return new ConcessionaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcessionaryListAdapter.ConcessionaryViewHolder holder, int position) {

        holder.concessionaryName.setText(concessionaryArrayList.get(position).getName());
        String fullAddress =  concessionaryArrayList.get(position).getAddres()
                +" "+concessionaryArrayList.get(position).getNumber()
                +","+concessionaryArrayList.get(position).getCity();
        holder.concessionaryAddress.setText(fullAddress);
        holder.btnDelete.setOnClickListener(view -> {
            ConcessionaryController controller = new ConcessionaryController(view.getContext());
            controller.open();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("¿Deseas eliminar esta concesionaria?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(controller.deleteConcessionary(concessionaryArrayList.get(holder.getAdapterPosition()).getId())){
                                Toast.makeText(view.getContext(), "Concesionaria eliminada exitosamente!", Toast.LENGTH_SHORT).show();
                                Intent launchReload = new Intent(view.getContext(), ConcesionariasActivity.class);
                                view.getContext().startActivity(launchReload);
                            }
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(view.getContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();

                        }
                    }).show();
            controller.close();
        });
        holder.itemView.setOnClickListener( view -> {
            Intent launchConcessionaryDetailActivity = new Intent(view.getContext(), ConcesionariaDetailActivity.class);
            launchConcessionaryDetailActivity.putExtra("ID", concessionaryArrayList.get(holder.getAdapterPosition()).getId());
            view.getContext().startActivity(launchConcessionaryDetailActivity);
            ConcesionariasActivity.class.cast(view.getContext()).finish();
        });
    }

    @Override
    public int getItemCount() {
        return concessionaryArrayList.size();
    }

    public class ConcessionaryViewHolder extends RecyclerView.ViewHolder {
        TextView concessionaryName, concessionaryAddress;
        ImageButton btnDelete;
        public ConcessionaryViewHolder(@NonNull View itemView) {
            super(itemView);

            concessionaryName = itemView.findViewById(R.id.tvNombreConcesionaria);
            concessionaryAddress = itemView.findViewById(R.id.tvDireccion);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            itemView.setOnClickListener(view -> {
                Intent launchConcessionaryDetailActivity = new Intent(view.getContext(), ConcesionariaDetailActivity.class);
                launchConcessionaryDetailActivity.putExtra("ID", concessionaryArrayList.get(getAdapterPosition()).getId());
                view.getContext().startActivity(launchConcessionaryDetailActivity);
            });
        }
    }
}
