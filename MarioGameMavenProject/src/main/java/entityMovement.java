import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;

public class entityMovement{
    private PhysicsComponent physics;

    public void jump(Entity entity){
        physics.setVelocityY(-400);
    }

}
