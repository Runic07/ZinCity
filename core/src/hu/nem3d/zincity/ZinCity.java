package hu.nem3d.zincity;


import com.badlogic.gdx.Game;
import hu.nem3d.zincity.Screen.MainMenu;

/**
 * Main class of the ZinCity game
 * @see com.badlogic.gdx.Game
 */
public class ZinCity extends Game {
	
	@Override
	public void create () {
		this.setScreen(new MainMenu());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
