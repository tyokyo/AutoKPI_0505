package com.ckt.autokpi.video.helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 
 * @author houyin.tian
 *
 */
public class ImageHelper {
	private static BufferedImage defaultImage = null;

	public static BufferedImage getDefaultImage() {
		if (defaultImage == null) {
			BufferedImage bi = null;
			InputStream in = ImageHelper.class
					.getResourceAsStream("/com/ckt/autokpi/video/icon/default.jpg");
			if (in == null) {
				System.err.println("Image not found.");
				return null;
			}
			try {
				bi = ImageIO.read(in);
			} catch (IOException e) {
				System.err.println("Unable to read image.");
				e.printStackTrace();
			}
			defaultImage = new BufferedImage(640, 480,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = defaultImage.createGraphics();
			g2d.drawImage(bi, 0, 0, defaultImage.getWidth(),
					defaultImage.getHeight(), null);
			g2d.dispose();
		}
		return defaultImage;
	}
}
