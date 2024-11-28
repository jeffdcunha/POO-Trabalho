import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RacaoDAOImpl implements RacaoDAO { 
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3307/PetshopDB?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public RacaoDAOImpl() throws PetshopException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new PetshopException( e );
        }
    }

    @Override
    public void inserir(Racao c) throws PetshopException {
        try { 
            String sql = """
                    insert into racoes (marca, sabor, variante, quantidade, peso)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getMarca() );
            stm.setString(2, c.getSabor() );
            stm.setString(3, c.getVariante() );
            stm.setInt(4, c.getQuantidade());
            stm.setFloat(5, c.getPeso());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
    }

    @Override
    public void atualizar(Racao c) throws PetshopException {
        try { 
            String sql = """
                    UPDATE racoes SET marca=?, sabor=?,
                    variante=?, quantidade=?, peso=?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getMarca() );
            stm.setString(2, c.getSabor());
            stm.setString(3, c.getVariante());
            stm.setInt(4, c.getQuantidade());
            stm.setFloat(5, c.getPeso());
            stm.setLong(6, c.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
    }

    @Override
    public void remover(Racao c) throws PetshopException {
        try { 
            String sql = """
                    DELETE FROM racoes
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
    public List<Racao> pesquisarPorMarca(String marca) throws PetshopException {
        List<Racao> lista = new ArrayList<>();
        try { 
            String sql = """
                    SELECT * FROM racoes
                    WHERE marca LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + marca + "%");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) { 
                Racao racao = new Racao();
                racao.setId( rs.getLong("id")  );
                racao.setMarca( rs.getString("marca") );
                racao.setSabor( rs.getString("sabor") );
                racao.setVariante( rs.getString("variante") );
                racao.setQuantidade( rs.getInt("quantidade") );
                racao.setPeso(rs.getFloat("peso"));
                lista.add(racao);
            }
        } catch (SQLException err) {
            throw new PetshopException(err);
        }
        return lista;
    }
}
