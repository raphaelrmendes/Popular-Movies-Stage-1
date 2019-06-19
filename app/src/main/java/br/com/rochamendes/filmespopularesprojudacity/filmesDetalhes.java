package br.com.rochamendes.filmespopularesprojudacity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class filmesDetalhes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmes_detalhes);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            Toast.makeText(this, "Deu pau aqui no app!", Toast.LENGTH_SHORT).show();
        }

        TextView titulo = findViewById(R.id.tituloFilme);
        TextView tituloOriginal = findViewById(R.id.tituloOriginalFilme);
        ImageView capa = findViewById(R.id.capaFilmeDetalhes);
        TextView idioma = findViewById(R.id.idioma);
        TextView sinopse = findViewById(R.id.sinopse);
        TextView data = findViewById(R.id.data);
        TextView nota = findViewById(R.id.nota);

        titulo.setText(intent.getStringExtra("Nome"));
        tituloOriginal.setText(intent.getStringExtra("NomeOriginal"));
        idioma.setText(intent.getStringExtra("Idioma"));
        sinopse.setText(intent.getStringExtra("Sinopse"));
        data.setText(intent.getStringExtra("Data"));
        nota.setText(String.valueOf(intent.getFloatExtra("Classific",0)));

        try{
            //Final webaddress should look like "https://image.tmdb.org/t/p/w185/imageID.jpg"
            Uri.Builder construtorUri = new Uri.Builder();
            construtorUri.scheme("https")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath("w780")
                    .appendPath(intent.getStringExtra("Capa"));
            String UrlPesquisa = construtorUri.build().toString();
            Picasso.get().load(UrlPesquisa).into(capa);
        } catch (Exception e) {
            Toast.makeText(this, "Deu um trem errado aqui...", Toast.LENGTH_SHORT).show();
        }
    }
}
