public class Pet {
    private long id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private float peso;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setEspecie(String especie){
        this.especie = especie;
    }

    public String getEspecie(){
        return this.especie;
    }
    
    public void setRaca(String raca){
        this.raca = raca;
    }

    public String getRaca(){
        return this.raca;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public int getIdade(){
        return idade;
    }

    public void setPeso(float peso){
        this.peso = peso;
    }
    
    public float getPeso(){
        return this.peso;
    }

}
