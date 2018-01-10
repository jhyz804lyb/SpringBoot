package com.vuck.utils;

import com.google.zxing.*;
import com.google.zxing.common.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class ImageUtils
{
    /**
     * 生成二维码
     *
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] CreateImgCode(int width, int height, String content, String charset)
            throws WriterException, IOException
    {
        if (StringUtils.isEmpty(charset)) charset = "utf-8";
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, charset);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        //MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // MatrixToImageWriter.writeToStream(bitMatrix,format,out);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // Path path = FileSystems.getDefault().getPath("D:/temp/temp.png");
        //Path path =  FileSystem.De
        //  MatrixToImageWriter.writeToPath(bitMatrix,format,path);
        ImageIO.write(bufferedImage, format, out);
        return out.toByteArray();
    }

    public static byte[] CreateImgCode(int width, String content)
            throws WriterException, IOException
    {
        return CreateImgCode(width, width, content, null);
    }

    public static String DecodeImg(byte[] data)
    {
        return DecodeImg(data, null);
    }

    /**
     * 解析二维码
     */
    public static String DecodeImg(byte[] data, String charset)
    {
        String filePath = "D://zxing.png";
        BufferedImage image;
        try
        {
            if (data == null) return null;
            if (StringUtils.isEmpty(charset)) charset = "utf-8";
            ByteArrayInputStream bin = new ByteArrayInputStream(data);
            image = ImageIO.read(bin);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, charset);
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            return result.getText();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] toByteArray(Object obj)
    {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return bytes;
    }


}
