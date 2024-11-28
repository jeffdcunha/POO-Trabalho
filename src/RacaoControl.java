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

public class RacaoControl {
    private ObservableList<Racao> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty marca = new SimpleStringProperty("");
    private StringProperty sabor = new SimpleStringProperty("");
    private StringProperty variante = new SimpleStringProperty("");
    private IntegerProperty quantidade = new SimpleIntegerProperty(0);
    private FloatProperty peso = new SimpleFloatProperty(0);

    private RacaoDAO racaoDAO = null;


    public RacaoControl() throws PetshopException { 
        racaoDAO = new RacaoDAOImpl();
    }

    public Racao paraEntidade() { 
        Racao c = new Racao();
        c.setId( id.get() );
        c.setMarca( marca.get() );
        c.setSabor(sabor.get());
        c.setVariante(variante.get());
        c.setQuantidade(quantidade.get());
        c.setPeso(peso.get());
        return c;
    }

    public void excluir(Racao c ) throws PetshopException { 
        racaoDAO.remover( c );
        pesquisarTodos();
    }

    public void limparTudo() { 
        id.set( 0 );
        marca.set("");
        sabor.set("");
        variante.set("");
        quantidade.set(0);
        peso.set(0);
    }

    public void paraTela(Racao c) { 
        if (c != null) {
            id.set( c.getId() );
            marca.set(c.getMarca());
            sabor.set(c.getSabor());
            variante.set(c.getVariante());
            quantidade.set(c.getQuantidade());
            peso.set(c.getPeso());
        }
    }


    public void gravar() throws PetshopException { 
        Racao c = paraEntidade();
        if (c.getId() == 0 ) { 
            this.contador += 1;
            c.setId( this.contador );
            racaoDAO.inserir( c );
        } else { 
            racaoDAO.atualizar( c );
        }
        pesquisarTodos();
    }

    public void pesquisar() throws PetshopException { 
        lista.clear();
        lista.addAll( racaoDAO.pesquisarPorMarca(marca.get()) );
    }

    public void pesquisarTodos() throws PetshopException { 
        lista.clear();
        lista.addAll(racaoDAO.pesquisarPorMarca(""));
    }

    public LongProperty idProperty() { 
        return this.id;
    }
    public StringProperty marcaProperty() { 
        return this.marca;
    }
    public StringProperty saborProperty() { 
        return this.sabor;
    }
    public StringProperty varianteProperty() { 
        return this.variante;
    }
    public IntegerProperty quantidadeProperty(){
        return this.quantidade;
    }
    public FloatProperty pesoProperty(){
        return this.peso;
    }
    public ObservableList<Racao> getLista() { 
        return this.lista;
    }
}
