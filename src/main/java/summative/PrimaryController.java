package summative;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javafx.scene.paint.Color;

public class PrimaryController {

    private Stage stage;
    private Image originalImage; // Use this to keep track of the original image

    @FXML
    private ImageView imageView;

    @FXML
    private MenuItem openImage;

    @FXML
    private MenuItem saveImage;

    @FXML
    private MenuItem horizontalFlip;

    @FXML
    private MenuItem verticalFlip;

    @FXML
    private MenuItem rotation;

    @FXML
    private MenuItem invert;

    @FXML
    private MenuItem greyscale;

    @FXML
    private MenuItem reset;

    @FXML
    private MenuItem sepiaTone;

    @FXML
    private MenuItem bulge;

    @FXML
    private MenuItem pixelation;

    @FXML
    private MenuItem vignette;

    @FXML
    private MenuItem brightness;

    @FXML
    private MenuItem colorOverlay;

    @FXML
    private MenuItem edgeDetection;

    @FXML
    private MenuItem emboss;

    @FXML
    private MenuItem edges;

    @FXML
    private MenuItem boxBlur;

    private MenuItem anaglyph;


    @FXML
    void onOpenImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        try {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                originalImage = image;
                imageView.setImage(image);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Image Load Failed");
            alert.setContentText("There was a problem opening your image");
            alert.showAndWait();
        }
    }

    @FXML
    public void onSaveImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

        if (file != null) {
            WritableImage writableImage = imageView.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Image Save Failed");
                alert.setContentText("There was a problem saving your image");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onHorizontalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(width - i - 1, j, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onVerticalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(i, height - j - 1, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onRotation(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int cx = width / 2;
                int cy = height / 2;
                double angle = Math.toRadians(90);
                int dx = i - cx;
                int dy = j - cy;
                int x = (int) (dx * Math.cos(angle) - dy * Math.sin(angle) + cx + 0.5);
                int y = (int) (dx * Math.sin(angle) + dy * Math.cos(angle) + cy + 0.5);
                if (x > 0 && x < width && y > 0 && y < height) {
                    writer.setColor(i, j, reader.getColor(x, y));
                }

            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onInvert(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                Color newColor = new Color(1.0 - color.getRed(), 1.0 - color.getGreen(), 1.0 - color.getBlue(), 1);
                writer.setColor(i, j, newColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onGrayscale(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double shade = color.getRed() * 0.21 + color.getGreen() * 0.71 + color.getBlue() * 0.07;
                Color newColor = new Color(shade, shade, shade, 1);
                writer.setColor(i, j, newColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onBrightness(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = Math.min(color.getRed() + color.getRed() * 0.2, 1.0);
                double green = Math.min(color.getGreen() + color.getGreen() * 0.2, 1.0);
                double blue = Math.min(color.getBlue() + color.getBlue() * 0.2, 1.0);
                Color newColor = new Color(red, green, blue, 1);
                writer.setColor(i, j, newColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onReset(ActionEvent event) {
        imageView.setImage(originalImage);
    }

    @FXML
    void onSepiaTone(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();

                double redMul = red * 0.393 + green * 0.769 + blue * 0.189;
                double greenMul = red * 0.349 + green * 0.686 + blue * 0.168;
                double blueMul = red * 0.272 + green * 0.534 + blue * 0.131;
                Color newColor = new Color(Math.min(redMul, 1), Math.min(greenMul, 1), Math.min(blueMul, 1), 1);
                writer.setColor(i, j, newColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onBulge(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int cx = width / 2;
                int cy = height / 2;
                int dx = i - cx;
                int dy = j - cy;
                double r = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);
                double rf = Math.pow(r, 1.6) / 30;
                int x = (int) (cx + rf * Math.cos(angle) + 0.5);
                int y = (int) (cy + rf * Math.sin(angle) + 0.5);
                if (x > 0 && x < width && y > 0 && y < height) {
                    writer.setColor(i, j, reader.getColor(x, y));
                }

            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onPixelation(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int x = 0; x < width; x += 5) {
            for (int y = 0; y < height; y += 5) {
                Color color = reader.getColor(x, y);
                for (int i = 0; i < 10; i ++) {
                    for (int j = 0; j < 10; j++) {
                        if (x + i > 0 && x + i < width && y + j > 0 && y + j  < height) {
                            writer.setColor(x + i, y + j, color);
                        }
                    }
                }
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onVignette(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                int cx = width / 2;
                int cy = height / 2;
                double max = Math.sqrt(cx * cx + cy * cy);
                double dist = Math.sqrt(Math.pow(i - cx, 2) + Math.pow(j - cx, 2));
                double brightness = Math.max(1 - dist / max, 0.3);
                writer.setColor(i, j, color.deriveColor(0, 1, 1, brightness));

            }
        }
        imageView.setImage(writableImage);
    }
    
    @FXML
    void onColorOverlay(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();
        Color overlayColor = new Color(0.993, 0.5, 0, 1);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                writer.setColor(i, j, color.interpolate(overlayColor, 0.5));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onEdgeDetection(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        double[][] kernel = { { 1, 1, 1 }, { 1, -7, 1 }, { 1, 1, 1 } };
        final int SIZE = kernel.length;
        final int OFFSET = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for (int kx = 0; kx < SIZE && y + kx < height && x + kx < width; kx++) {
                    for (int ky = 0; ky < SIZE; ky++) {
                        Color color = reader.getColor(x + kx - OFFSET, y + kx - OFFSET);
                        red += color.getRed() * kernel[kx][ky];
                        green += color.getGreen() * kernel[kx][ky];
                        blue += color.getBlue() * kernel[kx][ky];
                    }
                }
                double newRed = Math.max(0, Math.min(red, 1));
                double newGreen = Math.max(0, Math.min(green, 1));
                double newBlue = Math.max(0, Math.min(blue, 1));

                Color newColor = new Color(newRed, newGreen, newBlue, 1);
                writer.setColor(x, y, newColor);
            }
        }
        imageView.setImage(writableImage);
    }
    @FXML
    void onEdges(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        double[][] gx = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
        double[][] gy = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double gxRed = 0;
                double gxGreen = 0;
                double gxBlue = 0;
                double gyRed = 0;
                double gyGreen = 0;
                double gyBlue = 0;
                for (int kx = 0; kx < 3; kx++) {
                    for (int ky = 0; ky < 3; ky++) {
                        if (x + kx - 1 > 0 && x + kx - 1 < width) {
                            if (y + ky - 1 > 0 && y + ky - 1 < height) {
                                Color color = reader.getColor(x + kx - 1, y + ky - 1);
                                gxRed += color.getRed() * gx[kx][ky];
                                gxGreen += color.getGreen() * gx[kx][ky];
                                gxBlue += color.getBlue() * gx[kx][ky];
                                gyRed += color.getRed() * gy[kx][ky];
                                gyGreen += color.getGreen() * gy[kx][ky];
                                gyBlue += color.getBlue() * gy[kx][ky];
                                }
                        }
                    }
                }
                double newRed = Math.min(Math.sqrt(gxRed * gxRed + gyRed * gyRed), 1);
                double newGreen = Math.min(Math.sqrt(gxGreen * gxGreen + gyGreen * gyGreen), 1);
                double newBlue = Math.min(Math.sqrt(gxBlue * gxBlue + gyBlue * gyBlue), 1);
                Color newColor = new Color(newRed, newGreen, newBlue, 1);
                writer.setColor(x, y, newColor);
            }
        }
        imageView.setImage(writableImage);
    }
    @FXML
    void onBoxBlur(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int size = 3;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                int count = 0;
                for (int kx = 0; kx < size; kx++) {
                    for (int ky = 0; ky < size; ky++) {
                        if (x + kx - 1 > 0 && x + kx - 1 < width) {
                            if (y + ky - 1 > 0 && y + ky - 1 < height) {
                                Color color = reader.getColor(x + kx - 1, y + ky - 1);
                                red += color.getRed();
                                green += color.getGreen();
                                blue += color.getBlue();
                                count++;
                            }
                        }
                    }
                }
                double newRed = Math.min(red / count, 1);
                double newGreen = Math.min(green / count, 1);
                double newBlue = Math.min(blue / count, 1);
                Color newColor = new Color(newRed, newGreen, newBlue, 1);
                writer.setColor(x, y, newColor);
            }
        }
        imageView.setImage(writableImage);
    }
    @FXML
    void onEmboss(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        double[][] kernel = { { -2, -1, 0 }, { -1, 1, 1 }, { 0, 1, 2 } };
        final int SIZE = kernel.length;
        final int OFFSET = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for (int kx = 0; kx < SIZE && (y + kx) < height && (x+kx) < width; kx++) {
                    for (int ky = 0; ky < SIZE; ky++) {
                        Color color = reader.getColor(x + kx - OFFSET, y + kx - OFFSET);
                        red += color.getRed() * kernel[kx][ky];
                        green += color.getGreen() * kernel[kx][ky];
                        blue += color.getBlue() * kernel[kx][ky];
                    }
                }
                double newRed = Math.max(0, Math.min(red, 1));
                double newGreen = Math.max(0, Math.min(green, 1));
                double newBlue = Math.max(0, Math.min(blue, 1));

                Color newColor = new Color(newRed, newGreen, newBlue, 1);
                writer.setColor(x, y, newColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onAnaglyph(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int size = 3;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color leftColor = reader.getColor(x, y);
                double red = 0;
                double green = 0;
                double blue = 0;
                double newRed = Math.min(red, 1);
                double newGreen = Math.min(green, 1);
                double newBlue = Math.min(blue, 1);
                Color newColor = new Color(newRed, newGreen, newBlue, 1);
                writer.setColor(x, y, newColor);
            }
        }
        imageView.setImage(writableImage);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
