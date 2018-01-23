package com.grooveguang.commons.util;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class GlobalUtil {
	/**
	 * 对源字符串进行加密操作
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(String source) {

		if (source == null || source.length() == 0) {
			return null;
		}

		// 1.准备工作
		StringBuilder builder = new StringBuilder();
		char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		// 2.获取源字符串的字节数组
		byte[] bytes = source.getBytes();

		// 3.获取MessageDigest对象
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 4.执行加密操作
		byte[] targetBytes = digest.digest(bytes);

		// 5.遍历字节数组
		for (int i = 0; i < targetBytes.length; i++) {
			byte b = targetBytes[i];

			// 6.取当前字节的高四位值
			int highValue = (b >> 4) & 15;

			// 7.取当前字节的低四位值
			int lowValue = b & 15;

			// 8.以上面计算的结果为下标从字符数组中取值
			char highChar = c[highValue];
			char lowChar = c[lowValue];

			// 9.拼装字符串
			builder.append(highChar).append(lowChar);

		}

		return builder.toString();
	}

	/**
	 * 压缩并保存图片
	 * 
	 * @param inputStream
	 * @param realPath
	 * @return
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {

		OutputStream out = null;

		try {
			// 1.构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			// 2.获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();

			// 3.计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;

			if (sourceWidth > 50) {
				// 按比例压缩目标图片的尺寸
				targetWidth = 50;
				targetHeight = sourceHeight / (sourceWidth / 50);

			}

			// 4.创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth,
					targetHeight, BufferedImage.TYPE_INT_RGB);

			// 5.绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth,
					targetHeight, null);

			// 6.构造目标图片文件名
			String targetFileName = System.nanoTime() + ".jpg";

			// 7.创建目标图片对应的输出流
			out = new FileOutputStream(realPath + "/" + targetFileName);

			// 8.获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

			// 9.JPEG编码
			encoder.encode(targetImage);

			// 10.返回文件名
			return "manager/picture/" + targetFileName;

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		} finally {
			// 10.关闭流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
