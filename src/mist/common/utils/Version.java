package mist.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import mist.client.engine.Mist;

public class Version {
	private int primary, secondary, build;
	private String title;
	
	private static String versionFilePath = "\\data\\version";
	
	private Version(int primary, int secondary, int build, String title) {
		super();
		this.primary = primary;
		this.secondary = secondary;
		this.build = build;
		this.title = title;
	}
	
	private Version(String title){
		this.title = title;
	}
	
	public void saveAndUpdate(){
		String path = Mist.mainFolder + versionFilePath;
		
		if(Mist.getInstance().isDev)
			build++;
		
		if(build > 1000){
			build = 0;
			secondary++;
		}
		if(secondary > 99){
			secondary = 0;
			primary++;
		}
		
		try {
			PrintWriter p = new PrintWriter(path);
			p.flush();
			p.println(primary + "." + String.format("%02d", secondary) + "." + String.format("%04d", build));
			p.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Version maybeRead(String title){
		String path = Mist.mainFolder + versionFilePath;
		Version v = new Version (0,0,0, title);
		
		Scanner s;
		try {
			s = new Scanner(new File(path));
			String line = "";

			if(s.hasNextLine())
				line = s.nextLine();
			System.out.println(line);
			String part[] = line.split("[.]");
			System.out.println(part[1]);
			
			v.primary = Integer.parseInt(part[0]);
			v.secondary = Integer.parseInt(part[1]);
			v.build = Integer.parseInt(part[2]);
			
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return v;
		
	}
	
	@Override
	public String toString() {
		return primary + "." + secondary + "." + build + " " + title;
	}
}
