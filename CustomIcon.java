import java.awt.Image;
import javax.swing.ImageIcon;

public class CustomIcon {

    private static final String DEFAULT_ICON_PATH = "images/back_of_card.jpg";
    private static final int ICON_SIZE = 140;
    // The card back is identical for every card, so it is loaded and scaled once.
    private static final ImageIcon DEFAULT_ICON_IMAGE = loadScaled(DEFAULT_ICON_PATH);

    private String defaultIconPath; // Path to the default image icon
    private String flippedIconPath; // Path to the flipped image icon
    private Boolean isDone; // Variable to track whether the icon and its corresponding image icon has been found or not
    private Boolean isFlipped; // Variable to track whether the icon is currently flipped or not.
    private ImageIcon defaultIconImage; // Variable to store image of the default icon.
    private ImageIcon flippedIconImage; // Variable to store image of the flipped icon.

    public CustomIcon(String flippedIconPath) {
        this.defaultIconPath = DEFAULT_ICON_PATH;
        this.flippedIconPath = flippedIconPath;
        this.isDone = false;
        this.isFlipped = false;

        this.defaultIconImage = DEFAULT_ICON_IMAGE;
        this.flippedIconImage = loadScaled(this.flippedIconPath);
    }

    private static ImageIcon loadScaled(String path) {
        Image original = new ImageIcon(path).getImage();
        return new ImageIcon(original.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
    }

    public String getDefaultIconPath() {
        return defaultIconPath;
    }

    public String getFlippedIconPath() {
        return flippedIconPath;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public Boolean getIsFlipped() {
        return isFlipped;
    }

    public ImageIcon getDefaultIconImage() {
        return defaultIconImage;
    }

    public ImageIcon getFlippedIconImage() {
        return flippedIconImage;
    }


    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public void setIsFlipped(Boolean isFlipped) {
        this.isFlipped = isFlipped;
    }
}