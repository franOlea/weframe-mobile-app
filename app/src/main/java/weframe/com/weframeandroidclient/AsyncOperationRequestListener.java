package weframe.com.weframeandroidclient;

public interface AsyncOperationRequestListener {
    void onComplete(boolean success);
    void onError(Throwable throwable);
}
