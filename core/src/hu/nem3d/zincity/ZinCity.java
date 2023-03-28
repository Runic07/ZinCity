package hu.nem3d.zincity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;




public class ZinCity extends Game {
	
	@Override
	public void create () {
		this.setScreen(new MainMenu());
		System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
