package mist.client.engine;

public class Time {
	private static float delta = 0;
	private static long lastTime = 0;
	
	
	public static void loop(){
		lastTime = System.nanoTime();
	}
	
	public static double getDelta(){
		return System.nanoTime() - lastTime;
	}
	
	public static double getDeltaSeconds(){
		return getDelta() / 1000000000;
	}
}
