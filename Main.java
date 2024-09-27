package hellofx;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


public class Main extends Application {

    private boolean isCtrlPressed = false;
    private double x_at_scrollpane_pressed;
    private double y_at_scrollpane_pressed;

    @Override
    public void start(Stage primaryStage) throws Exception{
        

        //Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
        primaryStage.setTitle("vhdl builder");
        //primaryStage.setScene(new Scene(root, 400, 300));

        // PANE
        Pane whiteboard = new Pane();
        whiteboard.setPrefSize(1600, 1200);
        //whiteboard.setStyle("-fx-background-color: lightgrey;");

        // SCROLL PANE
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setContent(whiteboard);
        scrollpane.setPannable(false);
        scrollpane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

            if (isCtrlPressed){
                x_at_scrollpane_pressed = event.getSceneX();
                y_at_scrollpane_pressed = event.getSceneY();
                event.consume();
            }
        });

        scrollpane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {

            if (isCtrlPressed) {
                double deltaX = x_at_scrollpane_pressed - event.getSceneX();
                double deltaY = y_at_scrollpane_pressed - event.getSceneY();            
                //
                scrollpane.setHvalue(scrollpane.getHvalue()+deltaX / whiteboard.getWidth());
                scrollpane.setVvalue(scrollpane.getVvalue()+deltaY / whiteboard.getHeight());
                
                x_at_scrollpane_pressed = event.getSceneX();
                y_at_scrollpane_pressed = event.getSceneY();
                event.consume();
            }
        });

        // BLOCK PALETTE AREA
        VBox blockPalette = new VBox(10);
        Button rectButton = new Button("Add Rectangle");
        Button circleButton = new Button("Add Circle");

        blockPalette.getChildren().addAll(rectButton, circleButton);

        // Handle block creation when buttons are clicked
        rectButton.setOnAction(e -> addRectangle(whiteboard));
        circleButton.setOnAction(e -> addCircle(whiteboard));

        // Layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setLeft(blockPalette);
        layout.setCenter(scrollpane);

        Scene scene = new Scene(layout, 800, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                isCtrlPressed = true;
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL){
                isCtrlPressed = false;
            }
        });
        primaryStage.setScene(scene);


        primaryStage.show();
    }

    // Add a rectangle to the whiteboard
    private void addRectangle(Pane whiteboard) {
        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(100, 60);
        rect.setStyle("-fx-fill: blue;");
        rect.setX(100);
        rect.setY(100);
        //rect.setTranslateX(100);
        //rect.setTranslateY(100);
        makeDraggable(rect);
        whiteboard.getChildren().add(rect);
    }

    // Add a circle to the whiteboard
    private void addCircle(Pane whiteboard) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(50);
        circle.setStyle("-fx-fill: red;");
        circle.setCenterX(200);
        circle.setCenterY(200);
        makeDraggable(circle);
        whiteboard.getChildren().add(circle);
    }

    double draggable_x_at_press;
    double draggable_y_at_press;
    double deltaX, deltaY;
    double initial_translate_x;
    double initial_translate_y;


    // Make a node draggable
    private void makeDraggable(javafx.scene.Node node) {

        node.setOnMousePressed(event -> {
            draggable_x_at_press = event.getSceneX();
            draggable_y_at_press = event.getSceneY();
            //
            initial_translate_x = node.getTranslateX();
            initial_translate_y = node.getTranslateY();
        });

        node.setOnMouseDragged(event -> {
            deltaX = event.getSceneX()-draggable_x_at_press;
            deltaY = event.getSceneY()-draggable_y_at_press;
            node.setTranslateX(initial_translate_x + deltaX);
            node.setTranslateY(initial_translate_y + deltaY);
        });
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
