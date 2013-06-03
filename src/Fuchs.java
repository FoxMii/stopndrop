import core.ai.AiMapInfo;
import core.ai.AiPlayerInfo;
import core.map.FieldType;
import core.player.Player;
import core.player.Player.Action;
import core.player.PlayerController;
import core.Position;

import java.util.LinkedList;
import java.util.List;

public class Fuchs extends PlayerController {

	int LIMIT = 70;
	LinkedList<Position> history = new LinkedList<Position>();
	
	@Override
	public String getName() {
		return "Fuchs";
	}

	@Override
	public String getAuthor() {
		return "Lisa";
	}

	@Override
	public Action think(AiMapInfo map, AiPlayerInfo[] enemies,AiPlayerInfo ownPlayer) {
		Position now = ownPlayer.getPosition();
		
		if (history.isEmpty() || !history.getFirst().equals(now))
			history.addFirst(ownPlayer.getPosition());
		
		LinkedList<Position> prev = new LinkedList<Position>(history);
		
		if (go(now.up(), map, enemies, ownPlayer, prev, LIMIT))
			return Player.Action.MOVE_UP;
		if (go(now.down(), map, enemies, ownPlayer, prev, LIMIT))
			return Player.Action.MOVE_DOWN;
		if (go(now.left(), map, enemies, ownPlayer, prev, LIMIT))
			return Player.Action.MOVE_LEFT;
		if (go(now.right(), map, enemies, ownPlayer, prev, LIMIT))
			return Player.Action.MOVE_RIGHT;
		
		return Player.Action.MOVE_DOWN;
	}
	
	public boolean go(Position now, AiMapInfo map, AiPlayerInfo[] enemies, AiPlayerInfo ownPlayer, LinkedList<Position> prev, int limit) {

		if (limit <= 0)
			return false;
		
		for (Position position : prev) if (now.equals(position)) return false;
		
		if (map.getField(now) == FieldType.ACTION_FIELD_FLAG)
			return true;
		
		if (!map.getField(now).isSave())
			return false;
		
		prev.add(now);
		
		boolean up = false, down = false, left = false, right = false;
		
		up = go(now.up(), map, enemies, ownPlayer, prev, limit - 1);
		down = go(now.down(), map, enemies, ownPlayer, prev, limit - 1);
		left = go(now.left(), map, enemies, ownPlayer, prev, limit - 1);
		right = go(now.right(), map, enemies, ownPlayer, prev, limit - 1);
		
		return up || down || left || right;
	}

}
			
	


