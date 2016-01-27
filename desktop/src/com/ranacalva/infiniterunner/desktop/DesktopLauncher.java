package com.ranacalva.infiniterunner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ranacalva.infiniterunner.InfiniteRunnerClass;
import com.ranacalva.infiniterunner.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= Constants.APP_WIDTH;
		config.height= Constants.APP_HEIGHT;
		new LwjglApplication(new InfiniteRunnerClass(), config);
	}
}
