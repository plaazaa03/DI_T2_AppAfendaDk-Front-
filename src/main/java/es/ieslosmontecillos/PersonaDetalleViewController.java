package es.ieslosmontecillos;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;


public class PersonaDetalleViewController {

    public static final char CASADO='C';
    public static final char SOLTERO='S';
    public static final char VIUDO='V';


    @javafx.fxml.FXML
    private DatePicker datePickerFechaNacimiento;
    @javafx.fxml.FXML
    private TextField textFieldNombre;
    @javafx.fxml.FXML
    private TextField textFieldApellidos;
    @javafx.fxml.FXML
    private TextField textFieldTelefono;
    @javafx.fxml.FXML
    private TextField textFieldEmail;
    @javafx.fxml.FXML
    private TextField textFieldNumHijos;
    @javafx.fxml.FXML
    private TextField textFieldSalario;
    @javafx.fxml.FXML
    private CheckBox checkBoxJubilado;
    @javafx.fxml.FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @javafx.fxml.FXML
    private RadioButton radioButtonSoltero;
    @javafx.fxml.FXML
    private RadioButton radioButtonCasado;
    @javafx.fxml.FXML
    private RadioButton radioButtonViudo;
    @javafx.fxml.FXML
    private ImageView imageViewFoto;

    private Pane rootAgendaView;
    @javafx.fxml.FXML
    private Pane rootPersonaDetalleView;

    private TableView tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;

    //Métodos

    public void setTableViewPrevio(TableView tableViewPrevio){
        this.tableViewPrevio=tableViewPrevio;

    }

    public void setPersona(Persona persona, Boolean nuevaPersona){
        if (!nuevaPersona){
            this.persona= persona;
            if (persona.getProvincia() != null){
                comboBoxProvincia.setValue(persona.getProvincia());
            }
        } else {
            this.persona = new Persona();
        }
        this.nuevaPersona=nuevaPersona;


    }

    public void onActionButtonGuardar(ActionEvent actionEvent) {

        persona.setNombre(textFieldNombre.getText());
        persona.setApellidos(textFieldApellidos.getText());
        persona.setTelefono(textFieldTelefono.getText());
        persona.setEmail(textFieldEmail.getText());
        if (nuevaPersona){
            dataUtil.addPersona(persona);
        } else {
            dataUtil.actualizarPersona(persona);
        }


        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);

        int numFilaSeleccionada;
        if (nuevaPersona){
            tableViewPrevio.getItems().add(persona);
            numFilaSeleccionada = tableViewPrevio.getItems().size()- 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada=
                    tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada,persona);
        }
        TablePosition pos = new TablePosition(tableViewPrevio,
                numFilaSeleccionada,null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();

    }

    public void onActionButtonCancelar(ActionEvent actionEvent) {
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);

        int numFilaSeleccionada =
                tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio,
                numFilaSeleccionada,null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void mostrarDatos() {
        textFieldNombre.setText(persona.getNombre());
        textFieldApellidos.setText(persona.getApellidos());
        textFieldTelefono.setText(persona.getTelefono());
        textFieldEmail.setText(persona.getEmail());
        if (persona.getNumHijos() != null){
            textFieldNumHijos.setText(persona.getNumHijos().toString());
        }
        if (persona.getSalario() != null){
            textFieldSalario.setText(persona.getSalario().toString());
        }
        if (persona.getJubilado() != null && persona.getJubilado() == 1) {
            checkBoxJubilado.setSelected(true);
        } else {
            checkBoxJubilado.setSelected(false);
        }

        if (persona.getEstadoCivil() != null){
            switch(persona.getEstadoCivil().charAt(0)){
                case CASADO:
                    radioButtonCasado.setSelected(true);
                    break;
                case SOLTERO:
                    radioButtonSoltero.setSelected(true);
                    break;
                case VIUDO:
                    radioButtonViudo.setSelected(true);
                    break;
            }
        }


        comboBoxProvincia.setCellFactory(
                (ListView<Provincia> l)-> new ListCell<Provincia>(){
                    @Override
                    protected void updateItem(Provincia provincia, boolean empty){
                        super.updateItem(provincia, empty);
                        if (provincia == null || empty){
                            setText("");
                        } else {
                            setText(provincia.getCodigo()+"-"+provincia.getNombre());
                        }
                    }
                });
        comboBoxProvincia.setConverter(new StringConverter<Provincia>(){
            @Override
            public String toString(Provincia provincia){
                if (provincia == null){
                    return null;
                } else {
                    return provincia.getCodigo()+"-"+provincia.getNombre();
                }
            }
            @Override
            public Provincia fromString(String userId){
                return null;
            }
        });
    }
}