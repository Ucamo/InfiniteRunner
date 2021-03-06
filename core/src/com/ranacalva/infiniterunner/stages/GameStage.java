package com.ranacalva.infiniterunner.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.ranacalva.infiniterunner.InfiniteRunnerClass;
import com.ranacalva.infiniterunner.actors.Background;
import com.ranacalva.infiniterunner.actors.Enemy;
import com.ranacalva.infiniterunner.actors.Ground;
import com.ranacalva.infiniterunner.actors.Runner;
import com.ranacalva.infiniterunner.screens.GameScreen;
import com.ranacalva.infiniterunner.utils.BodyUtils;
import com.ranacalva.infiniterunner.utils.Constants;
import com.ranacalva.infiniterunner.utils.SoundUtils;
import com.ranacalva.infiniterunner.utils.WorldUtils;



public class GameStage extends Stage implements ContactListener {
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH= Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT=Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;

    private final float TIME_STEP = 1/300f;
    private float accumulator=0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private Vector3 touchPoint;

    public GameStage(){
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
       // renderer = new Box2DDebugRenderer();
    }

    private void setUpWorld(){
        world= WorldUtils.createWorld();
        //Let the world know you are handling contacts
        world.setContactListener(this);
        setUpBackground();
        setUpGround();
        setUpRunner();
        createEnemy();
    }

    private void setUpBackground() {
        addActor(new Background());
    }

    private void setUpGround(){
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpRunner(){
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setupCamera(){
        camera= new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0f);
        camera.update();
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0,0,getCamera().viewportWidth/2,getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for(Body body : bodies){
            update(body);
        }

        // Fixed timestep
        accumulator+=delta;

        while(accumulator>=delta){
            world.step(TIME_STEP,6,2);
            accumulator-=TIME_STEP;
        }

        //TODO: Implement interpolation

    }

    //@Override
    //public void draw(){
    //    super.draw();
    //    renderer.render(world,camera.combined);
    //}

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();

        }else if(leftSideTouched(touchPoint.x,touchPoint.y)){
            runner.dodge();
        }

        if(runner.isHit())
        {
            setUpWorld();
        }

        return super.touchDown(x, y, pointer, button);
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        if(runner.isDodging()){
            runner.stopDodge();
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean leftSideTouched(float x, float y){
        return screenLeftSide.contains(x,y);
    }

    /*
    Helper function to get the actual coordinates in my world
    @param x
    @param y
     */

    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) || (BodyUtils.bodyIsEnemy(a)&&BodyUtils.bodyIsRunner(b))){
            runner.hit();
        }else if((BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsGround(b)) || (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))){
            runner.landed();
        }

    }

    @Override
    public void endContact(Contact contact){

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold){

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse){

    }



    public void update(Body body){
        if(!BodyUtils.bodyInBounds(body)){
            if(BodyUtils.bodyIsEnemy(body) && !runner.isHit()){
                createEnemy();
            }
            world.destroyBody(body);
        }
    }

    private void createEnemy(){
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        addActor(enemy);
    }


}
