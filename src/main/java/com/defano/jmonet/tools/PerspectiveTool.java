package com.defano.jmonet.tools;

import com.defano.jmonet.model.FlexQuadrilateral;
import com.defano.jmonet.model.PaintToolType;
import com.defano.jmonet.tools.base.AbstractTransformTool;
import com.defano.jmonet.algo.Transform;

import java.awt.*;

public class PerspectiveTool extends AbstractTransformTool {

    public PerspectiveTool() {
        super(PaintToolType.PERSPECTIVE);
    }

    @Override
    protected void moveTopLeft(FlexQuadrilateral quadrilateral, Point newPosition) {
        int bottomLeft = quadrilateral.getBottomLeft().y - (newPosition.y - quadrilateral.getTopLeft().y);

        quadrilateral.setBottomLeft(new Point(quadrilateral.getBottomLeft().x, bottomLeft));
        quadrilateral.setTopLeft(new Point(quadrilateral.getTopLeft().x, newPosition.y));

        setSelectedImage(Transform.project(getOriginalImage(), quadrilateral.translate(getSelectedImageLocation().x, getSelectedImageLocation().y)));
    }

    @Override
    protected void moveTopRight(FlexQuadrilateral quadrilateral, Point newPosition) {
        int bottomRight = quadrilateral.getBottomRight().y - (newPosition.y - quadrilateral.getTopRight().y);

        quadrilateral.setBottomRight(new Point(quadrilateral.getBottomRight().x, bottomRight));
        quadrilateral.setTopRight(new Point(quadrilateral.getTopRight().x, newPosition.y));

        setSelectedImage(Transform.project(getOriginalImage(), quadrilateral.translate(getSelectedImageLocation().x, getSelectedImageLocation().y)));
    }

    @Override
    protected void moveBottomLeft(FlexQuadrilateral quadrilateral, Point newPosition) {
        int topLeft = quadrilateral.getTopLeft().y - (newPosition.y - quadrilateral.getBottomLeft().y);

        quadrilateral.setTopLeft(new Point(quadrilateral.getTopLeft().x, topLeft));
        quadrilateral.setBottomLeft(new Point(quadrilateral.getBottomLeft().x, newPosition.y));

        setSelectedImage(Transform.project(getOriginalImage(), quadrilateral.translate(getSelectedImageLocation().x, getSelectedImageLocation().y)));
    }

    @Override
    protected void moveBottomRight(FlexQuadrilateral quadrilateral, Point newPosition) {
        int topRight = quadrilateral.getTopRight().y - (newPosition.y - quadrilateral.getBottomRight().y);

        quadrilateral.setTopRight(new Point(quadrilateral.getTopRight().x, topRight));
        quadrilateral.setBottomRight(new Point(quadrilateral.getBottomRight().x, newPosition.y));

        setSelectedImage(Transform.project(getOriginalImage(), quadrilateral.translate(getSelectedImageLocation().x, getSelectedImageLocation().y)));
    }

}
