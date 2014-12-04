package com.tanks.tankmasters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Player {

    float tankDir = 0; // Rotation of the tank body
    Vector2 pos;
    Vector2 size = new Vector2(3f, 2.5f);
    Texture tankTex;
    private float density = 0.75f;
    float forward = 0.0f; // (backward) -1.0f <<>> 1.0f (forward)
    float turning = 0.0f; // Same as for 'forward'

    float turnSpeed = 50f; //turnspeed of the tank
    float moveSpeed = 30000f;

    //Tower/turret vars
    double towerAngle = 0;
    double mouseX = .5;
    double mouseY = .5;
    Texture towerTex;

    private Body b;

    //Bullets
    private ArrayList<Body> bulletBList = new ArrayList<Body>();
    private int bulletDmg = 40;
    private float bulletSpd = 40;
    private float bulletSize = .2f;
    Texture bTex;


    //Location of player on screen in unit coords
    Vector2 scrnPos = new Vector2(0,0);

    public Player(int posX, int posY){

        bTex = new Texture("Bullet.png");
        tankTex = new Texture("Tank.png");
        towerTex = new Texture("Tank_Tower.png");

        pos = new Vector2(posX,posY);

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

        b.setAngularDamping(7f);
        b.setLinearDamping(13f);

        shape.dispose();
        Gdx.app.log("App", "Player mass: " + b.getMass()+"kg");
    }

    public void fire(){
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.KinematicBody;
        //Add a bit of space between player and bullet to prevent self damage
        bDef.position.set(pos.x -Main.WORLDSIZE_WIDTH/2 +(float)(2.2f*Math.cos(towerAngle)) +size.x/2,
                pos.y -Main.WORLDSIZE_HEIGHT/2 +(float)(2.2f*Math.sin(towerAngle)) +size.y/2);

        Body tmp = Main.world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bulletSize,bulletSize);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;

        tmp.createFixture(fDef);

        tmp.setBullet(true);
        tmp.setLinearVelocity((float)(bulletSpd*Math.cos(towerAngle)), (float)(bulletSpd*Math.sin(towerAngle)) );

        bulletBList.add(tmp);
        shape.dispose();
    }

    public void update(float delta){
        pos.x = b.getPosition().x + Main.WORLDSIZE_WIDTH/2 -size.x/2;
        pos.y = b.getPosition().y + Main.WORLDSIZE_HEIGHT/2 -size.y/2;
        tankDir = (float) Math.toDegrees(b.getAngle());

        b.applyAngularImpulse(-turning * turnSpeed * delta,true);

        float xForce = (float) (forward*Math.cos(Math.toRadians(tankDir)));
        float yForce = (float) (forward*Math.sin(Math.toRadians(tankDir)));

        b.applyForceToCenter(new Vector2(xForce * moveSpeed, yForce * moveSpeed).scl(delta),true);

        calcTowerAngle();
    }

    public void render(OrthographicCamera cam,float delta){

        Main.batch.setProjectionMatrix(cam.projection);
        Main.batch.begin();

        for(Body b: bulletBList){

            Vector2 tmpPos = b.getPosition();

            Main.batch.draw(bTex,
                    tmpPos.x - Main.WORLDSIZE_WIDTH / 2,
                    tmpPos.y - Main.WORLDSIZE_HEIGHT / 2,
                    bulletSize / 2,
                    bulletSize / 2,
                    bulletSize,
                    bulletSize,
                    1.0f,
                    1.0f,
                    (float)towerAngle,
                    0,
                    0,
                    bTex.getWidth(),
                    bTex.getHeight(),
                    false,
                    false);

        }
        Main.batch.draw(tankTex,
                pos.x - Main.WORLDSIZE_WIDTH / 2,
                pos.y - Main.WORLDSIZE_HEIGHT / 2,
                size.x / 2,
                size.y / 2,
                size.x,
                size.y,
                1.0f,
                1.0f,
                tankDir,
                0,
                0,
                tankTex.getWidth(),
                tankTex.getHeight(),
                false,
                false);

        /**   Tank
          *
          *   4  ________
          *   8 |        |
          *   p |________| 64px
          *   x
          *
          *   Turret
         *
          *   2  ________
          *   4 |________|
          *   px    64px
          *
          */

        Main.batch.draw(towerTex,
                pos.x-Main.WORLDSIZE_WIDTH/2+size.x/4f,// Lower left corner
                pos.y-Main.WORLDSIZE_HEIGHT/2+size.x/4f,//Lower left corner
                size.x/4f, // Center of rotation
                size.x/4f, // Center of rotation
                size.x,
                size.x/2f,
                1.0f,
                1.0f,
                (float)Math.toDegrees(towerAngle),
                0,
                0,
                towerTex.getWidth(),
                towerTex.getHeight(),
                false,
                false);
        Main.batch.end();

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

        this.mouseX = mouseX;
        this.mouseY = 1.0f - mouseY;// Invert Y component
    }

    public void calcTowerAngle(){
        // Screen position of tank, scale 0f-1f
        scrnPos.set((pos.x - Main.WORLDSIZE_WIDTH / 2) / Main.WORLDSIZE_WIDTH,
                (pos.y - Main.WORLDSIZE_HEIGHT / 2) / Main.WORLDSIZE_HEIGHT);
        double sizeFracX = size.x / Main.WORLDSIZE_WIDTH;
        double sizeFracY = size.y / Main.WORLDSIZE_WIDTH;

        // Add constant to center origo to player center
        scrnPos.set((float) (scrnPos.x + .5f + sizeFracX / 2d), (float) (scrnPos.y + .5f + sizeFracY / 2d));
        //

        double diffX = mouseX-scrnPos.x;
        double diffY = mouseY-scrnPos.y;

        towerAngle = Math.atan2(diffX , diffY) - MathUtils.PI/2f;

        if(towerAngle < 0)
            towerAngle += MathUtils.PI2;

        //Invert angle
        towerAngle = MathUtils.PI2 - towerAngle;

        //Gdx.app.log("App","Tower angle:"+towerAngle);
    }

    public void dispose(){

    }

}
