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
import com.almasb.fxgl.physics.PhysicsComponent;

public class Game extends GameApplication {

    private Entity player;
    private Entity player2;
    private PhysicsComponent physics;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(500);
        gameSettings.setTitle("Test");
        gameSettings.setVersion("1.0");
    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(100,400)
                .viewWithBBox("goomba.png")
                .scale(0.2, 0.2)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        player2 = FXGL.entityBuilder()
                .at(200,400)
                .viewWithBBox("goomba.png")
                .scale(0.2, 0.2)
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER2)
                .buildAndAttach();
        FXGL.getGameScene().setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void initInput(){
        var transform = player.getTransformComponent();
        FXGL.onKey(KeyCode.W, () -> {
            if(transform.getY() > player.getTransformComponent().getY()){
                player.translateY(-1);
            }
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });

        FXGL.onKey(KeyCode.I, () -> {
            physics.setVelocityY(-400);
        });

        FXGL.onKey(KeyCode.J, () -> {
            player2.translateX(-5);
        });

        FXGL.onKey(KeyCode.L, () -> {
            player2.translateX(5);
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
