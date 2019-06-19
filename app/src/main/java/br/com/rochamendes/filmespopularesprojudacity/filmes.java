package br.com.rochamendes.filmespopularesprojudacity;

public class filmes {
//    private  String idFilme;
    private  String nomeFilme;
    private  String nomeOriginal;
    private  String capaFilme;
    private  String idiomaFilme;
    private  String sinopseFilme;
    private  String dataFilme;
    private  float classificFilme;

    public filmes(//String iD,
                  String nomeFilme,
                  String nomeOriginal,
                  String capaFilme,
                  String idiomaFilme,
                  String sinopseFilme,
                  String dataFilme,
                  float classificFilme) {
//        this.idFilme = iD;
        this.nomeFilme = nomeFilme;
        this.nomeOriginal = nomeOriginal;
        this.capaFilme = capaFilme;
        this.idiomaFilme = idiomaFilme;
        this.sinopseFilme = sinopseFilme;
        this.dataFilme = dataFilme;
        this.classificFilme = classificFilme;

    }
//    public String getIdFilme(){
//        return idFilme;
//    }

    public String getNomeFilme(){
        return nomeFilme;
    }

    public String getNomeOriginal(){
        return nomeOriginal;
    }

    public String getCapaFilme(){
        return capaFilme;
    }

    public String getIdiomaFilme(){
        return idiomaFilme;
    }

    public String getSinopseFilme(){
        return sinopseFilme;
    }

    public String getDataFilme() {
        return dataFilme;
    }

    public float getClassificFilme(){
        return classificFilme;
    }

//    public void setIdFilme(String idFilme){
//        this.idFilme = idFilme;
//    }

    public void setNomeFilme(String nomeFilme){
        this.nomeFilme = nomeFilme;
    }

    public void setNomeOriginal(String nomeOriginal){
        this.nomeOriginal = nomeOriginal;
    }

    public void setCapaFilme(String capaFilme){
        this.capaFilme = capaFilme;
    }

    public void setIdiomaFilme(String idiomaFilme){
        this.idiomaFilme = idiomaFilme;
    }

    public void setSinopseFilme(String sinopseFilme){
        this.sinopseFilme = sinopseFilme;
    }

    public void setDataFilme(String dataFilme) {
        this.dataFilme = dataFilme;
    }

    public void setClassificFilme(float classificFilme){
        this.classificFilme = classificFilme;
    }

}
