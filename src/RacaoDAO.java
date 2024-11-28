import java.util.List;

public interface RacaoDAO {
    void inserir(Racao c) throws PetshopException;
    void atualizar(Racao c) throws PetshopException;
    void remover(Racao c) throws PetshopException;
    List<Racao> pesquisarPorMarca(String marca) throws PetshopException;
}
