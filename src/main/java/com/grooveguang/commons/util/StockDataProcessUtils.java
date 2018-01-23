package com.grooveguang.commons.util;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class StockDataProcessUtils {

	public static void resizeImages(InputStream is, String targetPath) {
			OutputStream os = null;
			try {
				BufferedImage sourceImage = ImageIO.read(is);
				// 2.获取原始图片的宽高值
				int sourceWidth = sourceImage.getWidth();
				int sourceHeight = sourceImage.getHeight();

				// 3.计算目标图片的宽高值
				int targetWidth = sourceWidth;
				int targetHeight = sourceHeight;

				if (sourceWidth > 30) {
					// 按比例压缩目标图片的尺寸
					targetWidth = 30;
					targetHeight = sourceHeight / (sourceWidth / 30);

				}

				// 4.创建压缩后的目标图片对应的Image对象
				BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

				// 5.绘制目标图片
				targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);

				// 7.创建目标图片对应的输出流
				os = new FileOutputStream(targetPath);

				// 8.获取JPEG图片编码器
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);

				// 9.JPEG编码
				encoder.encode(targetImage);

				// 10.返回文件名
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}

}
