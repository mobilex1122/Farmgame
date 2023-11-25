package cz.janpalma.farmgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cz.janpalma.farmgame.components.PositionComponent;
import cz.janpalma.farmgame.components.TextureComponent;
import cz.janpalma.farmgame.systems.RenderSystem;

public class Farmgame extends Game {
	SpriteBatch batch;
	Texture img;



	Engine engine;
	Entity player;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		engine = new PooledEngine();


		engine.addSystem(new RenderSystem(this.batch));

		this.player = engine.createEntity();
		this.player.add(engine.createComponent(PositionComponent.class));
		TextureComponent playerTexture = engine.createComponent(TextureComponent.class);
		playerTexture.region = new TextureRegion(img, 256,256);
		this.player.add(playerTexture);
		engine.addEntity(player);
	}


	@Override
	public void render () {
		System.out.println(engine.getEntitiesFor(Family.all(PositionComponent.class, TextureComponent.class).get()));
		engine.update(Gdx.graphics.getDeltaTime());

		PositionComponent position = this.player.getComponent(PositionComponent.class);

		float delta = Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= 100 * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += 100 * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += 100 * delta;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= 100 * delta;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
