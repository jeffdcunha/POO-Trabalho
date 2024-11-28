import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PetControl {
    private ObservableList<Pet> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty especie = new SimpleStringProperty("");
    private StringProperty raca = new SimpleStringProperty("");
    private IntegerProperty idade = new SimpleIntegerProperty(0);
    private FloatProperty peso = new SimpleFloatProperty(0);

    private PetDAO petDAO = null;


    public PetControl() throws PetshopException { 
        petDAO = new PetDAOImpl();
    }

    public Pet paraEntidade() { 
        Pet c = new Pet();
        c.setId( id.get() );
        c.setRaca( raca.get() );
        c.setEspecie(especie.get());
        c.setNome(nome.get());
        c.setIdade(idade.get());
        c.setPeso(peso.get());
        return c;
    }

    public void excluir(Pet c ) throws PetshopException { 
        petDAO.remover( c );
        pesquisarTodos();
    }

    public void limparTudo() { 
        id.set( 0 );
        nome.set("");
        raca.set("");
        especie.set("");
        idade.set(0);
        peso.set(0);
    }

    public void paraTela(Pet c) { 
        if (c != null) {
            id.set( c.getId() );
            nome.set(c.getNome());
            especie.set(c.getEspecie());
            raca.set(c.getRaca());
            idade.set(c.getIdade());
            peso.set(c.getPeso());
        }
    }


    public void gravar() throws PetshopException { 
        Pet c = paraEntidade();
        if (c.getId() == 0 ) { 
            this.contador += 1;
            c.setId( this.contador );
            petDAO.inserir( c );
        } else { 
            petDAO.atualizar( c );
        }
        pesquisarTodos();
    }

    public void pesquisar() throws PetshopException { 
        lista.clear();
        lista.addAll( petDAO.pesquisarPorNome(nome.get()) );
    }

    public void pesquisarTodos() throws PetshopException { 
        lista.clear();
        lista.addAll(petDAO.pesquisarPorNome(""));
    }

    public LongProperty idProperty() { 
        return this.id;
    }
    public StringProperty nomeProperty() { 
        return this.nome;
    }
    public StringProperty racaProperty() { 
        return this.raca;
    }
    public StringProperty especieProperty() { 
        return this.especie;
    }
    public IntegerProperty idadeProperty(){
        return this.idade;
    }
    public FloatProperty pesoProperty(){
        return this.peso;
    }
    public ObservableList<Pet> getLista() { 
        return this.lista;
    }
}
