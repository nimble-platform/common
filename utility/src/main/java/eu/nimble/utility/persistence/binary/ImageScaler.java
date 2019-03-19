package eu.nimble.utility.persistence.binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Image scaler for the published images
 *
 * Created by suat on 03-Dec-18.
 */
@Component
public class ImageScaler {
    private static final int THUMBNAIL_MAX_WIDTH = 500;
    private static final int ORIGINAL_IMAGE_MAX_WIDTH = 1920;

    private static final Logger logger = LoggerFactory.getLogger(ImageScaler.class);

    /**
     * Scales the image based on the {@code isThumbnail} parameter. If this parameter is {@code true} the given image
     * is scaled to a square with edge length specified by the {@code THUMBNAIL_MAX_WIDTH} constant.
     *
     * Landscape images are scaled such that the height of the image is reduced to {@code THUMBNAIL_MAX_WIDTH}. Then,
     * the center of the scaled image with a width of {@code THUMBNAIL_MAX_WIDTH} is cropped. The dimensions are swapped
     * for portrait images.
     *
     * If the {@code isThumbnail} is {@code false}, the image is scaled such that its width is reduced to
     * {@code ORIGINAL_IMAGE_MAX_WIDTH}. No check is performed on height.
     *
     * @param originalContent
     * @param isThumbnail
     * @return
     * @throws IOException
     */
    public BufferedImage scale(InputStream originalContent, boolean isThumbnail) throws IOException {
        // parse the original image
        BufferedImage img;
        try {
            img = ImageIO.read(originalContent);
        } catch (IOException e) {
            logger.error("Failed to read the original image content", e);
            throw e;
        }
        // if we could not read the image for some reasons, then return null
        if(img == null){
            return null;
        }
        // get scale ratios
        ScaleRatios ratios = getScaleRatios(img, isThumbnail);

        // resize image
        BufferedImage finalImage = resizeImage(ratios, img, isThumbnail);

        return finalImage;
    }

    private BufferedImage resizeImage(ScaleRatios ratios, BufferedImage image, boolean isThumbnail) {
        int newWidth = (int) (image.getWidth() * ratios.widthRatio);
        int newHeight = (int) (image.getHeight() * ratios.heightRatio);
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();

        int x = 0;
        int y = 0;

        if(isThumbnail) {
            if(newWidth > THUMBNAIL_MAX_WIDTH) {
                x = (newWidth - THUMBNAIL_MAX_WIDTH) / 2;
                newWidth = THUMBNAIL_MAX_WIDTH;
            }
            if(newHeight > THUMBNAIL_MAX_WIDTH) {
                y = (newHeight - THUMBNAIL_MAX_WIDTH) / 2;
                newHeight = THUMBNAIL_MAX_WIDTH;
            }
        }

        BufferedImage subImage = resizedImage.getSubimage(x, y, newWidth, newHeight);
        return subImage;
    }

    private ScaleRatios getScaleRatios(BufferedImage originalImage, boolean isThumbnail) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        double heightRatio = 1;
        double widthRatio = 1;

        if(isThumbnail) {
            if (width >= height) {
                if (height > THUMBNAIL_MAX_WIDTH) {
                    heightRatio = (double) THUMBNAIL_MAX_WIDTH / (double) height;
                    widthRatio = heightRatio;
                }

            } else {
                if (width > THUMBNAIL_MAX_WIDTH) {
                    widthRatio = (double) THUMBNAIL_MAX_WIDTH / (double) width;
                    heightRatio = widthRatio;
                }
            }

        } else {
            if (width > ORIGINAL_IMAGE_MAX_WIDTH) {
                widthRatio = (double) ORIGINAL_IMAGE_MAX_WIDTH / (double) width;
                heightRatio = widthRatio;
            }
        }

        return new ScaleRatios(widthRatio, heightRatio);
    }

    private class ScaleRatios {

        public ScaleRatios(double widthRatio, double heightRatio) {
            this.widthRatio = widthRatio;
            this.heightRatio = heightRatio;
        }

        private double widthRatio;
        private double heightRatio;
    }
}
