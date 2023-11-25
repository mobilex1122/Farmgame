package cz.janpalma.farmgame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import cz.janpalma.farmgame.components.BodyComponent;
import cz.janpalma.farmgame.components.PositionComponent;

public class BodySystem extends EntitySystem {
	private Engine engine;


	private final Family renderFamily = Family.all(PositionComponent.class, BodyComponent.class).get();

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float deltaTime) {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(renderFamily);
		// System.out.println();
		for (Entity entity : entities) {
			PositionComponent position = entity.getComponent(PositionComponent.class);
			BodyComponent body = entity.getComponent(BodyComponent.class);

			position.y = body.body.getPosition().y;
			position.x = body.body.getPosition().x;
		}

		// renderQueue.clear();
	}
}
