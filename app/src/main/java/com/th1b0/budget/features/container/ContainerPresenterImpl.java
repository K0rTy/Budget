package com.th1b0.budget.features.container;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class ContainerPresenterImpl extends PresenterImpl<ContainerView>
    implements ContainerPresenter {

  ContainerPresenterImpl(@NonNull ContainerView view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void loadContainers() {
    mSubscription.add(mDataManager.getContainers()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(containers -> {
          if (isViewAttached()) {
            getView().onContainersLoaded(containers);
          }}, error -> {
          if (isViewAttached()) {
            getView().onError(error.getMessage());
          }
        }));
  }

  @Override public void deleteContainer(@NonNull Container container) {
    mSubscription.add(mDataManager.deleteContainer(container)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe());
  }
}