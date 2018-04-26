package com.swetake.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QrcodeUtil {
	
	public static void main(String[] args) throws Exception {
		String filePath="d:qrcode.png";
		File qrFile=new File(filePath);
		/**
		 * 二维码内容
		 */
		String encodedate="http://www.baidu.com";
		 try { 
             qrCodeEncode(encodedate, qrFile); 
         } catch (IOException e) { 
             e.printStackTrace(); 
         } 
    
         // 解码 
         String reText =qrCodeDecode(qrFile); 
         System.out.println(reText); 
	
	}
	
	/**
	 * 生成二维码
	 */
	static void qrCodeEncode(String encodedate,File destFile)throws Exception{
		Qrcode qrcode=new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');//纠错级别（L 7%，M 15% ，Q 25%,H 30%）和版本有关
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);//设置Qrcode包的版本
		
		byte[] d=encodedate.getBytes("GBK");//得到二维码内容的字节集
		BufferedImage bi=new BufferedImage(139, 139, BufferedImage.TYPE_INT_RGB);//设置样式
		//createGraphice  创建图层
		Graphics2D g=bi.createGraphics();
		
		g.setBackground(Color.WHITE);//设置背景颜色白色
		g.clearRect(0, 0, 139, 139); //矩形X Y WIDTH HEIGHT 
		g.setColor(Color.BLACK); //设置图像颜色黑色
		
		if (d.length>0&&d.length<123) {
			boolean[][] b=qrcode.calQrcode(d);
			for (int i = 0; i < b.length; i++) {
				for (int j = 0; j < b.length; j++) {
					if (b[i][j]) {
						g.fillRect(j*3+2, i*3+2,3, 3);
					}
				}
			}
		}
		
//      Image img = ImageIO.read(new File("D:/tt.png"));  logo 
//      g.drawImage(img, 25, 55,60,50, null); 
                     
          g.dispose(); // 释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics 对象 
          bi.flush(); // 刷新此 Image 对象正在使用的所有可重构的资源 
     
          ImageIO.write(bi, "png", destFile); 
//        System.out.println("Input Encoded data is：" + encodeddata); 
      } 
	
	
	/**
	 * 将二维码转换成字符串
	 * 解析二维码 返回解析内容
	 */
	 public static String qrCodeDecode(File imageFile) { 
         String decodedData = null; 
         QRCodeDecoder decoder = new QRCodeDecoder(); 
         BufferedImage image = null; 
         try { 
             image = ImageIO.read(imageFile); 
         } catch (IOException e) { 
             System.out.println("Error: " + e.getMessage()); 
         } 
    
         try { 
             decodedData = new String(decoder.decode(new J2SEImage(image)), "GBK"); 
//           System.out.println("Output Decoded Data is：" + decodedData); 
         } catch (DecodingFailedException dfe) { 
             System.out.println("Error: " + dfe.getMessage()); 
         } catch (UnsupportedEncodingException e) { 
             e.printStackTrace(); 
         } 
         return decodedData; 
     } 
    
     static class J2SEImage implements QRCodeImage { 
         BufferedImage image; 
        
         public J2SEImage(BufferedImage image) { 
             this.image = image; 
         } 
        
         public int getWidth() { 
             return image.getWidth(); 
         } 
        
         public int getHeight() { 
             return image.getHeight(); 
         } 
        
         public int getPixel(int x, int y) { 
             return image.getRGB(x, y); 
         } 
     } 
}
