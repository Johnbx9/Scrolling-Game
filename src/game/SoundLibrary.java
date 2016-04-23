package game;

import java.applet.*;
import java.net.*;
import java.util.HashMap;

public class SoundLibrary 
{
	//Attributes needed in the library
	HashMap<String, AudioClip> soundLibrary;
	
	//Create a hashmap of audio clips for select cases
	public SoundLibrary()
	{
		AudioClip jump = null;
		AudioClip collect = null;
		AudioClip badcollect = null;
		AudioClip death = null;
		//AudioClip gorillaRoar = null;
		//AudioClip variantRoar = null;
		AudioClip music = null;
		
		try
		{
			jump = Applet.newAudioClip(new URL("file:./src/resources/Sounds/jump.wav"));
			collect = Applet.newAudioClip(new URL("file:./src/resources/Sounds/collect.wav"));
			badcollect = Applet.newAudioClip(new URL("file:./src/resources/Sounds/badcollect.wav"));
			death = Applet.newAudioClip(new URL("file:./src/resources/Sounds/death.wav"));
			//gorillaRoar = Applet.newAudioClip(new URL("file:./Sounds/gorilla.wav"));
			//variantRoar = Applet.newAudioClip(new URL("file:./Sounds/monster.wav"));
			music = Applet.newAudioClip(new URL("file:./src/resources/Sounds/music.wav"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		soundLibrary = new HashMap(5);
		soundLibrary.put("jump", jump);
		soundLibrary.put("collect", collect);
		soundLibrary.put("badcollect", badcollect);
		soundLibrary.put("death", death);
		//soundLibrary.put("gorilla", gorillaRoar);
		//soundLibrary.put("variant", variantRoar);
		soundLibrary.put("music", music);
	}
	
	//Play a clip when the action happens
	public void playAudio(String name)
	{
		AudioClip sample = soundLibrary.get(name);
		sample.play();
	}
	
	public void stopAudio(String name)
	{
		AudioClip sample = soundLibrary.get(name);
		sample.stop();
	}
}
