package com.example.demo1.controller;

import com.example.demo1.model.command.*;
import com.example.demo1.model.observer.*;
import com.example.demo1.model.Decorator.*;
import com.example.demo1.model.graph.*;
import com.example.demo1.model.shapes.Factory.*;
import com.example.demo1.model.singleton.LoggerService;
import com.example.demo1.model.strategy.*;
import com.example.demo1.services.FileSaver;
import com.example.demo1.services.JavaFXFileSaver;
import com.example.demo1.utils.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import com.example.demo1.model.enums.DrawMode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DrawingController implements Observer {

    @FXML private Pane drawingPane;
    @FXML private Label helpLabel;
    @FXML private ListView<String> logListView;
    @FXML private ColorPicker colorPicker;

    private final Observable observable = new ObservableImpl();
    private final CommandManager commandManager = new CommandManager(this::addLog);
    private Consumer<String> logAction = this::addLog;
    private final DrawingConstraintStrategy constraintStrategy = new BoundedDrawingStrategy();

    private final FileSaver fileSaver = new JavaFXFileSaver(() -> {
        if (drawingPane != null && drawingPane.getScene() != null)
            return drawingPane.getScene().getWindow();
        return null;
    });

    private final ShapeDecoratorManager decoratorManager = new ShapeDecoratorManager();
    private final GraphManager graphManager = new GraphManager();

    private DrawMode currentMode = DrawMode.NONE;

    private double startX, startY;
    private Shape previewShape;

    private final ShapeFactoryRegistry factoryRegistry = new ShapeFactoryRegistry();


    @FXML
    public void initialize() {
        try {
            Connection conn = DBConnection.getConnection(); // Connexion centralisée
            LoggerService.getInstance().setLogStrategy(
                    new CombinedLogStrategy(List.of(
                            new ConsoleLogStrategy(),
                            new DBLogStrategy(conn)
                    ))
            );
        } catch (SQLException e) {
            System.err.println("⚠️ Impossible de se connecter à la base. Utilisation de la console uniquement.");
            LoggerService.getInstance().setLogStrategy(new ConsoleLogStrategy());
        }
        observable.addObserver(this);
        setHelpText("Bienvenue ! Dessinez une forme.");
        logListView.getItems().clear();
        setupDrawingEvents();

        if (colorPicker != null) {
            colorPicker.setValue(Color.LIGHTGRAY);
            decoratorManager.setFillColor(colorPicker.getValue());
            colorPicker.setOnAction(e -> {
                decoratorManager.setFillColor(colorPicker.getValue());
                logAction.accept("Couleur de remplissage sélectionnée : " + colorPicker.getValue());
            });
        }
    }

    @FXML
    void onExportLogs() {
        commandManager.execute(new ExportLogCommand(logListView, fileSaver, logAction));
    }

    @FXML
    void onShortestPath() {
        List<GraphNode> nodes = graphManager.getGraph().getNodes();
        if (nodes.size() < 2) {
            logAction.accept("Veuillez dessiner au moins deux nœuds pour trouver un chemin.");
            return;
        }

        // Reset node colors before calculating new path
        for (GraphNode node : nodes) {
            node.setFill(Color.LIGHTBLUE);
            if (node.getConnectionLine() != null) {
                node.getConnectionLine().setStroke(Color.BLACK);
            }
        }

        GraphNode start = nodes.get(0);
        GraphNode end = nodes.get(nodes.size() - 1);

        ShortestPathStrategy strategy = new DijkstraStrategy();
        List<GraphNode> path = strategy.findPath(graphManager.getGraph(), start, end);

        if (path != null && !path.isEmpty() && path.get(0) == start) {
            for (int i = 0; i < path.size(); i++) {
                GraphNode node = path.get(i);
                node.setFill(Color.GOLD); // Highlight node
                if (i < path.size() - 1) {
                    GraphNode nextNode = path.get(i + 1);
                    // Highlight the connection line between consecutive nodes
                    for (Map.Entry<GraphNode, Double> neighbor : node.getNeighbors().entrySet()) {
                        if (neighbor.getKey() == nextNode && node.getConnectionLine() != null) {
                            node.getConnectionLine().setStroke(Color.RED);
                            node.getConnectionLine().setStrokeWidth(2.0);
                        }
                    }
                }
            }
            logAction.accept("Plus court chemin trouvé entre le nœud " + start.getNodeId() + " et le nœud " + end.getNodeId() + ".");
        } else {
            logAction.accept("Aucun chemin trouvé entre le nœud " + start.getNodeId() + " et le nœud " + end.getNodeId() + ".");
        }
    }


    private void setupDrawingEvents() {
        drawingPane.setOnMousePressed(e -> {
            if (currentMode == DrawMode.NONE) return;
            startX = constraintStrategy.constrainX(e, drawingPane);
            startY = constraintStrategy.constrainY(e, drawingPane);
            if (startX >= 0 && startY >= 0) {
                previewShape = createPreviewShape();
                if (previewShape != null) drawingPane.getChildren().add(previewShape);
            }
        });

        drawingPane.setOnMouseDragged(e -> {
            if (previewShape != null) {
                double x = constraintStrategy.constrainX(e, drawingPane);
                double y = constraintStrategy.constrainY(e, drawingPane);
                if (x >= 0 && y >= 0) updatePreviewShape(x, y);
            }
        });

        drawingPane.setOnMouseReleased(e -> {
            if (previewShape != null) {
                double endX = constraintStrategy.constrainX(e, drawingPane);
                double endY = constraintStrategy.constrainY(e, drawingPane);
                if (endX >= 0 && endY >= 0) {
                    Shape shape = createFinalShape(startX, startY, endX, endY);
                    if (shape != null) {
                        shape = applyDecorators(shape);
                        drawingPane.getChildren().remove(previewShape);
                        commandManager.execute(new AddShapeCommand(drawingPane, shape, logAction));

                        String actionLog = "Forme créée : " + currentMode;
                        LoggerService.getInstance().log(actionLog);
                        observable.notifyObservers(actionLog != null ? actionLog : "Action sans message");
                        logAction.accept(actionLog);

                        if (currentMode == DrawMode.CIRCLE) {
                            Circle circle = (Circle) shape;
                            GraphNode node = graphManager.createNode(circle.getCenterX(), circle.getCenterY());
                        } else if (currentMode == DrawMode.LINE) {
                            connectLastTwoNodes((Line) shape);
                        }
                    }
                }
                previewShape = null;
            }
        });
    }

    private void connectLastTwoNodes(Line line) {
        List<GraphNode> nodes = graphManager.getGraph().getNodes();
        if (nodes.size() >= 2) {
            GraphNode n1 = nodes.get(nodes.size() - 2);
            GraphNode n2 = nodes.get(nodes.size() - 1);
            double weight = Math.hypot(n2.getCenterX() - n1.getCenterX(), n2.getCenterY() - n1.getCenterY());
            graphManager.connectNodes(n1, n2, weight);
            n1.setConnectionLine(n2, line); // Store the line in n1
            n2.setConnectionLine(n1, line); // Store the same line in n2
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(1.5);
            logAction.accept("Connexion créée entre le nœud " + n1.getNodeId() + " et le nœud " + n2.getNodeId() + ".");
        } else {
            logAction.accept("Erreur : Pas assez de nœuds pour créer une connexion.");
        }
    }

    private Shape createPreviewShape() {
        return switch (currentMode) {
            case RECTANGLE -> styledShape(new Rectangle(startX, startY, 0, 0));
            case CIRCLE -> styledShape(new Circle(startX, startY, 0));
            case LINE -> styledShape(new Line(startX, startY, startX, startY));
            case TRIANGLE -> styledShape(new Polygon(startX, startY, startX, startY, startX, startY));
            default -> null;
        };
    }

    private Shape styledShape(Shape s) {
        if (s != null) {
            s.setStroke(Color.BLUE);
            s.setFill(Color.TRANSPARENT);
        }
        return s;
    }

    private void updatePreviewShape(double x, double y) {
        if (previewShape == null) return;

        double paneWidth = drawingPane.getWidth();
        double paneHeight = drawingPane.getHeight();

        double endX = Math.max(0, Math.min(x, paneWidth));
        double endY = Math.max(0, Math.min(y, paneHeight));

        if (previewShape instanceof Rectangle rect) {
            double width = Math.abs(endX - startX);
            double height = Math.abs(endY - startY);
            double finalX = Math.min(startX, endX);
            double finalY = Math.min(startY, endY);

            width = Math.min(width, paneWidth - finalX);
            height = Math.min(height, paneHeight - finalY);

            rect.setX(finalX);
            rect.setY(finalY);
            rect.setWidth(width);
            rect.setHeight(height);

        } else if (previewShape instanceof Circle circ) {
            double dx = endX - startX;
            double dy = endY - startY;
            double radius = Math.min(Math.hypot(dx, dy),
                    Math.min(Math.min(startX, paneWidth - startX), Math.min(startY, paneHeight - startY)));
            circ.setCenterX(startX);
            circ.setCenterY(startY);
            circ.setRadius(radius);

        } else if (previewShape instanceof Line line) {
            line.setEndX(endX);
            line.setEndY(endY);

        } else if (previewShape instanceof Polygon poly) {
            double constrainedX = Math.max(0, Math.min(endX, paneWidth));
            double constrainedY = Math.max(0, Math.min(endY, paneHeight));

            double x1 = startX, y1 = startY;
            double x2 = constrainedX, y2 = constrainedY;
            double x3 = (x1 + x2) / 2, y3 = y2;

            poly.getPoints().setAll(x1, y1, x2, y2, x3, y3);
        }
    }

    private Shape createFinalShape(double startX, double startY, double endX, double endY) {
        double paneWidth = drawingPane.getWidth();
        double paneHeight = drawingPane.getHeight();

        ShapeFactory factory = factoryRegistry.getFactory(currentMode);
        return (factory != null)
                ? factory.createShape(startX, startY, endX, endY, paneWidth, paneHeight)
                : null;
    }


    private Shape applyDecorators(Shape shape) {
        return decoratorManager.decorate(shape);
    }

    @FXML void toggleBorder() {
        boolean newState = !decoratorManager.borderEnabled;
        decoratorManager.setBorderEnabled(newState);
        logAction.accept("Bordure " + (newState ? "activée" : "désactivée"));
    }

    @FXML void toggleFill() {
        boolean newState = !decoratorManager.fillEnabled;
        decoratorManager.setFillEnabled(newState);
        decoratorManager.setFillColor(colorPicker.getValue());
        logAction.accept("Remplissage " + (newState ? "activé" : "désactivé"));
    }

    @FXML void toggleShadow() {
        boolean newState = !decoratorManager.shadowEnabled;
        decoratorManager.setShadowEnabled(newState);
        logAction.accept("Ombre " + (newState ? "activée" : "désactivée"));
    }

    @FXML void onRectangle() { setMode(DrawMode.RECTANGLE); }
    @FXML void onCircle() { setMode(DrawMode.CIRCLE); }
    @FXML void onLine() { setMode(DrawMode.LINE); }
    @FXML void onTriangle() { setMode(DrawMode.TRIANGLE); }

    private void setMode(DrawMode mode) {
        currentMode = mode;
        if (previewShape != null) {
            drawingPane.getChildren().remove(previewShape);
            previewShape = null;
        }
        logAction.accept(mode + " activé.");
    }

    @FXML void onUndoBtn() { commandManager.undo(); }
    @FXML void onRedoBtn() { commandManager.redo(); }

    @FXML void onSaveBtn() {
        commandManager.execute(new SaveCommand(drawingPane, fileSaver, logAction));
    }

    @FXML void onShareBtn() { logAction.accept("Dessin partagé."); }
    @FXML void onOpen() { logAction.accept("Ouverture déclenchée."); }
    @FXML void onLogs() { logAction.accept("Affichage des logs."); }

    private void addLog(String message) {
        logListView.getItems().add(message);
        logListView.scrollTo(logListView.getItems().size() - 1);
        LoggerService.getInstance().log(message);
    }

    @Override
    public void update(Object arg) { setHelpText(arg != null ? arg.toString() : "Aucun message"); }
    private void setHelpText(String txt) { helpLabel.setText(txt); }
}
