import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class DumpsterDiversHome extends Application {

    private StackPane root;
    private VBox instructionOverlay;
    private Rectangle dimmer;

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        root = new StackPane();

        // Wallpaper
        Image wallpaper = new Image("file:Images/wallpaper.png");
        ImageView wallpaperView = new ImageView(wallpaper);
        wallpaperView.setPreserveRatio(true);
        wallpaperView.fitWidthProperty().bind(root.widthProperty());
        wallpaperView.setOpacity(0); // start fully transparent

        // Logo
        Image logo = new Image("file:Images/Logo_2.png");
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(500);

        // Buttons
        Button startButton = new Button("Start Playing");
        Button howToPlayButton = new Button("How to Play");
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 20;");
        howToPlayButton.setStyle("-fx-font-size: 18px; -fx-padding: 10 20;");
        startButton.setOnAction(e -> System.out.println("Game Starting..."));
        howToPlayButton.setOnAction(e -> showInstructions());

        // Menu VBox
        VBox menu = new VBox(30, logoView, startButton, howToPlayButton);
        menu.setAlignment(Pos.CENTER);
        menu.setTranslateY(-40);
        menu.setOpacity(0); // start fully transparent

        // Create the instruction overlay (dimmer + instructions)
        createInstructionOverlay();

        // Add everything to the root
        root.getChildren().addAll(wallpaperView, menu, dimmer, instructionOverlay);

        // Apply the fade transitions
        applyFadeTransition(wallpaperView, menu);

        // Set up scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Dumpster Divers");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1190);
        primaryStage.setMinHeight(680);
        primaryStage.show();
    }

    // Method for fading transition in the home page
    private void applyFadeTransition(ImageView wallpaperView, VBox menu) {
        FadeTransition fadeWallpaper = new FadeTransition(Duration.seconds(3.5), wallpaperView);
        fadeWallpaper.setFromValue(0);
        fadeWallpaper.setToValue(1);
        fadeWallpaper.setOnFinished(event -> {
            // Fade in menu after wallpaper fades in
            FadeTransition fadeMenu = new FadeTransition(Duration.seconds(1.2), menu);
            fadeMenu.setFromValue(0);
            fadeMenu.setToValue(1);
            fadeMenu.play();
        });
        fadeWallpaper.play();
    }

    // Method to create the instructions popup overlay
    private void createInstructionOverlay() {
        dimmer = new Rectangle(500, 600, Color.rgb(0, 0, 0, 0.4));
        dimmer.widthProperty().bind(root.widthProperty());
        dimmer.heightProperty().bind(root.heightProperty());
        dimmer.setVisible(false);

        instructionOverlay = new VBox(15);
        instructionOverlay.setAlignment(Pos.CENTER);
        instructionOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20px;");
        instructionOverlay.setMaxWidth(450);
        instructionOverlay.setMaxHeight(600);
        instructionOverlay.setVisible(false);

        Text instructions = new Text(
            "Welcome to Dumpster Divers!\n\n" +
            "➡ Search through dumpsters for valuable items.\n" +
            "🚨 Watch out for cops! After 3 offenses, it's game over.\n" +
            "💰 Progress by collecting valuable loot without getting caught."
        );
        instructions.setFill(Color.WHITE);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            instructionOverlay.setVisible(false);
            dimmer.setVisible(false);
        });

        instructionOverlay.getChildren().addAll(instructions, closeBtn);
    }

    // ✅ Method to show the instructions popup
    private void showInstructions() {
        instructionOverlay.setVisible(true);
        dimmer.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
