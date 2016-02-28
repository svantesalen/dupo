package se.klartbra.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that can load images from a file system path.
 * 
 * @author svante
 *
 */
public class ImageLoader {
	private static Logger log = LogManager.getLogger(ImageLoader.class);

	private ImageLoader() {}

	public static BufferedImage loadBufferedImage(String path) {
		try {
			Class<? extends ImageLoader> theClass = (new ImageLoader()).getClass();
			java.net.URL url = theClass.getResource(path);
			return ImageIO.read(url);
		} catch (IOException e) {
			log.error("Exception at loading image.", e);
		}
		return null;
	}

	public static Icon createIcon(String path, int width, int height) {
		BufferedImage picture=loadBufferedImage(path);
		if(picture==null) {
			String message = "Could not load image at path="+path;
			log.error(message);
			return null;
		}
		Image newimg = picture.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon(newimg);
	}

}
