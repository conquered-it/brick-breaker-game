package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;

public class Grid extends JPanel implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private boolean play = false, pause = false;
	 private Timer Timer;
	 private int xOffset, yOffset, brickRows, brickColumns, brickLength, brickHeight, frameXsize,
	 			frameYsize, score, xOff, boardLevel, ballDiameter, totalBricks, boardPosition,
	 			ballXpos, ballYpos, ballXspeed, ballYspeed, oldBallXspeed, oldBallYspeed, 
	 			boardSpeed, boardLength, boardHeight, bricks[][];

	 long leftMoveTime = 0, rightMoveTime = 0;
	 
	 public void initializeGame() {
	        boardLength = 120;
	        boardLevel = 500;
	        boardHeight = 10;
	        boardSpeed = 20;
	        ballDiameter = 20;
	        xOff = 17;
	        ballXpos = (frameXsize-xOff)/2;
	        ballYpos = boardLevel-ballDiameter;
	        List<Integer> speedXList = Arrays.asList(-2,-1,1,2);
	        Random rand = new Random();
	        ballXspeed = speedXList.get(rand.nextInt(speedXList.size()));
	        ballYspeed = -2;
	        
	        score = 0;
	        boardPosition = (frameXsize-xOff-boardLength)/2;
	        totalBricks = brickRows*brickColumns;
	        xOffset = 30;
	        yOffset = (frameXsize-xOff-brickColumns*brickLength)/2;
	        for(int i=0; i<brickRows; ++i) {
				for(int j=0; j<brickColumns; ++j) {
					this.bricks[i][j]=1;
				}
			}
		}

	public Grid(int brickRows, int brickColumns, int brickLength, int brickHeight, int frameXsize, int frameYsize) {
		this.brickLength = brickLength;
		this.brickHeight = brickHeight;
		this.brickRows = brickRows;
		this.brickColumns = brickColumns;
		this.frameXsize = frameXsize;
		this.frameYsize = frameYsize;
		this.bricks = new int[brickRows][brickColumns];
		initializeGame();
		
		addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(10, (ActionListener) this);
        Timer.start();
	}
	
	public void fillBricks(Graphics2D g) {
		for(int i=0; i<brickRows; ++i) {
			for(int j=0; j<brickColumns; ++j) {
				if(bricks[i][j]>0){
					g.setColor(Color.orange);
					g.fillRect(j * brickLength + yOffset, i * brickHeight + xOffset, brickLength, brickHeight);

					g.setStroke(new BasicStroke(2));
					g.setColor(Color.black);
					g.drawRect(j * brickLength + yOffset, i * brickHeight + xOffset, brickLength, brickHeight);
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, frameXsize, frameYsize);

		g.setColor(Color.green);
        g.fillRect(0, 0, 2, frameYsize);
        g.fillRect(0, 0, frameXsize, 2);
        g.fillRect(frameXsize-xOff, 0, 2, frameYsize);
        g.fillRect(0, frameYsize-40, frameXsize, 2);
        
        g.setColor(Color.cyan);
        g.fillRect(boardPosition, boardLevel, boardLength, boardHeight);
        
        g.setColor(Color.magenta);
        g.fillOval(ballXpos, ballYpos, ballDiameter, ballDiameter);
        
        this.fillBricks((Graphics2D) g);
        
        g.setColor(Color.white);
        g.drawString("score: " + score, 730, 20);
        if(play) {
        	if(!pause) {
        		g.drawString("Press spacebar to pause the game", 587, 550);
        	}
        	else {
        		g.drawString("Press spacebar to resume the game", 580, 550);
        	}
        }
        
        if (ballYpos > frameYsize) {
            play = false;
            ballXspeed = 0;
            ballYspeed = 0;
            g.setColor(Color.red);
            g.drawString("Game Over,  Score: " + score, 190, 300);
            g.drawString("Press Enter to restart", 190, 340);
        }
        if(totalBricks == 0){
            play = false;
            ballYspeed = 0;
            ballXspeed = 0;
            g.setColor(Color.red);
            g.drawString("You won!! Score: "+score,190,300);
            g.drawString("Press Enter to restart", 190, 340);


        }
        
        if(!play && totalBricks>0 && ballYpos<frameYsize) {
        	g.setColor(Color.red);
            g.drawString("Press Enter to start the game", 190, 340);
        }

        g.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	if(!pause) {
	        	if(boardPosition<frameXsize-xOff-boardLength) {
	        		rightMoveTime = System.currentTimeMillis();
	        	}
	        	boardPosition=Math.min(boardPosition+boardSpeed, frameXsize-xOff-boardLength);
        	}
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(!pause) {
	        	if(boardPosition>0) {
	        		leftMoveTime = System.currentTimeMillis();
	        	}
	        	boardPosition = Math.max(boardPosition-boardSpeed, 0);
        	}
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                initializeGame();
                repaint();
                play=true;
                pause=false;
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        	if(play) {
        		if(pause) {
        			ballXspeed = oldBallXspeed;
        			ballYspeed = oldBallYspeed;
        		}
        		else {
        			oldBallXspeed = ballXspeed;
        			oldBallYspeed = ballYspeed;
        			ballXspeed = 0;
        			ballYspeed = 0;
        		}
        		pause = !pause;
        	}
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Timer.start();

        if (play) {
        	Rectangle ball = new Rectangle(ballXpos, ballYpos, ballDiameter, ballDiameter);
            Rectangle board = new Rectangle(boardPosition, boardLevel, boardLength, boardHeight);
            
            if (ball.intersects(board)) {
                ballYspeed = -ballYspeed;
                if(System.currentTimeMillis() - leftMoveTime <= 100) {
                	ballXspeed=Math.max(ballXspeed-2,-5);
                }
                if(System.currentTimeMillis() - rightMoveTime <= 100) {
                	ballXspeed=Math.min(ballXspeed+2, 5);
                }
            }

            for (int i = 0; i < brickRows; ++i) {
                for (int j = 0; j < brickColumns; ++j) {
                    if (bricks[i][j] > 0) {
                        int brickX = j * brickLength + yOffset;
                        int brickY = i * brickHeight + xOffset;

                        Rectangle brick = new Rectangle(brickX, brickY, brickLength, brickHeight);

                        if (ball.intersects(brick)) {
                            bricks[i][j]=0;
                            --totalBricks;
                            ++score;
                            if(ballYpos+ballDiameter-1 <= brickY || ballYpos + 1 >= brickY) {
                            	ballYspeed = - ballYspeed;
                            } else {
                                ballXspeed = -ballXspeed;
                            }
                        }
                    }


                }
            }
            

            ballXpos += ballXspeed;
            ballYpos += ballYspeed;
            if (ballXpos < 0) {
                ballXspeed = -ballXspeed;
            }
            if (ballYpos < 0) {
                ballYspeed = -ballYspeed;
            }
            if (ballXpos > frameXsize-xOff-ballDiameter) {
                ballXspeed = -ballXspeed;
            }
      
        }
        repaint();
		// TODO Auto-generated method stub
		
	}
}
