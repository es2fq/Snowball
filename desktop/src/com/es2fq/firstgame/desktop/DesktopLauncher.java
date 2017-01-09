package com.es2fq.firstgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.es2fq.firstgame.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameClass.WIDTH;
		config.height = GameClass.HEIGHT;
		config.title = GameClass.TITLE;
		new LwjglApplication(new GameClass(), config);
	}
}
