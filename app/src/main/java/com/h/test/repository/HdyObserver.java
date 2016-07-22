package cn.hdmoney.hdy.repository;

import rx.Observer;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public abstract class HdyObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {}

    @Override
    public final void onError(Throwable e) {
        try {
            onThrowable(e);
        } catch (Throwable e1){
            e1.printStackTrace();
        }
    }

    public abstract void onThrowable(Throwable e);

    @Override
    public abstract void onNext(T t);
}
