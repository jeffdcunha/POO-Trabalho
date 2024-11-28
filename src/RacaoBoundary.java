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

public class RacaoBoundary implements Tela {
    private Label lblId = new Label("");
    private TextField txtMarca = new TextField();
    private TextField txtVariante = new TextField();
    private TextField txtSabpr = new TextField();
    private TextField txtQuantidade = new TextField();
    private TextField txtPeso = new TextField();
    
    private RacaoControl control = null;

    private TableView<Racao> tableView = new TableView<>();

    @Override
    public Pane render() {
        try { 
            control = new RacaoControl();
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
                new Alert(AlertType.ERROR, "Erro ao gravar a racao", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (PetshopException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por marca", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Marca: "), 0, 1);
        paneForm.add(txtMarca, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Sabor: "), 0, 2);
        paneForm.add(txtSabpr, 1, 2);
        paneForm.add(new Label("Variante: "), 0, 3);
        paneForm.add(txtVariante, 1, 3);
        paneForm.add(new Label("Quantidade: "), 0, 4);
        paneForm.add(txtQuantidade, 1, 4);
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
        TableColumn<Racao, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Racao, Long>("id") );

        TableColumn<Racao, String> col2 = new TableColumn<>("Marca");
        col2.setCellValueFactory( new PropertyValueFactory<Racao, String>("marca"));

        TableColumn<Racao, String> col3 = new TableColumn<>("Sabor");
        col3.setCellValueFactory( new PropertyValueFactory<Racao, String>("sabor"));

        TableColumn<Racao, String> col4 = new TableColumn<>("Variante");
        col4.setCellValueFactory( new PropertyValueFactory<Racao, String>("variante"));

        TableColumn<Racao, Integer> col5 = new TableColumn<>("Quantidade");
        col5.setCellValueFactory( new PropertyValueFactory<Racao, Integer>("quantidade"));

        TableColumn<Racao, Float> col6 = new TableColumn<>("Peso");
        col6.setCellValueFactory( new PropertyValueFactory<Racao, Float>("peso"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Marca: " + novo.getMarca());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Racao, Void>, TableCell<Racao, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Racao, Void> call(
                    TableColumn<Racao, Void> param) {
                    TableCell<Racao, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Racao racao = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( racao ); 
                                } catch (PetshopException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir a racao", ButtonType.OK).showAndWait();
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

        TableColumn<Racao, Void> col7 = new TableColumn<>("Ação");
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
        Bindings.bindBidirectional(control.marcaProperty(), txtMarca.textProperty());
        Bindings.bindBidirectional(control.saborProperty(), txtSabpr.textProperty());
        Bindings.bindBidirectional(control.varianteProperty(), txtVariante.textProperty());
        Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidadeProperty(), (StringConverter) integerConverter);
        Bindings.bindBidirectional(txtPeso.textProperty(), control.pesoProperty(), (StringConverter) floatConverter);
    }
}
