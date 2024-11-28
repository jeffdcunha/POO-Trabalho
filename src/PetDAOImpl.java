import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3307/PetshopDB?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public PetDAOImpl() throws PetshopException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new PetshopException( e );
        }
    }

    @Override
    public void inserir(Pet c) throws PetshopException {
        try { 
            String sql = """
                    insert into pets (nome, especie, raca, idade, peso)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getNome() );
            stm.setString(2, c.getEspecie() );
            stm.setString(3, c.getRaca() );
            stm.setInt(4, c.getIdade());
            stm.setFloat(5, c.getPeso());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
    }

    @Override
    public void atualizar(Pet c) throws PetshopException {
        try { 
            String sql = """
                    UPDATE pets SET nome=?, especie=?,
                    raca=?, idade=?, peso=?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getNome() );
            stm.setString(2, c.getEspecie());
            stm.setString(3, c.getRaca());
            stm.setInt(4, c.getIdade());
            stm.setFloat(5, c.getPeso());
            stm.setLong(6, c.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
    }

    @Override
    public void remover(Pet c) throws PetshopException {
        try { 
            String sql = """
                    DELETE FROM pets
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, c.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
    }

    @Override
    public List<Pet> pesquisarPorNome(String nome) throws PetshopException {
        List<Pet> lista = new ArrayList<>();
        try { 
            String sql = """
                    SELECT * FROM pets
                    WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) { 
                Pet pet = new Pet();
                pet.setId( rs.getLong("id")  );
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                pet.setPeso(rs.getFloat("peso"));
                lista.add(pet);
            }
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
        return lista;
    }
}
