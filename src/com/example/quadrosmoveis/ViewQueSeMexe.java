package com.example.quadrosmoveis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ViewQueSeMexe extends View implements Runnable {

	private static final int MAX_SPEED = 5;
	private long time = 1;
	private float cx;
	private float cy;
	Bitmap monaLisa;

	private Paint paint;
	private int iy2;
	private int ix2;
	private int w;
	private int h;
	private int dx;
	private int dy;

	public ViewQueSeMexe(Context context) {
		super(context);

		paint = new Paint();
		paint.setColor(Color.BLACK); 
		paint.setTextSize(20); 
		
		randomDirection();

		try {
			// imagem esta na pasta assets !!!
			// 
			InputStream is = context.getAssets().open("quadros/monalisa.png");
			monaLisa = BitmapFactory.decodeStream(is);
			if (monaLisa != null) {
				ix2 = monaLisa.getWidth() / 2;
				iy2 = monaLisa.getHeight() / 2;
			}
		} catch (IOException e) {
			Log.e(MainActivity.TAG, "Erro carregando imagem");
		}

		Thread thread = new Thread(this);
		thread.start();
	}

	private void randomDirection() {
		Random r = new Random();
		dx = r.nextInt(MAX_SPEED) + 1;
		dy = r.nextInt(MAX_SPEED) + 1;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		this.w = getWidth();
		this.h = getHeight();
		cx = w / 2;
		cy = h / 2;
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);

		if (monaLisa != null) {
			canvas.drawBitmap(monaLisa, cx - ix2, cy - iy2, paint);
			canvas.drawText("Toque para mudar", 10, 30, paint);
			canvas.drawText("dx : " + dx , 10, 60, paint);			
			canvas.drawText("dy : " + dy , 10, 90, paint);
		} else {
			Log.e(MainActivity.TAG, "Alguem roubou a MONA LISA !!!");
		}
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				Log.e(MainActivity.TAG, "interrupcao do run()");
			}
			update();
			postInvalidate();
		}

	}

	private void update() {
		cx += dx;
		cy += dy;

		if (cx + ix2 > w || cx - ix2 < 0) {
			dx *= -1;
			cx += dx;
		}

		if (cy + iy2 > h || cy - iy2 < 0) {
			dy *= -1;
			cy += dy;
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			randomDirection();
		}
		return super.onTouchEvent(event);
	}

}
