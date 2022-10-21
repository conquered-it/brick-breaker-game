package Game;

import javax.swing.JFrame;


public class Main {
	public static void main(String[] args) {
		int frameXsize = 800, frameYsize = 600, brickRows = 4, brickColumns = 5, brickLength = 60, brickHeight = 40;
        JFrame obj = new JFrame();
        Grid grid = new Grid(brickRows, brickColumns, brickLength, brickHeight, frameXsize, frameYsize);
        obj.setBounds(225,75,frameXsize,frameYsize);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.add(grid);
    }
}
