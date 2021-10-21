package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.view.View;

public interface Presenter {
    void attachView(View view);

    void detachView();

    void saveInstance(Bundle state);

    void restoreInstance(Bundle state);
}
