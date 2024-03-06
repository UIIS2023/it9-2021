package strategy;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class OpenPainting implements OpenAndSave {

    private int currentIndex;

    @Override
    public void save(Object o, File file) {
    }

    @Override
    public void open(Object c, Object m, Object f, File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        DrawingFrame frame = (DrawingFrame) f;
        DrawingController controller = (DrawingController) c;
        DrawingModel model = (DrawingModel) m;
        PaintingDrawer drawer = new PaintingDrawer(controller, model, frame);

        controller.getUndoStack().clear();
        model.getShapes().clear();
        frame.getDlm().clear();
        frame.getBtnUndo().setEnabled(false);
        frame.getBtnRedo().setEnabled(false);
        controller.getSelectedList().clear();
        File toOpen = new File(file.getAbsolutePath().replaceAll(".bin", ".txt"));
        FileReader fr = new FileReader(toOpen);
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<String> shapesToDraw = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            shapesToDraw.add(line);
        }
        int delayBetweenShapes = 1000;

        currentIndex = 0;
        frame.getBtnNextCommand().setEnabled(true);
        frame.getBtnNextCommand().addActionListener(e -> executeNextCommand(shapesToDraw, drawer, frame, delayBetweenShapes));

        br.close();

        for (int i = 0; i < model.getShapes().size(); i++) {
            if (model.getShapes().get(i).isSelected()) {
                controller.getSelectedList().add(model.getShapes().get(i));
            }
        }
        frame.repaint();
    }

    private void drawShape(String shape, PaintingDrawer drawer, DrawingFrame frame) {
        drawer.paint(shape);
        frame.getDlm().addElement(shape + '\n');
    }

    private void executeNextCommand(List<String> shapes, PaintingDrawer drawer, DrawingFrame frame, int delay) {
        if (currentIndex < shapes.size()) {
            String shape = shapes.get(currentIndex);
            drawShape(shape, drawer, frame);

            currentIndex++;

            if (currentIndex < shapes.size()) {
                frame.getBtnNextCommand().setEnabled(true);
            } else {
                frame.getBtnNextCommand().setEnabled(false);
            }
        }
    }
}
