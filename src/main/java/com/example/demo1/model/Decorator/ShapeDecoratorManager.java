package com.example.demo1.model.Decorator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ShapeDecoratorManager {
    public boolean borderEnabled = true;
    public boolean fillEnabled = true;
    public boolean shadowEnabled = true;

    private Color fillColor = Color.LIGHTGRAY;
    private Color borderColor = Color.BLACK;
    private double borderWidth = 2.0;
    private Color shadowColor = Color.GRAY;
    private double shadowRadius = 4.0;

    public void setBorderEnabled(boolean enabled) { this.borderEnabled = enabled; }
    public void setFillEnabled(boolean enabled) { this.fillEnabled = enabled; }
    public void setShadowEnabled(boolean enabled) { this.shadowEnabled = enabled; }

    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }
    public void setBorderWidth(double borderWidth) { this.borderWidth = borderWidth; }
    public void setShadowColor(Color shadowColor) { this.shadowColor = shadowColor; }
    public void setShadowRadius(double shadowRadius) { this.shadowRadius = shadowRadius; }

    public Shape decorate(Shape shape) {
        Shape decorated = shape;
        if (borderEnabled) {
            decorated = new BorderDecorator(decorated, borderColor, borderWidth).getDecoratedShape();
        }
        if (fillEnabled) {
            decorated = new FillColorDecorator(decorated, fillColor).getDecoratedShape();
        }
        if (shadowEnabled) {
            decorated = new ShadowDecorator(decorated, shadowColor, shadowRadius).getDecoratedShape();
        }
        return decorated;
    }
}
