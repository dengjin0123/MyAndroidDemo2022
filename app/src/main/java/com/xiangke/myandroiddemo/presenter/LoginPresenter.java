package com.xiangke.myandroiddemo.presenter;

import com.google.gson.JsonObject;
import com.xiangke.base.mvp.presenter.BasePresenter;
import com.xiangke.http.BaseResponse;
import com.xiangke.http.errorhandler.ExceptionHandle;
import com.xiangke.myandroiddemo.bean.LoginBean;
import com.xiangke.myandroiddemo.http.LoginApiManager;
import com.xiangke.http.observer.BaseObserver;
import com.xiangke.myandroiddemo.bean.UserInfoBean;
import com.xiangke.myandroiddemo.constant.Constants;
import com.xiangke.utilcode.EncryptUtils;
import com.xiangke.utilcode.LogUtils;
import com.xiangke.utilcode.SPUtils;
import com.xiangke.utilcode.ToastUtils;

/**
 * @Date:2022/8/9 9:56
 * @Author:dengjin
 * @Name:
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.presenter{
    private static final String TAG = "LoginPresenter";
    @Override
    public void toLogin(String name, String psw) {
//        String url = "sacw/center/app/loginByDevice";

        String pswErp = EncryptUtils.encryptMD5ToString(psw).toLowerCase();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty(Constants.LOGIN_USERNAME, name);
        jsonObject1.addProperty(Constants.LOGIN_PASSWORD, psw);
        jsonObject1.addProperty(Constants.LOGIN_MAC, "40:C8:1F:A2:DF:27");
        String s1 = jsonObject1.toString();

        LoginApiManager.requestLogin(mView.getInstance(), s1, new BaseObserver<LoginBean>() {
            @Override
            public void onSuccess(BaseResponse<LoginBean> tBaseReponse) {
                int code = tBaseReponse.getRet();
                LoginBean obj = tBaseReponse.getObj();
                String satoken = obj.getSatoken();
                SPUtils.getInstance().put(Constants.TOKEN, satoken);
            }

            @Override
            public void onCodeError(BaseResponse<LoginBean> tBaseReponse) {
                int code = tBaseReponse.getRet();
                LogUtils.i(TAG, "onCodeError code = "+ code);
            }

            @Override
            public void onFailure(ExceptionHandle.ResponseThrowable throwable) {
                LogUtils.i(TAG, "e = "+ throwable.getMessage());
                ToastUtils.showShort(throwable.message);
            }
        });

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(Constants.LOGIN_USERNAME, name);
//            jsonObject.put(Constants.LOGIN_PASSWORD, pswErp);
//            jsonObject.put(Constants.LOGIN_MAC, "40:C8:1F:A2:DF:27");
//            String s = jsonObject.toString();
//            RequestHelper.getInstance().post(mView.getInstance(), url, s, new BaseObserver<JsonObject>() {
//                @Override
//
//                public void onSuccess(BaseResponse<JsonObject> tBaseReponse) {
//                    JsonObject res = tBaseReponse.getRes();
//                }
//
//                @Override
//                public void onCodeError(BaseResponse<JsonObject> tBaseReponse) {
//                    LogUtils.i(TAG, "code = "+ tBaseReponse.getCode());
//                }
//
//                @Override
//                public void onFailure(Throwable e, boolean netWork) throws Exception {
//                    LogUtils.i(TAG, "e = "+ e.getMessage());
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
