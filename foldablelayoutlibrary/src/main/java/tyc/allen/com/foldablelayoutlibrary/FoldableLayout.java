package tyc.allen.com.foldablelayoutlibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 *
 */
public class FoldableLayout extends FrameLayout {

    private boolean isFold = true;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private View foldView,unfoldView;

    public FoldableLayout(Context context) {
        super(context);
        init(null, 0, context);
    }

    public FoldableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, context);
    }

    public FoldableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle,context);
    }

    private void init(AttributeSet attrs, int defStyle,Context context) {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void init(){
        foldView = getChildAt(0);
        unfoldView = getChildAt(1);
        unfoldView.setAlpha(0);
//        unfoldView.setVisibility(View.GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

//        View view = LayoutInflater.from(getContext()).inflate(foldLayout,get);

    }

    private Boolean lock = false;

private long dur = 1000;
    private long diff = 100;
    public OnClickListener getFoldControlListener(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (lock){
                    if(lock)
                        return;
                }
                if(isFold){
                    ObjectAnimator animator = ObjectAnimator.ofFloat(unfoldView,"scaleY",0f,1f,1f);
                    animator.setDuration(dur);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(foldView,"alpha",1f,0f,0f);
                    animator1.setDuration(dur+diff);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(unfoldView,"alpha",0f,1f,1f);
                    animator2.setDuration(dur);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(animator).with(animator1).with(animator2);
                    animSet.start();
                }else{
                    ObjectAnimator animator = ObjectAnimator.ofFloat(unfoldView,"scaleY",1f,0f,0f);
                    animator.setDuration(dur);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(foldView,"alpha",0f,1f,1f);
                    animator1.setDuration(dur);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(unfoldView,"alpha",1f,0f,0f);
                    animator2.setDuration(dur+diff);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(animator).with(animator1).with(animator2);
                    animSet.start();
                }
                synchronized (lock){
                    lock = true;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(dur);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (lock){
                            lock = false;
                        }
                    }
                });
                thread.start();
                isFold = !isFold;
            }
        };
    }
}
