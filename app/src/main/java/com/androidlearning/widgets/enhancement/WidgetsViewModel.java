package com.androidlearning.widgets.enhancement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Using ViewModel and LiveData can significantly enhance your code, especially
 * for regarding the lifecycle-aware handling of UI updates and listeners.
 *
 * Why Use ViewModel and LiveData?
 *
 *     Lifecycle Awareness:
 *         ViewModel survives configuration changes like screen rotations, ensuring your data is not lost.
 *         LiveData automatically observes changes and updates the UI only when the activity is in the appropriate lifecycle state (e.g., RESUMED).
 *
 *     No Need for WeakReference:
 *         By moving state and logic into the ViewModel, you eliminate the need for manually managing weak references to the activity.
 *
 *     Cleaner Code:
 *         Separates UI logic (Activity) from data/state logic (ViewModel), adhering to the MVVM architecture.
 *
 *
 */
public class WidgetsViewModel extends ViewModel {

    private final MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private final MutableLiveData<Integer> progress = new MutableLiveData<>(0);

    // Expose LiveData for selectedDate
    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(int year, int month, int day) {
        // Month is 0-indexed, adjust to 1-indexed for display
        selectedDate.setValue(year + "-" + (month + 1) + "-" + day);
    }

    // Expose LiveData for progress
    public LiveData<Integer> getProgress() {
        return progress;
    }

    public void incrementProgress() {
        if (progress.getValue() != null) {
            int newProgress = (progress.getValue() + 10) % 100; // Increment by 10
            progress.setValue(newProgress);
        }
    }
}
