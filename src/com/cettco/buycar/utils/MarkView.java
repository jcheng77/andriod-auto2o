package com.cettco.buycar.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cettco.buycar.R;

public class MarkView extends FrameLayout {
	private Button[] starButton;
	private int level;

	public static final int LEVEL_ZERO = 0;
	public static final int LEVEL_HATE = 1;
	public static final int LEVEL_DISLIKE = 2;
	public static final int LEVEL_NORMAL = 3;
	public static final int LEVEL_GOOD = 4;
	public static final int LEVEL_GREAT = 5;

	public MarkView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.star, this);

		level = 0;
		starButton = new Button[5];
		starButton[0] = (Button) findViewById(R.id.star0Button);
		starButton[1] = (Button) findViewById(R.id.star1Button);
		starButton[2] = (Button) findViewById(R.id.star2Button);
		starButton[3] = (Button) findViewById(R.id.star3Button);
		starButton[4] = (Button) findViewById(R.id.star4Button);
		setStar(0, false);

		for (int i = 0; i < 5; ++i) {
			final int f = i;
			starButton[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setStar(f + 1, true);
					level = f + 1;
				}
			});
		}
	}

	private void setStar(int n, boolean withAnimation) {
		for (int i = 0; i < 5; ++i) {
			if (i < n) {
				if (withAnimation) {
					Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
							1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);

					animation.setDuration(300 + 100 * i);
					starButton[i].startAnimation(animation);
				}
				starButton[i].setBackgroundResource(R.drawable.star_on);
			} else {
				starButton[i].setBackgroundResource(R.drawable.star_off);
			}
		}
	}

	public void setClick(boolean clickable) {
		for (int i = 0; i < 5; ++i) {
			starButton[i].setClickable(clickable);
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		setStar(level, false);
	}
}
