import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PetBoundary implements Tela{
    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtEspecie = new TextField();
    private TextField txtRaca = new TextField();
    private TextField txtIdade = new TextField();
    private TextField txtPeso = new TextField();
    
    private PetControl control = null;

    private TableView<Pet> tableView = new TableView<>();

    @Override
    public Pane render() {
        try { 
            control = new PetControl();
        } catch (PetshopException e ) { 
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction( e -> { 
            try { 
                control.gravar();
            } catch (PetshopException err) { 
                new Alert(AlertType.ERROR, "Erro ao gravar pet", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (PetshopException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Especie: "), 0, 2);
        paneForm.add(txtEspecie, 1, 2);
        paneForm.add(new Label("Raca: "), 0, 3);
        paneForm.add(txtRaca, 1, 3);
        paneForm.add(new Label("Idade: "), 0, 4);
        paneForm.add(txtIdade, 1, 4);
        paneForm.add(new Label("Peso: "), 0, 5);
        paneForm.add(txtPeso, 1, 5);
        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

       return panePrincipal;
    }

    public void gerarColunas() { 
        TableColumn<Pet, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Pet, Long>("id") );

        TableColumn<Pet, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Pet, String>("nome"));

        TableColumn<Pet, String> col3 = new TableColumn<>("Especie");
        col3.setCellValueFactory( new PropertyValueFactory<Pet, String>("especie"));

        TableColumn<Pet, String> col4 = new TableColumn<>("Raca");
        col4.setCellValueFactory( new PropertyValueFactory<Pet, String>("Raca"));

        TableColumn<Pet, Integer> col5 = new TableColumn<>("Idade");
        col5.setCellValueFactory( new PropertyValueFactory<Pet, Integer>("idade"));

        TableColumn<Pet, Float> col6 = new TableColumn<>("Peso");
        col6.setCellValueFactory( new PropertyValueFactory<Pet, Float>("peso"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Nome: " + novo.getNome());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Pet, Void>, TableCell<Pet, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Pet, Void> call(
                    TableColumn<Pet, Void> param) {
                    TableCell<Pet, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Pet pet = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( pet ); 
                                } catch (PetshopException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir pet", ButtonType.OK).showAndWait();
                                }
                            });
                        }

                        @Override
                        public void updateItem( Void item, boolean empty) {                             
                            if (!empty) { 
                                setGraphic(btnApagar);
                            } else { 
                                setGraphic(null);
                            }
                        }
                        
                    };
                    return celula;            
                } 
            };

        TableColumn<Pet, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems( control.getLista() );
    }

    public void ligacoes() { 
        control.idProperty().addListener( (obs, antigo, novo) -> {
            lblId.setText( String.valueOf(novo) );
        });

        IntegerStringConverter integerConverter = new IntegerStringConverter();
        FloatStringConverter floatConverter = new FloatStringConverter();
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.especieProperty(), txtEspecie.textProperty());
        Bindings.bindBidirectional(control.racaProperty(), txtRaca.textProperty());
        Bindings.bindBidirectional(txtIdade.textProperty(), control.idadeProperty(), (StringConverter) integerConverter);
        Bindings.bindBidirectional(txtPeso.textProperty(), control.pesoProperty(), (StringConverter) floatConverter);
    }
}
