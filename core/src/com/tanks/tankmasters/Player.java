package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player {

    float tankDir = 0; // Rotation of the tank body
    double towerAngle = 0;
    Vector2 pos;
    Vector2 size = new Vector2(3f, 2.5f);
    private float density = 0.75f;
    float forward = 0.0f; // (backward) -1.0f <<>> 1.0f (forward)
    float turning = 0.0f; // Same as for 'forward'

    float turnSpeed = 5f; //turnspeed of the tank
    float moveSpeed = 10000f;

    private ShapeRenderer renderer;
    private Body b;

    //TMP
    Vector2 scrnPos;
    //

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
        fDef.friction = 0.5f;
        fDef.restitution = 0.2f;

        b.createFixture(fDef);

        b.setAngularDamping(45f);
        b.setLinearDamping(35f);

        shape.dispose();
        Gdx.app.log("App", "Player mass: " + b.getMass()+"kg");
    }

    public void update(){
        pos.x = b.getPosition().x + Main.WORLDSIZE_WIDTH/2 -size.x/2;
        pos.y = b.getPosition().y + Main.WORLDSIZE_HEIGHT/2 -size.y/2;
        tankDir = (float) Math.toDegrees(b.getAngle());

        b.applyAngularImpulse(-turning * turnSpeed,true);

        float xForce = (float) (forward*Math.cos(Math.toRadians(tankDir)));
        float yForce = (float) (forward*Math.sin(Math.toRadians(tankDir)));

        b.applyForceToCenter(new Vector2(xForce * moveSpeed, yForce * moveSpeed),true);


    }

    public void render(OrthographicCamera cam,float delta){
        renderer.setProjectionMatrix(cam.projection);
        renderer.identity();
        renderer.translate(-Main.WORLDSIZE_WIDTH/2,-Main.WORLDSIZE_HEIGHT/2,0);
        renderer.translate(pos.x+size.x/2,pos.y+size.y/2,0);
        renderer.rotate(0,0,1,tankDir);

        renderer.setColor(0,1,0,1);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(-size.x / 2, -size.y / 2, size.x, size.y);

        //Turret
        float pX = (float)Math.cos(towerAngle)*2.2f;
        float pY = (float)Math.sin(towerAngle)*2.2f;
        renderer.line(0,0,pX,pY);

        //
        renderer.setColor(.5f,.5f,0,1);
        renderer.point(0,0,0);

        //Center check
        renderer.identity();
        renderer.setColor(1,0,0,1);
        renderer.translate(-Main.WORLDSIZE_WIDTH/2,-Main.WORLDSIZE_HEIGHT/2,0); // Reset origo to left bottom
        renderer.point(scrnPos.x*Main.WORLDSIZE_WIDTH,scrnPos.y*Main.WORLDSIZE_HEIGHT,0);

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

    // X and Y positions 0f <<>> 1.0f
    public void updateTurret(double mouseX,double mouseY){

        // Screen position of tank, scale 0f-1f
        scrnPos = new Vector2( (pos.x-Main.WORLDSIZE_WIDTH/2)/Main.WORLDSIZE_WIDTH,
                                        (pos.y-Main.WORLDSIZE_HEIGHT/2)/Main.WORLDSIZE_HEIGHT );
        double sizeFracX = size.x / Main.WORLDSIZE_WIDTH;
        double sizeFracY = size.y / Main.WORLDSIZE_WIDTH;

        scrnPos = new Vector2((float)(scrnPos.x+.5f+sizeFracX/2f),(float)(scrnPos.y+.5f+sizeFracY/2f));

        double diffX = mouseX-scrnPos.x;
        double diffY = mouseY-scrnPos.y;

        towerAngle = Math.atan2(diffX , diffY) - MathUtils.PI/2f;

        if(towerAngle < 0)
            towerAngle += MathUtils.PI2;


        Gdx.app.log("App","Towe angle:"+towerAngle);
    }

    public void dispose(){
        renderer.dispose();
    }

}
