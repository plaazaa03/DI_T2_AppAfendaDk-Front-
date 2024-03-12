package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioController {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProv;
    private ObservableList<Persona> olPers;
    private ObservableList<Usuario> olUsr;

    public View inicio;
    public TextField txtUsuario;
    public TextField txtpassword;
    public Button botonEnter;
    public Button buttonExit;


    private Pane rootMain = new Pane();
    private Pane getRootMain(){
        return rootMain;
    }
    // Probar el funcionamiento sin bd

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }
    public void setOlProv(ObservableList olProv) {
        this.olProv = olProv;
    }
    public void setOlPers(ObservableList olPers) {
        this.olPers = olPers;
    }
    public void setOlUsr(ObservableList olUsr){
        this.olUsr = olUsr;
    }

    //Al pulsar en el Boton Enter
    public void onActionButtonEnter(ActionEvent event) {
        String nombreUsuario = txtUsuario.getText();
        String contraseña = txtpassword.getText();

        // Verifica si las credenciales ingresadas son válidas
        if (validarCredenciales(nombreUsuario, contraseña)) {
            // Si las credenciales son correctas, cargamos la siguiente vista
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
                Pane rootAgendaView = fxmlLoader.load();
                rootMain.getChildren().add(rootAgendaView);
                AgendaViewController agendaViewController = fxmlLoader.getController();
                agendaViewController.setDataUtil(dataUtil);
                agendaViewController.setOlProvincias(olProv);
                agendaViewController.setOlPersonas(olPers);
                agendaViewController.cargarTodasPersonas();
                inicio.setVisible(false);
            } catch (IOException e) {
                System.out.println("IOException: " + e);
            }
        } else {
            // Mostrar error cuando no coincida el usuario ni la contraseña
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fallo al introducir Usuario o Contraseña");
            alert.setHeaderText(null);
            alert.setContentText("Compruebe su nombre de usuario y contraseña.");
            alert.showAndWait();
        }
    }

    // Validar que el usuario y contraseña son correctos
    private boolean validarCredenciales(String nombreUsuario, String contraseña) {
        olUsr = dataUtil.getOlUsuarios();
        for (Usuario usr : olUsr) {
            if (usr.getUsername().equals(nombreUsuario) && usr.getPassword().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }

    //Al pulsar en exit
    public void onActionButtonExit(ActionEvent event) {
    }
}
