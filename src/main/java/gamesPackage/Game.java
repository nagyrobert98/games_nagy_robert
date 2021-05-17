package gamesPackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Game {
    public StringProperty cim = new SimpleStringProperty();
    public IntegerProperty ev = new SimpleIntegerProperty();
    public StringProperty kiado = new SimpleStringProperty();
    public StringProperty mufaj = new SimpleStringProperty();
    public ObjectProperty<Platform[]> platform = new SimpleObjectProperty<>();

    public static ObservableList<Game> Games = FXCollections.observableArrayList(new ArrayList<>());

    public static final String DEFAULTPATH = "/games.fxml";

    public static void load(String path){
        try {
            File file = new File(path);

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();
            root.normalize();
            NodeList nodes = root.getElementsByTagName("game");

            for(int i = 0; i < nodes.getLength();i++){
                Node node = nodes.item(i);
                Game game = new Game();
                game.cim.set(node.getAttributes().getNamedItem("cim").getNodeValue());
                game.ev.set(Integer.parseInt(node.getAttributes().getNamedItem("ev").getNodeValue()));
                game.kiado.set(node.getAttributes().getNamedItem("kiado").getNodeValue());
                game.mufaj.set(node.getAttributes().getNamedItem("mufaj").getNodeValue());
                game.platform.set(Arrays.stream(node.getAttributes()
                        .getNamedItem("platform").getNodeValue().split(","))
                        .map(Platform::valueOf).toArray(Platform[]::new));

                Games.add(game);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static void save (String path) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element root = document.createElement("gamesPackage");
            document.appendChild(root);

            for (Game game : Games) {
                Element node = document.createElement("game");

                node.setAttribute("cim", game.cim.get());
                node.setAttribute("ev", Integer.toString(game.ev.get()));
                node.setAttribute("kiado", game.kiado.get());
                node.setAttribute("mufaj", game.mufaj.get());
                node.setAttribute("platform", Arrays.stream(game.platform.get())
                        .map(Platform::toString).collect(Collectors.joining(",")));

                root.appendChild(node);
            }

            File file = new File(path);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    static public void add(String cim, int ev,  String kiado, String mufaj, Platform[] platform) {
        Game game = new Game();

        game.cim.set(cim);
        game.ev.set(ev);
        game.kiado.set(kiado);
        game.mufaj.set(mufaj);
        game.platform.set(platform);

        Game.Games.add(game);
    }

}
