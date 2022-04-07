import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Super Mario");
        settings.setVersion("1.0");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new MySceneFactory());
    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(400, 400)
                .viewWithBBox("mario_recht_static.png")
                .with(new CollidableComponent(true))
                .scale(0.4,0.4)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        FXGL.getGameTimer().runAtInterval(() ->{
            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
            FXGL.entityBuilder()
                    .at(randomPosX, randomPosY)
                    .viewWithBBox(new Circle(5, Color.WHITE))
                    .with(new CollidableComponent(true))
                    .type(EntityTypes.MONSTER)
                    .buildAndAttach();

        }, Duration.millis(2000));
    }



    @Override
    protected void initInput(){

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
        });
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER,EntityTypes.MONSTER) {
            @Override
            protected void onCollision(Entity player, Entity monster) {
                FXGL.inc("kills", +1);
                monster.removeFromWorld();
            }
        });
    }




    @Override
    protected void initUI(){
        Label myText = new Label("Hallo There");
        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(50);
        myText.setTranslateY(50);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameScene().addUINode(myText);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
