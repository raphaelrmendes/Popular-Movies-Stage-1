package br.com.rochamendes.filmespopularesprojudacity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class filmesAdapter extends RecyclerView.Adapter<filmesAdapter.filmesViewHolder> {

    private filmes[] filmesList;
    private Context Contexto;

    filmesAdapter(filmes[] Filmes, Context contexto) {
        filmesList = Filmes;
        Contexto = contexto;
    }

    public class filmesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mCapaFilme;
        TextView mTituloFilme;

        filmesViewHolder(View itemView) {
            super(itemView);
            mCapaFilme = itemView.findViewById(R.id.miniaturaCapaFilme_mainActivity);
            mTituloFilme = itemView.findViewById(R.id.tituloFilme_mainActivity);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @NonNull
    @Override
    public filmesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filme_item_recyclerview, parent, false);
        return new filmesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final filmesViewHolder holder, int position) {
        try {
            //Final webaddress should look like "https://image.tmdb.org/t/p/w185/imageID.jpg"
            Uri.Builder construtorUri = new Uri.Builder();
            construtorUri.scheme("https")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath("w500")
                    .appendPath(filmesList[position].getCapaFilme());
            String UrlPesquisa = construtorUri.build().toString();
            ImageView imageView = holder.mCapaFilme;
            Picasso.get().load(UrlPesquisa).into(imageView);
            TextView textView = holder.mTituloFilme;
            textView.setText(filmesList[position].getNomeFilme());

            holder.itemView.setOnClickListener(view ->{
                try {
                    Intent intent = new Intent(Contexto, filmesDetalhes.class);
//                    intent.putExtra("Id", filmesList[position].getIdFilme());
                    intent.putExtra("Nome", filmesList[position].getNomeFilme());
                    intent.putExtra("NomeOriginal", filmesList[position].getNomeOriginal());
                    intent.putExtra("Capa", filmesList[position].getCapaFilme());
                    intent.putExtra("Idioma", filmesList[position].getIdiomaFilme());
                    intent.putExtra("Sinopse", filmesList[position].getSinopseFilme());
                    intent.putExtra("Data", filmesList[position].getDataFilme());
                    intent.putExtra("Classific", filmesList[position].getClassificFilme());
                    Contexto.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(Contexto, "Deu ruim!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (filmesList == null) return 0;
        return filmesList.length;
    }
}