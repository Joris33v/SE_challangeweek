import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import com.almasb.fxgl.entity.Entity;

public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1300);
        gameSettings.setHeight(900);
        gameSettings.setTitle("Test");
        gameSettings.setVersion("1.0");
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(400,400)
                .viewWithBBox("mario_recht_static.png")
                .scale(0.2, 0.2)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(200, 200)
                .viewWithBBox(new Circle(5, Color.RED))
                .with(new CollidableComponent(true))
                .type(EntityTypes.STAR)
                .buildAndAttach();

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
        });

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.STAR) {
            @Override
            protected void onCollision(Entity player, Entity star) {
                star.removeFromWorld();
            }
        });
    }

    public static void main(String [] args){
        launch(args);
    }
}
