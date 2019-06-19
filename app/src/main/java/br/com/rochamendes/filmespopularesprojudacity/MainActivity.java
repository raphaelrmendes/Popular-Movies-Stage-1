package br.com.rochamendes.filmespopularesprojudacity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    //ATTENTION!!! Insert your API Key down here! (Only here)
    private static final String apiKey = "place you API KEY - theMovieDB API";
    //                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    private RecyclerView filmesRecycler;
    RecyclerView.LayoutManager filmesLayout;
    filmesAdapter FilmesAdapter;
    filmes[] filmesList;
    SharedPreferences preferencias;
    String ordenamento;
    private ProgressBar carregamento;
    private TextView txtCarregamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregamento = findViewById(R.id.barraProgresso);
        txtCarregamento = findViewById(R.id.textoCarregamento);
        filmesRecycler = findViewById(R.id.filmesRecyclerView);
        filmesRecycler.setHasFixedSize(true);
        int colunas = 3;
        filmesLayout = new GridLayoutManager(this, colunas);
        filmesRecycler.setLayoutManager(filmesLayout);
        preferencias = getSharedPreferences("ordenamento",MODE_PRIVATE);
        ordenamento = preferencias.getString("ordenamento", "popular");
        if (ordenamento.equals("popular")) getSupportActionBar().setTitle("Mais Populares");
        else getSupportActionBar().setTitle("Mais Bem Classificados");
        new atualizaFilmes().execute(ordenamento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.ordemExibicao){
            AlertDialog.Builder construtor = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = preferencias.edit();
            int popular = 0;
            ordenamento = preferencias.getString("ordenamento","popular");
            if (ordenamento.equals("popular")) popular = 0;
            else popular = 1;
            construtor.setTitle("Ordem dos resultados");
            construtor.setSingleChoiceItems(new String[]{"Mais populares","Melhor Classificação"}, popular,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selecionado) {
                            if (selecionado == 0) editor.putString("ordenamento", "popular");
                            else editor.putString("ordenamento", "top_rated");
                        }
                    });
            construtor.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.apply();
                }
            });
            construtor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            construtor.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = construtor.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public class atualizaFilmes extends AsyncTask<String, Void, filmes[]> {

        protected void onPreExecute() {
            super.onPreExecute();
            visibilide(true);
        }

        protected filmes[] doInBackground(String... params) {
            try {
                // https://api.themoviedb.org/3/movie/popular?api_key=[api_key that you get on The Movies DB website]
                Uri.Builder construtorUri = new Uri.Builder();
                construtorUri.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(params[0])
                        .appendQueryParameter("api_key", apiKey);
                String UrlPesquisa = construtorUri.build().toString();

                URL url = new URL(UrlPesquisa);
                URLConnection requisicao = url.openConnection();
                requisicao.connect();

                JsonParser dadosJson = new JsonParser();
                JsonElement raizJson = dadosJson.parse(new InputStreamReader((InputStream) requisicao.getContent()));
                JsonObject pagPesqJSON = raizJson.getAsJsonObject();
                JsonArray resultadoPesqJSON = pagPesqJSON.getAsJsonArray("results");

                filmesList = new filmes[resultadoPesqJSON.size()];
                for (int i = 0; i < resultadoPesqJSON.size(); i++) {

                    JsonObject FilmeJson = resultadoPesqJSON.get(i).getAsJsonObject();

//                    JsonPrimitive id = FilmeJson.getAsJsonPrimitive("id");
                    JsonPrimitive titulo = FilmeJson.getAsJsonPrimitive("title");
                    JsonPrimitive tituloOriginal = FilmeJson.getAsJsonPrimitive("original_title");
                    JsonPrimitive capa = FilmeJson.getAsJsonPrimitive("poster_path");
                    JsonPrimitive idioma = FilmeJson.getAsJsonPrimitive("original_language");
                    JsonPrimitive sinopse = FilmeJson.getAsJsonPrimitive("overview");
                    JsonPrimitive lancamento = FilmeJson.getAsJsonPrimitive("release_date");
                    JsonPrimitive avaliacao = FilmeJson.getAsJsonPrimitive("vote_average");

                    filmesList[i] = new filmes(
//                            id.getAsInt(),
                            titulo.getAsString(),
                            tituloOriginal.getAsString(),
                            capa.getAsString().replace("/", ""),
                            idioma.getAsString(),
                            sinopse.getAsString(),
                            lancamento.getAsString(),
                            avaliacao.getAsFloat());
                }
                return filmesList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(filmes[] Filmes) {
            if (Filmes != null) {
                visibilide(false);
                FilmesAdapter = new filmesAdapter(Filmes, MainActivity.this);
                filmesRecycler.setAdapter(FilmesAdapter);
            } else {
                visibilide(false);
                Toast.makeText(getBaseContext(), "Erro ao buscar filmes", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void visibilide(Boolean Visibilidade) {      //referente a barra de progresso
        if (Visibilidade) {
            carregamento.setVisibility(View.VISIBLE);
            txtCarregamento.setVisibility(View.VISIBLE);
            filmesRecycler.setVisibility(View.GONE);
        } else {
            carregamento.setVisibility(View.GONE);
            txtCarregamento.setVisibility(View.GONE);
            filmesRecycler.setVisibility(View.VISIBLE);
        }
    }
}
