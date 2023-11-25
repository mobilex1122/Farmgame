package cz.janpalma.farmgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import cz.janpalma.farmgame.components.BodyComponent;
import cz.janpalma.farmgame.components.PositionComponent;
import cz.janpalma.farmgame.components.TextureComponent;
import cz.janpalma.farmgame.systems.BodySystem;
import cz.janpalma.farmgame.systems.RenderSystem;

public class GameScreen implements Screen {

    Texture img;


    OrthographicCamera camera;
    World world;
    Engine engine;
    Entity player;

    Box2DDebugRenderer debugRenderer;

    public GameScreen (final Farmgame game) {
        img = new Texture("badlogic.jpg");
        engine = new PooledEngine();
        Box2D.init();
        world = new World(new Vector2(0, -0), true);


        this.camera = game.camera;

        debugRenderer = new Box2DDebugRenderer();


        engine.addSystem(new RenderSystem(game.batch));
        engine.addSystem(new BodySystem());

        this.player = engine.createEntity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(5, 10);

        Body body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        circle.dispose();

        BodyComponent bodyComp = engine.createComponent(BodyComponent.class);
        bodyComp.body = body;
        this.player.add(bodyComp);
        this.player.add(engine.createComponent(PositionComponent.class));
        TextureComponent playerTexture = engine.createComponent(TextureComponent.class);
        playerTexture.region = new TextureRegion(img, 256,256);
        this.player.add(playerTexture);
        engine.addEntity(player);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        world.step(1/60f, 6, 2);


        System.out.println(engine.getEntitiesFor(Family.all(PositionComponent.class, TextureComponent.class).get()));
        engine.update(Gdx.graphics.getDeltaTime());

        debugRenderer.render(world, camera.combined);

        BodyComponent playerBody = player.getComponent(BodyComponent.class);

        Vector2 vell = new Vector2(0,0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) vell.x = -100;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) vell.x = 100;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) vell.y = 100;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) vell.y = -100;

        playerBody.body.setLinearVelocity(vell.x, vell.y);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
