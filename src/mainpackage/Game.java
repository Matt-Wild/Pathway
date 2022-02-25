package mainpackage;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
	
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	private Display display;
	private ImageLoader imageLoader;
	private Player player;
	private KeyManager keyManager;
	private Ground ground;
	private Background background;
	private SoundObject backgroundSound;
	private WorldManager worldManager;
	
	private int width, height;
	private String title;
	private Thread thread;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init() {
		this.keyManager = new KeyManager();
		
		this.display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		
		this.imageLoader = new ImageLoader();
		imageLoader.init();
		
		worldManager = new WorldManager(imageLoader);
		
		this.backgroundSound = new SoundObject("backgroundAmbience.wav");
		backgroundSound.setVolume(0.2);
		backgroundSound.loop();
		
		player = new Player(keyManager, display, imageLoader, worldManager);
		
		this.ground = new Ground(imageLoader);
		this.background = new Background(imageLoader);
	}
	
	private void update() {
		player.update();
		worldManager.update(player);
		background.update(height);
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		this.width = display.getFrameWidth();
		this.height = display.getFrameHeight();
		
		g.clearRect(0, 0, width, height);	// CLEARING WINDOW
		
		// START DRAWING
		
		background.draw(g, player, width);
		
		ground.draw(g, player, width, height);
		
		player.draw(g);
		
		worldManager.draw(g, player);
		
		// STOP DRAWING
		
		bs.show();
		g.dispose();
	}

	public void run() {
		init();
		
		
		int fps = 120;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		
		long now;
		long lastTime = System.nanoTime();
		
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = System.nanoTime();
			
			if(delta >= 1) {
				runLoop();
				delta--;
			}
		}
		
		stop();
	}	
	
	public void runLoop() {
		update();
		render();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
