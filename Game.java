import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.handlers.CollectibleHandler;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import com.almasb.fxgl.entity.Entity;

public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Test");
        gameSettings.setVersion("1.0");
    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(600,300)
                .view(new Rectangle(15, 15, Color.BLUE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(800, 400)
                .viewWithBBox(new Circle(5, Color.RED))
                .with(new CollidableComponent(true))
                .type(EntityTypes.STAR)
                .buildAndAttach();

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-1);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-1);
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(1);
        });

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(1);
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
