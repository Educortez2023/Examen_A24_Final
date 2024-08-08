package ec.com.bienesracies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ec.com.bienesracies.R;
import ec.com.bienesracies.models.Propiedades;

import java.util.List;

public class PropiedadesAdapter extends RecyclerView.Adapter<PropiedadesAdapter.PropiedadesViewHolder>{
    private List<Propiedades> propiedadesList;

    public PropiedadesAdapter(List<Propiedades> propiedadesList) {

        this.propiedadesList = propiedadesList;
    }

    @NonNull
    @Override
    public PropiedadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propiedades, parent, false);
        return new PropiedadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropiedadesViewHolder holder, int position) {
       Propiedades propiedades = propiedadesList.get(position);
        holder.propiedadesId.setText(String.valueOf(propiedades.getId_agente()));
        holder.propiedadesDireccion.setText(propiedades.getDireccion());
        holder.propiedadesDescription.setText(propiedades.getDescripcion());
        holder.propiedadesPrecio.setText(String.valueOf(propiedades.getPrecio()));
        holder.agenteId.setText(String.valueOf(propiedades.getId_agente()));
    }

    @Override
    public int getItemCount() {
        return propiedadesList.size();
    }

    static class PropiedadesViewHolder extends RecyclerView.ViewHolder {
        TextView propiedadesId, propiedadesDireccion, propiedadesDescription, propiedadesPrecio, agenteId;

        public PropiedadesViewHolder(@NonNull View itemView) {
            super(itemView);
            propiedadesId = itemView.findViewById(R.id.tvId);
            propiedadesDireccion = itemView.findViewById(R.id.tvDireccion);
            propiedadesDescription = itemView.findViewById(R.id.tvDescripcion);
            propiedadesPrecio = itemView.findViewById(R.id.tvPrecio);
            agenteId = itemView.findViewById(R.id.tvAgenteId);
        }
    }
}
