import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {
    private Map<String, Tela> telas = new HashMap<>();
    @Override
    public void start(Stage stage) throws Exception {
        telas.put("pet", new PetBoundary());
        telas.put("racao", new RacaoBoundary());
        telas.put("creditos", new CreditosBoundary());
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        Menu mnuAjuda = new Menu("Ajuda");
        MenuItem mnuItemPet = new MenuItem("Pet");
        mnuItemPet.setOnAction ( e -> 
            panePrincipal.setCenter( telas.get("pet").render() )
        );
        MenuItem mnuItemRacao = new MenuItem("Racao");
        mnuItemRacao.setOnAction( e-> 
            panePrincipal.setCenter( telas.get("racao").render() )
        );
        MenuItem mnuItemCreditos = new MenuItem("Creditos");
        mnuItemCreditos.setOnAction( e-> 
            panePrincipal.setCenter( telas.get("creditos").render() )
        );
        mnuCadastro.getItems().addAll( mnuItemPet );
        mnuCadastro.getItems().addAll( mnuItemRacao );
        mnuAjuda.getItems().add( mnuItemCreditos );
        menuBar.getMenus().addAll( mnuCadastro, mnuAjuda);
        panePrincipal.setTop( menuBar );
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Gerenciador Petshop");
        stage.show();        
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }
}
