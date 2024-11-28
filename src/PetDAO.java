import java.util.List;

public interface PetDAO {
    void inserir( Pet c ) throws PetshopException;
    void atualizar( Pet c ) throws PetshopException;
    void remover( Pet c ) throws PetshopException;
    List<Pet> pesquisarPorNome( String nome ) throws PetshopException;
}
