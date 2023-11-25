package cz.janpalma.farmgame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import cz.janpalma.farmgame.components.PositionComponent;
import cz.janpalma.farmgame.components.TextureComponent;

public class RenderSystem extends EntitySystem {

    SpriteBatch batch;

    private Engine engine;


    private final Family renderFamily = Family.all(PositionComponent.class, TextureComponent.class).get();

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        ScreenUtils.clear(0, 0, 0, 1);
        this.batch.begin();

        ImmutableArray<Entity> entities = engine.getEntitiesFor(renderFamily);
        // System.out.println();
        for (Entity entity : entities) {
            PositionComponent position = entity.getComponent(PositionComponent.class);
            TextureComponent texture = entity.getComponent(TextureComponent.class);
            float width = texture.region.getRegionWidth();
            float height = texture.region.getRegionHeight();
            float originX = width / 2;
            float originY = height / 2;
            this.batch.draw(texture.region,position.x - originX,position.y - originY,width,height);
        }
        this.batch.end();
        // renderQueue.clear();
    }
}
