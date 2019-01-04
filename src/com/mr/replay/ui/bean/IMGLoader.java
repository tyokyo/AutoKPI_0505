package com.mr.replay.ui.bean;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class IMGLoader {
	private static Logger LOG = Logger.getLogger(IMGLoader.class.getName());
	public static String[][] getPX(String args) {
		int[] rgb = new int[3];
		File file = new File(args);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		String[][] list = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
			rgb[1] = (pixel & 0xff00) >> 8;
			rgb[2] = (pixel & 0xff);
			list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
			}
		}
		return list;
	}

	public static String compareImage(String imgPath1, String imgPath2){
		String[] images = {imgPath1, imgPath2};
		if (images.length == 0) {
			LOG.info("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int same = 0;
		int diff = 0;
		int i = 0, j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m=0; m<strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n=0; n<value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							same++;
						} else {
							diff++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		list1 = getPX(images[1]);
		list2 = getPX(images[0]);
		i = 0;
		j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m=0; m<strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n=0; n<value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							same++;
						} else {
							diff++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		String baifen = "";
		try {
			baifen = ((Double.parseDouble(same + "") / Double.parseDouble((diff + same) + "")) + "");
			baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
		} catch (Exception e) {
			baifen = "0";
		}
		if (baifen.length() <= 0) {
			baifen = "0";
		}
		if(diff == 0){
			baifen="100";
		}
		return "Same" + same + " Dif" + diff + " Percent" + Integer.parseInt(baifen) + "%";
	}

	public static void main(String[] args){
		System.out.println(IMGLoader.compareImage("E:\\AutoKPI\\video\\20140319092043\\04\\001\\00048_00480.jpg", "E:\\AutoKPI\\video\\20140319092043\\04\\001\\00060_00600.jpg"));
		//System.out.println(IMGLoader.compareImage("D:\\34.bmp", "D:\\35.bmp"));
	}
}
