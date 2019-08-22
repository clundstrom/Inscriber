package se.umu.chlu0125.inscriber.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 13/08/2019
 * <p>
 * Description: Guide Activity. Is started from GuideDialogFragment and is used to
 * explain to the user how the application works in a nutshell.
 * Uses Glide API and a custom library called
 * Android-Image-Slider @link https://github.com/smarteist/Android-Image-Slider
 */
public class GuideActivity extends AppCompatActivity {

    private static final String TAG = "GuideActivity";
    private Button mGotIt;
    private int[] mGuideImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_slideshow);
        mGotIt = findViewById(R.id.btn_guide_gotit);
        mGuideImages = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};

        mGotIt.setOnClickListener((click) -> finish());

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setAutoCycle(false);
        sliderView.setIndicatorSelectedColor(Color.parseColor("#1B5E20")); // primary theme colour
        sliderView.setIndicatorUnselectedColor(Color.GRAY);

    }

    /**
     * Custom adapter for ImageSlider in Guide Activity.
     */
    public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

        private Context context;

        public SliderAdapter(Context context) {
            this.context = context;
        }

        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_guide_slideshow, null);
            return new SliderAdapterVH(inflate);
        }

        /**
         * Binds each Image for the ViewHolders. Also crops and scales images using Glide API.
         * Uses some customized boilerplate code from example from ImageSlider lib.
         */
        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

            switch (position) {
                case 0:
                    Glide.with(viewHolder.itemView)
                            .load(mGuideImages[position])
                            .override(800, 800)
                            .centerCrop()
                            .into(viewHolder.imageViewBackground);
                    break;
                case 1:
                    Glide.with(viewHolder.itemView)
                            .load(mGuideImages[position])
                            .override(800, 800)
                            .centerCrop()
                            .into(viewHolder.imageViewBackground);
                    break;
                default:
                    Glide.with(viewHolder.itemView)
                            .load(mGuideImages[position])
                            .override(1000, 800)
                            .centerCrop()
                            .into(viewHolder.imageViewBackground);
                    break;

            }

        }

        @Override
        public int getCount() {
            return mGuideImages.length;
        }

        class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageViewBackground;

            public SliderAdapterVH(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.guide_image);
                this.itemView = itemView;
            }
        }
    }
}
