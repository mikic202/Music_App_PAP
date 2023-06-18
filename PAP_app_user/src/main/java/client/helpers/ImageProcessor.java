package client.helpers;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageProcessor {
    static public byte[] convertFilepathToBytes(String path, String format) throws IOException {
        byte data[];
        BufferedImage img = ImageIO.read(new File(path));
        Double imageHeight = 0.0;
        Double imageWidth = 0.0;
        if (img.getWidth() < 100.0 && img.getHeight() < 100) {

        } else if (img.getWidth() > img.getHeight()) {
            imageWidth = 100.0;
            imageHeight = img.getHeight() * (100.0 / img.getWidth());
        } else {
            imageHeight = 100.0;
            imageWidth = img.getWidth() * (100.0 / img.getHeight());
        }

        Image scaledImage = img.getScaledInstance(imageWidth.intValue(), imageHeight.intValue(),
                Image.SCALE_SMOOTH);
        BufferedImage imageBuff = new BufferedImage(imageWidth.intValue(), imageHeight.intValue(),
                BufferedImage.TYPE_INT_RGB);
        imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ImageIO.write(imageBuff, format, buffer);

        data = buffer.toByteArray();
        return data;
    }

    static public BufferedImage convertStringArrayToImage(String stringImage) throws IOException {
        BufferedImage defaultImage = ImageIO
                .read(new ByteArrayInputStream((convertStringArrayToImageBytes(stringImage))));
        return defaultImage;
    }

    static public byte[] convertStringArrayToImageBytes(String stringImage) {
        stringImage = stringImage.replace("[", "");
        stringImage = stringImage.replace("]", "");
        List<String> byteListImage = Arrays.asList(stringImage.split(","));
        byte[] imageData = new byte[byteListImage.size()];

        for (int i = 0; i < byteListImage.size(); i++) {
            imageData[i] = Byte.parseByte(byteListImage.get(i));
        }
        return imageData;
    }
}
