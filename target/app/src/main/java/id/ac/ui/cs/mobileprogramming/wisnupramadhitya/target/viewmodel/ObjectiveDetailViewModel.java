package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.time.OffsetDateTime;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.databinding.SingleLiveEvent;

public class ObjectiveDetailViewModel extends ViewModel {

    private ObjectiveRepository mObjectiveRepository;

    @Nullable
    private Integer mProjectId;
    @Nullable
    private String mUserId;
    @Nullable
    private Integer mObjectiveId;

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> rational = new ObservableField<>();
    public final ObservableField<OffsetDateTime> deadline =  new ObservableField<>();
    public final SingleLiveEvent<Void> onObjectiveSavedEvent = new SingleLiveEvent<>();

    private final MediatorLiveData<ObjectiveWithKeyResults> mObjectiveLiveData = new MediatorLiveData<>();
    private boolean mIsNewObjectivevMode = false;

    public ObjectiveDetailViewModel(ObjectiveRepository objectiveRepository) {
        mObjectiveRepository = objectiveRepository;
        mObjectiveLiveData.observeForever(this::setupForm);
    }

    public void startNewMode(String userId, int projectId) {
        mUserId = userId;
        mProjectId = projectId;
        setupForm(null);
    }

    public void startUpdateMode(Integer objectiveId){
        LiveData<ObjectiveWithKeyResults> liveData = mObjectiveRepository.getObjective(objectiveId);
        mObjectiveLiveData.addSource(liveData,
                                     objectiveWithKeyResults -> mObjectiveLiveData.setValue(objectiveWithKeyResults));
    }

    public boolean isNewMode() {
        return mIsNewObjectivevMode;
    }

    public MediatorLiveData<ObjectiveWithKeyResults> getObjectiveLiveData() {
        return mObjectiveLiveData;
    }

    private void setupForm(@Nullable ObjectiveWithKeyResults objKeyResults) {
        if(objKeyResults != null) {
            // update mode
            mIsNewObjectivevMode = false;
            Objective obj = objKeyResults.getObjective();
            mUserId = obj.getOwnerId();
            mProjectId = obj.getProjectId();
            mObjectiveId = obj.getId();

            title.set(obj.getTitle());
            rational.set(obj.getRational());
            deadline.set(obj.getDeadline());
        } else {
            // create mode
            mIsNewObjectivevMode = true;
            title.set("");
            rational.set("");
            deadline.set(null);
        }
    }

    private void clearForm() {
        mUserId = null;
        mProjectId = null;
        mObjectiveId = null;
    }

    public void saveObjective(View view) {
        if(isNewMode()) {
            createObjective(mUserId, mProjectId,
                            title.get(), rational.get(), deadline.get());
        } else {
            updateObjective(mUserId, mProjectId, mObjectiveId,
                            title.get(), rational.get(), deadline.get());
        }
        onObjectiveSavedEvent.call();
    }

    public void createObjective(String userId, Integer projectId,
                                String title, String rational, OffsetDateTime deadline) {
        mObjectiveRepository.createObjective(userId, projectId, title, rational, deadline);
        clearForm();
    }

    public void updateObjective(String userId, Integer projectId, Integer objectiveId,
                                String title, String rational, OffsetDateTime deadline) {
        if(isNewMode())
            throw new RuntimeException("saveUpdateObjective() was called but objective is new");

        Objective objective = new Objective(objectiveId, projectId, userId,
                                            title, rational, deadline);
        mObjectiveRepository.updateObjective(objective);
    }

    public void deleteCurrentObjective(View view) {
        if(!isNewMode() && mObjectiveId != null) {
            mObjectiveRepository.deleteObjective(mObjectiveId);
        }
    }

    public void setDeadline(@Nullable OffsetDateTime newDeadline) {
        deadline.set(newDeadline);
        if(!isNewMode()) {
            updateObjective(mUserId, mProjectId, mObjectiveId,
                            title.get(), rational.get(), deadline.get());
        }
    }

    public void clearDeadline(View view) {
        setDeadline(null);
    }
}
