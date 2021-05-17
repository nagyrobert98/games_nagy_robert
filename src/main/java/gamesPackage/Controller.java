package gamesPackage;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    public TextField keresesMezo;
    public ComboBox<String> comboBoxSearch;

    public CheckBox checkBoxSearch1;
    public CheckBox checkBoxSearch2;
    public CheckBox checkBoxSearch3;
    public CheckBox checkBoxSearch4;
    public CheckBox checkBoxSearch5;

    public TableColumn<Game,String> cimMezo;
    public TableColumn<Game, Integer> evMezo;
    public TableColumn<Game, String> kiadoMezo;
    public TableColumn<Game, String> mufajMezo;
    public TableColumn<Game, String> platformMezo;

    public TextField cimHozzaad;
    public TextField evHozzaad;
    public TextField kiadoHozzaad;

    public ComboBox<String> hozzaadLista;
    public CheckBox checkBoxAdd1;
    public CheckBox checkBoxAdd2;
    public CheckBox checkBoxAdd3;
    public CheckBox checkBoxAdd4;
    public CheckBox checkBoxAdd5;
    public TableView<Game> table;
    public VBox vbox1;
    public Button addButton;

    Map<CheckBox, Platform> checkBoxPlatformMap = new HashMap<>();
    ArrayList<Platform> checkBoxPlatformArray = new ArrayList<>();

    @FXML
    public void initialize(){

        ObservableList<String> temp = FXCollections
                .observableArrayList(Arrays.asList(   "Akció-kaland", "Arcade","Autóversenyzős", "Battle Royale","FPS","Horror",
                        "MMORPG","Oldalnézetes","Open World","Platform","RPG", "Szimulátor", "Stratégiai",
                        "Szerep","TPS", "Városépítő", "Verekedős"));

        comboBoxSearch.setItems(temp);
        hozzaadLista.setItems(temp);

        table.setItems(Game.Games);
        cimMezo.setCellValueFactory(data -> data.getValue().cim);
        evMezo.setCellValueFactory(data -> data.getValue().ev.asObject());
        kiadoMezo.setCellValueFactory(data -> data.getValue().kiado);
        mufajMezo.setCellValueFactory(data -> data.getValue().mufaj);
        platformMezo.setCellValueFactory(data ->
                Bindings.createStringBinding(() -> Arrays.stream(data.getValue().platform.get())
                .map(Platform::toString).collect(Collectors.joining(", ")), data.getValue().platform));

        keresesMezo.textProperty().addListener((observable, oldValue, newValue) -> keresesInditas());

        checkBoxPlatformMap.put(checkBoxSearch1, Platform.PC);
        checkBoxPlatformMap.put(checkBoxSearch2, Platform.XBOX);
        checkBoxPlatformMap.put(checkBoxSearch3, Platform.PlayStation);
        checkBoxPlatformMap.put(checkBoxSearch4, Platform.Switch);
        checkBoxPlatformMap.put(checkBoxSearch5, Platform.Mobile);
    }

    public void feltetelekTorlese(ActionEvent actionEvent) {
        keresesMezo.clear();
        comboBoxSearch.getSelectionModel().clearSelection();
        checkBoxSearch1.setSelected(false);
        checkBoxSearch2.setSelected(false);
        checkBoxSearch3.setSelected(false);
        checkBoxSearch4.setSelected(false);
        checkBoxSearch5.setSelected(false);
        table.setItems(Game.Games);

    }

    public void jatekFelvetel(ActionEvent actionEvent) {
        vbox1.setVisible(true);
        addButton.setVisible(true);
    }

    public void jatekVeglegesites(ActionEvent actionEvent) {

        ArrayList<Platform> platform = new ArrayList<>();

        if(checkBoxAdd1.isSelected()){
            platform.add(Platform.PC);
        }
        if(checkBoxAdd2.isSelected()){
            platform.add(Platform.XBOX);
        }
        if(checkBoxAdd3.isSelected()){
            platform.add(Platform.PlayStation);
        }
        if(checkBoxAdd4.isSelected()){
            platform.add(Platform.Switch);
        }
        if(checkBoxAdd5.isSelected()){
            platform.add(Platform.Mobile);
        }

        Game.add(cimHozzaad.getText(), Integer.parseInt(evHozzaad.getText()), kiadoHozzaad.getText(),
                hozzaadLista.getSelectionModel().getSelectedItem(), platform.toArray(new Platform[] {}));

        cimHozzaad.clear();
        evHozzaad.clear();
        kiadoHozzaad.clear();
        hozzaadLista.getSelectionModel().clearSelection();
        checkBoxAdd1.setSelected(false);
        checkBoxAdd2.setSelected(false);
        checkBoxAdd3.setSelected(false);
        checkBoxAdd4.setSelected(false);
        checkBoxAdd5.setSelected(false);
    }

    public void keresesInditas() {

        FilteredList<Game> filteredGames = new FilteredList<>(Game.Games, game ->
                (game.cim.get().contains(keresesMezo.getText())
                        || Integer.toString(game.ev.get()).contains(keresesMezo.getText()))
                        && (comboBoxSearch.getSelectionModel().getSelectedItem() == null ||
                        game.mufaj.get().equals(comboBoxSearch.getSelectionModel().getSelectedItem()))
                        && (checkBoxPlatformArray.isEmpty() ||
                        metszet(game.platform.get(), checkBoxPlatformArray)));
        table.setItems(filteredGames);
    }

    public boolean metszet(Platform[] tomb, ArrayList<Platform> lista){
        for (Platform platform : tomb) {
            if (lista.contains(platform))
                return true;
        }
        return false;
    }

    public void kezelo1(ActionEvent actionEvent) {
        CheckBox v1 = (CheckBox) actionEvent.getSource();

        if(v1.isSelected())
            checkBoxPlatformArray.add(checkBoxPlatformMap.get(v1));
        else
            checkBoxPlatformArray.remove(checkBoxPlatformMap.get(v1));

        keresesInditas();
    }

    public void kezelo2(ActionEvent actionEvent) {
        keresesInditas();
    }

    public void torlesInditas(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() != null){

            Game.Games.remove(table.getSelectionModel().getSelectedItem());
        }

    }
}
