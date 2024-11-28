import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CreditosBoundary implements Tela{

    @Override
    public Pane render() {
        BorderPane panePrincipal = new BorderPane();
        panePrincipal.setTop( new Label("Todos os direitos reservados") );
        panePrincipal.setCenter(new Label("Jefferson Dantas da Cunha"));

       return panePrincipal;
    }
}