package game;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation
{
   BufferedImage[] image;

   int delay;
   int waittime;
   int current = 1;

   public Animation(String name, int count, int delay)
   {
      image = new BufferedImage[count];

      for(int i = 0; i < image.length; i++)
		try {
			if(i == 9)
				image[i] = ImageIO.read(new File(name + "09.png"));
			if(i == 10)
				image[i] = ImageIO.read(new File(name + "10.png"));
			else
				image[i] = ImageIO.read(new File(name + "0" + i + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}


      this.delay = delay;
      waittime = delay;
   }


   public Image getStaticImage()
   {
      return image[0];
   }

   public Image getCurrentImage()
   {
      delay--;

      if(delay == 0)
      {
         delay = waittime;

         current++;
      }
      if(current >= image.length)
    		  current = 1;
    	
      return image[current];
   }
   
   public void resetCurrentImage()
   {
	   current = 0;
   }
   
   public Image getImageAt(int i)
   {
	   return image[i];
   }

}
