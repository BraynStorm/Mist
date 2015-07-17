package mist.client.engine;

public class Time {
	private static float delta = 0;
	private static long lastTime = 0;
	
	
	public static void loop(){
		lastTime = System.nanoTime();
	}
	
	public static float getDelta(){
		return System.nanoTime() - lastTime;
	}
}
