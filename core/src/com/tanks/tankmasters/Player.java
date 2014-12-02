package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player {

    float tankDir = 0; // Rotation of the tank body
    Vector2 pos;
    Vector2 size = new Vector2(3f, 2.5f);
    private float density = 0.75f;
    float forward = 0.0f; // (backward) -1.0f <<>> 1.0f (forward)
    float turning = 0.0f; // Same as for 'forward'
    float maxVel = 100f;
    float maxRev = -50f;
    float maxRot = 5f;
    float turnSpeed = 2f; //turnspeed of the tank
    float moveSpeed = 5000f;

    private ShapeRenderer renderer;
    private Body b;

    public Player(int posX, int posY){
        pos = new Vector2(posX,posY);
        renderer = new ShapeRenderer();

        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(pos.x,pos.y);

        b = Main.world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x/2,size.y/2);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = density;
        fDef.friction = 0.7f;
        fDef.restitution = 0.2f;

        b.createFixture(fDef);

        b.setAngularDamping(40f);
        b.setLinearDamping(30f);

        shape.dispose();
        Gdx.app.log("App", "Player mass: " + b.getMass());
    }

    public void update(){
        pos.x = b.getPosition().x + Main.WORLDSIZE_WIDTH/2 -size.x/2;
        pos.y = b.getPosition().y + Main.WORLDSIZE_HEIGHT/2 -size.y/2;
        tankDir = (float) Math.toDegrees(b.getAngle());

        if(b.getAngularVelocity() < maxRot && b.getAngularVelocity() > -maxRot)
            b.applyAngularImpulse(-turning * turnSpeed,true);

        float xForce = (float) (forward*Math.cos(Math.toRadians(tankDir)));
        float yForce = (float) (forward*Math.sin(Math.toRadians(tankDir)));

        if(b.getLinearVelocity().len() < maxVel && b.getLinearVelocity().len() > maxRev){
            b.applyForceToCenter(new Vector2(xForce * moveSpeed, yForce * moveSpeed),true);
        }

    }

    public void render(OrthographicCamera cam,float delta){
        renderer.setProjectionMatrix(cam.projection);
        renderer.translate(pos.x,pos.y,0);
        renderer.rotate(0,0,1,tankDir);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(0, 0, size.x, size.y);
        renderer.end();
    }

    public void moveForward(){
        forward = 1f;
    }
    public void moveBackward(){
        forward = -1f;
    }

    public void turnLeft(){
        turning = -1f;
    }
    public void turnRight(){
        turning = 1f;
    }

    public void resetTurn(){
        turning = 0f;
    }
    public void resetMove(){
        forward = 0f;
    }

    public void dispose(){
        renderer.dispose();
    }

}
