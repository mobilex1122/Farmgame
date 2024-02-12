package cz.janpalma.farmgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Farmgame extends Game {
	SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;
	private Viewport viewport;

	@Override
	public void create () {
		// Start up functions
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render(); // important!
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width,height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
