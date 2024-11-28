public class Racao {
    private long id;
    private String marca;
    private String sabor;
    private String variante;
    private int quantidade;
    private float peso;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getMarca(){
        return this.marca;
    }

    public void setSabor(String sabor){
        this.sabor = sabor;
    }

    public String getSabor(){
        return this.sabor;
    }
    
    public void setVariante(String variante){
        this.variante = variante;
    }

    public String getVariante(){
        return this.variante;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public void setPeso(float peso){
        this.peso = peso;
    }
    
    public float getPeso(){
        return this.peso;
    }

}
