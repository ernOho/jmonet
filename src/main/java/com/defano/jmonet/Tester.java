package com.defano.jmonet;

import com.defano.jmonet.canvas.JMonetCanvas;
import com.defano.jmonet.canvas.layer.ImageLayer;
import com.defano.jmonet.canvas.layer.ImageLayerSet;
import com.defano.jmonet.model.Interpolation;
import com.defano.jmonet.model.PaintToolType;
import com.defano.jmonet.tools.base.Tool;
import com.defano.jmonet.tools.builder.PaintToolBuilder;
import com.defano.jmonet.tools.builder.StrokeBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tester {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

//            BehaviorSubject<Boolean> drawCenteredObservable = BehaviorSubject.createDefault(true);
//
//            PaintToolBuilder.create(PaintToolType.RECTANGLE)
//                    .withDrawCenteredObservable(drawCenteredObservable)
//                    .makeActiveOnCanvas(myCanvas)
//                    .build();
//
//            JCheckBox drawCenteredCheckbox = new JCheckBox();
//            JCheckBoxMenuItem drawCenteredMenuItem = new JCheckBoxMenuItem();
//
//            drawCenteredObservable.subscribe(drawCentered -> drawCenteredMenuItem.setSelected(drawCentered));
//            drawCenteredMenuItem.addActionListener(e -> drawCenteredObservable.onNext(drawCenteredMenuItem.isSelected()));
//
//            drawCenteredObservable.subscribe(drawCentered -> drawCenteredCheckbox.setSelected(drawCentered));
//            drawCenteredCheckbox.addActionListener(a -> drawCenteredObservable.onNext(drawCenteredCheckbox.isSelected()));


            // Create and show Swing frame
            JFrame frame = new JFrame("My Pretty Picture");
            frame.setPreferredSize(new Dimension(600, 600));
            frame.setLayout(new FlowLayout());
            frame.pack();
            frame.setVisible(true);

            // Create a JMonet canvas and add it to the window
            JMonetCanvas myCanvas = new JMonetCanvas(new Dimension(600, 600));
//            myCanvas.setCanvasBackground(PaintFactory.makeCheckerboard(15, Color.WHITE, Color.GRAY));

            JScrollPane scroll = new JScrollPane();
            scroll.setPreferredSize(new Dimension(300, 300));
            scroll.setViewportView(myCanvas);

            myCanvas.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//            myCanvas.setScale(7.333333333);
//            myCanvas.setScanlineScaleThreadhold(100);

            frame.getContentPane().add(scroll);

            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = img.createGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 100, 100);
            g.dispose();

            myCanvas.commit(new ImageLayerSet(new ImageLayer(new Point(5, 5), img, AlphaComposite.SrcOver)));

            Tool activeTool = PaintToolBuilder.create(PaintToolType.AIRBRUSH)
                    .withShapeSides(3)
                    .withPathInterpolation(true)
                    .withCornerRadius(20)
                    .withIntensity(.5)
                    .withConstrainedAngle(45)
                    .withStroke(StrokeBuilder.withBasicStroke().ofWidth(16).build())
                    .withFillPaint(Color.BLUE)
                    .withStrokePaint(Color.RED)
                    .withAntiAliasing(Interpolation.NONE)
                    .makeActiveOnCanvas(myCanvas)
                    .build();

        });
    }

}
