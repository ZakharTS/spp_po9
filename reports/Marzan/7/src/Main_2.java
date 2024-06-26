import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double SIZE = 450;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new HilbertCurvePane();
        Scene scene = new Scene(pane);

            primaryStage.setTitle("Кривая гилберта");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class HilbertCurvePane extends BorderPane {
        private final int UP = 0;
        private final int LEFT = 1;
        private final int DOWN = 2;
        private final int RIGHT = 3;
        private final Polyline polyline;
        private Pane pane;
        private Slider orderSlider;
        private int direction;

        public HilbertCurvePane() {
            setCenter(createCanvas());
            setBottom(createControlBar());
            polyline = new Polyline();
            pane.getChildren().add(polyline);
        }

        private Node createCanvas() {
            pane = new Pane();
            pane.setPrefSize(SIZE, SIZE);
            return pane;
        }

        private Node createControlBar() {
            Label label = new Label("Order:");
            orderSlider = new Slider(0, 10, 0); // Минимальное значение, максимальное значение, начальное значение
            orderSlider.setShowTickLabels(true);
            orderSlider.setShowTickMarks(true);
            orderSlider.setMajorTickUnit(1);
            orderSlider.setBlockIncrement(1);
            orderSlider.valueProperty().addListener((observable, oldValue, newValue) -> drawHilbertCurve(newValue.intValue()));
            HBox hBox = new HBox(5, label, orderSlider);
            hBox.setPadding(new Insets(10));
            hBox.setAlignment(Pos.CENTER);
            return hBox;
        }

        private void drawHilbertCurve(int order) {
            polyline.getPoints().clear();
            direction = UP;
            drawHilbertCurve(order, 0, 0, pane.getWidth(), pane.getHeight());
        }

        private void drawHilbertCurve(int order, double x, double y, double width, double height) {
            ObservableList<Double> points = polyline.getPoints();
            if (order == 0) {
                points.addAll(width / 2 + x, height / 2 + y);
            } else {
                double halfWidth = width / 2;
                double halfHeight = height / 2;
                if (direction == UP) {
                    direction = LEFT;
                    drawHilbertCurve(order - 1, x, y, halfWidth, halfHeight);
                    direction = UP;
                    drawHilbertCurve(order - 1, x, y + halfHeight, halfWidth, halfHeight);
                    direction = UP;
                    drawHilbertCurve(order - 1, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
                    direction = RIGHT;
                    drawHilbertCurve(order - 1, x + halfWidth, y, halfWidth, halfHeight);
                } else if (direction == RIGHT) {
                    direction = DOWN;
                    drawHilbertCurve(order - 1, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
                    direction = RIGHT;
                    drawHilbertCurve(order - 1, x, y + halfHeight, halfWidth, halfHeight);
                    direction = RIGHT;
                    drawHilbertCurve(order - 1, x, y, halfWidth, halfHeight);
                    direction = UP;
                    drawHilbertCurve(order - 1, x + halfWidth, y, halfWidth, halfHeight);
                } else if (direction == DOWN) {
                    direction = RIGHT;
                    drawHilbertCurve(order - 1, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
                    direction = DOWN;
                    drawHilbertCurve(order - 1, x + halfWidth, y, halfWidth, halfHeight);
                    direction = DOWN;
                    drawHilbertCurve(order - 1, x, y, halfWidth, halfHeight);
                    direction = LEFT;
                    drawHilbertCurve(order - 1, x, y + halfHeight, halfWidth, halfHeight);
                } else if (direction == LEFT) {
                    direction = UP;
                    drawHilbertCurve(order - 1, x, y, halfWidth, halfHeight);
                    direction = LEFT;
                    drawHilbertCurve(order - 1, x + halfWidth, y, halfWidth, halfHeight);
                    direction = LEFT;
                    drawHilbertCurve(order - 1, x + halfWidth, y + halfHeight, halfWidth, halfHeight);
                    direction = DOWN;
                    drawHilbertCurve(order - 1, x, y + halfHeight, halfWidth, halfHeight);
                }
            }
        }
    }
}

