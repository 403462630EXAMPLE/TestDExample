package cn.hdmoney.hdy.fragment;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class PictureFragment extends BaseFragment {

    public static final String ARGUMENTS_IMAGE = "arguments_image";
    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initViews() {
        super.initViews();
        Glide.with(this).load(getArguments().get(ARGUMENTS_IMAGE)).into(photoView);
    }
}
