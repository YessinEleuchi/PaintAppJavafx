package com.example.demo1.model.shapes.Factory;

import com.example.demo1.model.enums.DrawMode;
import java.util.EnumMap;
import java.util.Map;

public class ShapeFactoryRegistry {
    private final Map<DrawMode, ShapeFactory> factoryMap = new EnumMap<>(DrawMode.class);

    public ShapeFactoryRegistry() {
        factoryMap.put(DrawMode.RECTANGLE, new RectangleFactory());
        factoryMap.put(DrawMode.CIRCLE, new CircleFactory());
        factoryMap.put(DrawMode.LINE, new LigneFactory());
        factoryMap.put(DrawMode.TRIANGLE, new TriangleFactory());
        factoryMap.put(DrawMode.FLESH, new FleshFactory());
    }

    public ShapeFactory getFactory(DrawMode mode) {
        return factoryMap.getOrDefault(mode, null);
    }
}
