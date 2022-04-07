import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Locale;
import java.util.Stack;

public class MyMenu extends FXGLMenu {
    public MyMenu(MenuType type) {
        super(type);

        int screenWidth = 1280;
        int screenHeight = 720;

        Texture background = FXGL.texture("background/wallpaper.jpg");
        background.setFitHeight(getAppHeight());
        background.setFitWidth(getAppWidth());

        var sign = createActionTexture("mainMenu/wood_sign", 900, 350, 400, 350, "sign");
        var textSign = createTextView("Super Mario", 1030, 430, 25, Color.web("#79470f"));
        var textGroep = createTextView("Groep Nr 6", 1050, 540, 20, Color.web("#79470f"));
        var startButton = createActionTexture("mainMenu/button", 65, 300, 300, 80, "start");
        var startText = createTextView("Start", 150, 315, 30, Color.web("#79470f"));
        var optionButton = createActionTexture("mainMenu/button", 65, 400, 300, 80, "option");
        var optionText = createTextView("Option", 150, 415, 30, Color.web("#79470f"));
        var exitButton = createActionTexture("mainMenu/button", 65, 500, 300, 80, "exit");
        var exitText = createTextView("Exit", 160, 515, 30, Color.web("#79470f"));

        var usernameText = createTextView("Gebruikersnaam:", 390, 175, 30, Color.web("#af5e24"));
        var usernameEditText = createEditText(385,240,500,80,26);
        var passwordText = createTextView("Wachtwoord:", 390, 340, 30, Color.web("#af5e24"));
        var passwordEditText = createEditText(385,405,500,80,26);
        var loginButton = createActionTexture("mainMenu/loginButton",485,550,350,80, "login");
        VBox loginScherm = new VBox();
        loginScherm.setTranslateX(0);
        loginScherm.setTranslateY(0);
        loginScherm.getChildren().addAll(background, usernameText,usernameEditText,passwordText,passwordEditText, loginButton);
        VBox mainMenu = new VBox();
        mainMenu.setTranslateX(0);
        mainMenu.setTranslateY(0);
        mainMenu.getChildren().addAll(background, sign, textSign, textGroep, startButton, startText, optionButton, optionText, exitButton, exitText);
        loginButton = createActionTexture("mainMenu/loginButton",485,550,350,80, "login", usernameEditText.getText(),passwordEditText.getText(), loginScherm,mainMenu);
        getContentRoot().getChildren().addAll(mainMenu);

        //getContentRoot().getChildren().addAll(background, usernameText,usernameEditText,passwordText,passwordEditText, loginButton);
//        getContentRoot().getChildren().addAll(background, sign, textSign, textGroep, startButton, startText, optionButton, optionText, exitButton, exitText);


    }

    protected final Texture createActionTexture(String url, int x, int y, int w, int h, String id) {
        Texture texture = FXGL.texture(url+".png");
        texture.setId(id);
        texture.setTranslateX(x);
        texture.setTranslateY(y);
        texture.setFitWidth(w);
        texture.setFitHeight(h);
        texture.setOnMouseClicked(e -> buttonClicked(texture));
        texture.setOnMouseEntered(e -> buttonHoverTrue(texture, x, y, w, h, url+"_effected.png"));
        texture.setOnMouseExited(e -> buttonHoverFalse(texture, x, y, w, h, url+".png"));
        return texture;
    }

    protected final Texture createActionTexture(String url, int x, int y, int w, int h, String id, String username, String password, VBox login, VBox menu) {
        Texture texture = FXGL.texture(url+".png");
        texture.setId(id);
        texture.setTranslateX(x);
        texture.setTranslateY(y);
        texture.setFitWidth(w);
        texture.setFitHeight(h);
        texture.setOnMouseClicked(e -> buttonClicked(texture, username, password,login,menu));
        texture.setOnMouseEntered(e -> buttonHoverTrue(texture, x, y, w, h, url+"_effected.png"));
        texture.setOnMouseExited(e -> buttonHoverFalse(texture, x, y, w, h, url+".png"));
        return texture;
    }

    public void buttonClicked(Texture texture){
        if (texture.getId().equals("start")){
            fireNewGame();
        }else if (texture.getId().equals("option")){

        }else if (texture.getId().equals("exit")){
            fireExit();
        }
    }


    public void buttonClicked(Texture texture, String username, String password, VBox login, VBox menu){
        if (texture.getId().equals("start")){
            fireNewGame();
        }else if (texture.getId().equals("option")){

        }else if (texture.getId().equals("exit")){
            fireExit();
        }else if (texture.getId().equals("login")){
            if (username.toLowerCase(Locale.ROOT).equals("ipose") && password.toLowerCase(Locale.ROOT).equals("groep6")){
                login.setVisible(false);
                menu.setVisible(true);
            }else {

            }
        }
    }


    public void buttonHoverTrue(Texture texture, int x, int y, int w, int h, String url) {
        texture.set(FXGL.texture(url));
        texture.setTranslateX(x);
        texture.setTranslateY(y);
        texture.setFitWidth(w + 2);
        texture.setFitHeight(h + 2);
    }


    public void buttonHoverFalse(Texture texture, int x, int y, int w, int h, String url) {
        texture.set(FXGL.texture(url));
        texture.setTranslateX(x);
        texture.setTranslateY(y);
        texture.setFitWidth(w);
        texture.setFitHeight(h);
    }

    protected final Label createTextView(String txt, int x, int y, int fontSize, Color color) {
        Label myText = new Label(txt);
        myText.setTranslateX(x);
        myText.setTranslateY(y);
        myText.setTextFill(color);
        myText.setFont(Font.loadFont(getClass().getResourceAsStream("RubikWetPaint-Regular.ttf"), fontSize));
        myText.setOnMouseEntered(e -> textHoverTrue(myText));
        myText.setOnMouseExited(e -> textHoverFalse(myText));
        myText.setId("text");
        return myText;
    }

    public void textHoverTrue(Label mytext) {
        mytext.setTextFill(Color.web("#ffda45"));
    }

    public void textHoverFalse(Label mytext) {
        mytext.setTextFill(Color.web("#af5e24"));
    }


    protected final TextField createEditText(int x, int y, int w, int h, int fontSize) {
        TextField textField = new TextField();
        textField.setTranslateX(x);
        textField.setTranslateY(y);
        textField.setMinWidth(w);
        textField.setMinHeight(h);
        textField.setMaxWidth(w);
        textField.setMaxHeight(h);
        textField.setFont(Font.loadFont(getClass().getResourceAsStream("RubikWetPaint-Regular.ttf"), fontSize));
        textField.setAlignment(Pos.CENTER);
        return textField;
    }
}

