package com.defano.jmonet.tools.base;

import com.defano.jmonet.canvas.Scratch;
import com.defano.jmonet.canvas.observable.SurfaceInteractionObserver;
import com.defano.jmonet.model.PaintToolType;
import com.defano.jmonet.tools.util.Geometry;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class BoundsTool extends BasicTool implements SurfaceInteractionObserver {

    private Point initialPoint;
    private Point currentPoint;

    public BoundsTool(PaintToolType type) {
        super(type);
    }

    /**
     * Draws the stroke (outline) of a shape described by a rectangular boundary.
     *
     * @param scratch The scratch buffer on which to draw
     * @param stroke The stroke with which to draw
     * @param paint The paint with which to draw
     * @param bounds The bounds of the shape to draw
     * @param isShiftDown True to indicate that the user is holding the shift key; implementers may use this flag to
     *                    constrain the bounds or otherwise modify the tool behavior.
     */
    public abstract void strokeBounds(Scratch scratch, Stroke stroke, Paint paint, Rectangle bounds, boolean isShiftDown);

    /**
     * Fills a shape described by a rectangular boundary.
     *
     * @param scratch The scratch buffer on which to draw
     * @param fill The paint with which to fill the shape
     * @param bounds The bounds of the shape to draw
     * @param isShiftDown True to indicate that the user is holding the shift key; implementers may use this flag to
     *                    constrain the bounds or otherwise modify the tool behavior.
     */
    public abstract void fillBounds(Scratch scratch, Paint fill, Rectangle bounds, boolean isShiftDown);

    /** {@inheritDoc} */
    @Override
    public Cursor getDefaultCursor() {
        return new Cursor(Cursor.CROSSHAIR_CURSOR);
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(MouseEvent e, Point imageLocation) {
        initialPoint = imageLocation;
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(MouseEvent e, Point imageLocation) {
        currentPoint = imageLocation;

        if (!getAttributes().isDrawMultiple()) {
            getScratch().clear();
        }

        Point originPoint = new Point(initialPoint);

        if (getAttributes().isDrawCentered()) {
            int height = currentPoint.y - initialPoint.y;
            int width = currentPoint.x - initialPoint.x;

            originPoint.x = initialPoint.x - width / 2;
            originPoint.y = initialPoint.y - height / 2;
        }

        Rectangle bounds = e.isShiftDown() ?
                Geometry.square(originPoint, currentPoint) :
                Geometry.rectangle(originPoint, currentPoint);

        getAttributes().getFillPaint().ifPresent(paint ->
                fillBounds(getScratch(), paint, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), e.isShiftDown()));

        strokeBounds(getScratch(), getAttributes().getStroke(), getAttributes().getStrokePaint(), new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), e.isShiftDown());
        getCanvas().repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased(MouseEvent e, Point imageLocation) {
        getCanvas().commit();
    }

    /** {@inheritDoc} */
    @Override
    public void mouseMoved(MouseEvent e, Point imageLocation) {
        setToolCursor(getToolCursor());
    }

    public Point getInitialPoint() {
        return initialPoint;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }
}
