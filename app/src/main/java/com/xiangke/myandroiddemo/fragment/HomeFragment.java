package com.xiangke.myandroiddemo.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.xiangke.base.mvp.BaseMvpFragment;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.download.HttpDownListener;
import com.xiangke.download.OkHttpDownUtil;
import com.xiangke.helper.DialogHelper;
import com.xiangke.listener.OnNeutralListener;
import com.xiangke.listener.OnPositiveListener;
import com.xiangke.myandroiddemo.R;
import com.xiangke.myandroiddemo.presenter.HomeContract;
import com.xiangke.myandroiddemo.presenter.HomePresenter;
import com.xiangke.utilcode.FileUtils;
import com.xiangke.utilcode.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Date:2022/8/2 13:49
 * @Author:dengjin
 * @Name:
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.home_btn)
    Button button;

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        super.initView();
        button.setOnClickListener(v -> setDialog());
    }

    @OnClick({
            R.id.home_btn_down
    })
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.home_btn_down) {
            ProgressDialog progressDialog = DialogHelper.createProgress(requireContext(), "正在下载数据，请勿关闭此页面");
            String url = "http://192.168.1.120:8082/sacw/center/app/storage/inventory/downloadDBTable";
            File filesDir = requireActivity().getFilesDir();
            String path = filesDir.getPath();
            LogUtils.i(TAG, "path = "+ path);
            File file = new File(filesDir, "abc.sql");
            FileUtils.createOrExistsFile(file);
            OkHttpDownUtil okHttpDownUtil = OkHttpDownUtil.getInstance();
            okHttpDownUtil.getDownRequest(progressDialog, url, file, new HttpDownListener() {
                @Override
                public void onDownloadSuccess(File file) {
                    progressDialog.dismiss();
                }

                @Override
                public void onDownloading(int progress) {
                    progressDialog.setProgress(progress);
                    LogUtils.i(TAG, "progress = "+ progress);
                }

                @Override
                public void onDownloadFailed(Exception e) {
                    progressDialog.dismiss();
                    LogUtils.e(TAG, "Exception = "+ e.getMessage());
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDialog(){
        DialogHelper.createPickDateDialog(getActivity(), (date, formatDate) -> {
            LogUtils.i(TAG, "选择的时间："+ date, " format时间："+ formatDate);
        }, dateTime -> {
            LogUtils.i(TAG, "现在的时间："+ dateTime);
        });
    }

    @Override
    public void requestSuccess() {

    }
}
