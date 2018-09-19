package co.jestrada.cupoescolarapp.common.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import co.jestrada.cupoescolarapp.attendant.interactor.AttendantInteractor;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.location.interactor.RefPositionInteractor;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.presenter.BasePresenter;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.school.interactor.SchoolInteractor;
import co.jestrada.cupoescolarapp.school.interactor.SchoolListOrderedInteractor;
import co.jestrada.cupoescolarapp.school.model.bo.DistanceResponse;
import co.jestrada.cupoescolarapp.school.model.bo.Element;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;
import co.jestrada.cupoescolarapp.school.restapiservice.RestUtil;
import co.jestrada.cupoescolarapp.school.restapiservice.SchoolApiService;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter implements
        IMainContract.IMainPresenter{

    private IMainContract.IMainView mMainView;
    private Context mContext;

    private AttendantInteractor mAttendantInteractor;
    private SchoolInteractor mSchoolInteractor;
    private SchoolListOrderedInteractor mSchoolListOrderedInteractor;
    private RefPositionInteractor mRefPositionInteractor;

    private FirebaseAuth mFirebaseAuth;

    private AttendantBO attendantBO;

    List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS = new ArrayList<>();
    int index = 0;
    List<SchoolBO> schoolBOSTemp = new ArrayList<>();
    RefPositionBO refPositionBOTemp = new RefPositionBO();

    public MainPresenter(final Context mContext) {
        this.mMainView = (IMainContract.IMainView) mContext;
        this.mAttendantInteractor = new AttendantInteractor(
                null,
                null,
                null,
                this,
                null,
                null
        );
        this.mSchoolInteractor = new SchoolInteractor(
                this,
                null
        );
        this.mSchoolListOrderedInteractor = new SchoolListOrderedInteractor(
                this
        );
        this.mRefPositionInteractor = new RefPositionInteractor(
                this,
                null,
                null
        );
        this.mContext = mContext;
        mFirebaseAuth = FirebaseAuth.getInstance();

        attendantBO = AttendantBO.getInstance();
        if (!attendantBO.isOnSession()){
            signOut();
        }
    }


    @Override
    public void signOut() {
        attendantBO = AttendantBO.getInstance();
        attendantBO.setOnSession(false);
        mFirebaseAuth.signOut();
        mMainView.goToLogin();
    }

    public void getData(){
        mAttendantInteractor.getAttendant();
        mRefPositionInteractor.getRefPosition(attendantBO.getUserUid());
    }

    @Override
    public void getUser(boolean isChanged) {
        mMainView.setNavViewUI(isChanged);
    }

    @Override
    public void getAttendant(boolean isChanged) {
        mMainView.setNavViewUI(isChanged);
    }

    @Override
    public void getRefPosition(RefPositionBO refPositionBO, boolean isChanged) {
        if (!refPositionBO.isHasSchoolOrderedList()){
            refPositionBOTemp = refPositionBO;
            mSchoolInteractor.getSchools();
        } else {
            mSchoolListOrderedInteractor.getSchools();
        }
        mMainView.setRefPosition(refPositionBO, isChanged);
    }

    @Override
    public void getStudent(StudentBO studentBO, boolean isChanged) {

    }

    @Override
    public void getSchools(List<SchoolBO> schoolBOS, boolean isChanged) {
        if(isChanged){
            schoolBOSTemp.addAll(schoolBOS);
            schoolOrderedByRefPositionBOS.clear();
            getDistanceToRefPosition();
        }
    }

    @Override
    public void getSchoolsListOrdered(List<SchoolOrderedByRefPositionBO> schools,
                                      boolean isChanged) {
        if(isChanged){
            schoolOrderedByRefPositionBOS.clear();
            schoolOrderedByRefPositionBOS.addAll(schools);
            mMainView.getSchoolsListOrdered(schools, isChanged);
        }
    }

    private void getDistanceToRefPosition() {
            String units = "metric";
            String origins = String.valueOf(refPositionBOTemp.getLat()) + "," + String.valueOf(refPositionBOTemp.getLng());
            String destinations = schoolBOSTemp.get(index).getLat().toString() + "," + schoolBOSTemp.get(index).getLng().toString();
            String key = "AIzaSyDhUk5mUniWtL7nJsrQC2CtpeAFd9d1kGw";
            SchoolApiService client = RestUtil.getInstance().getRetrofit().create(SchoolApiService.class);

            Call<DistanceResponse> call = client.getDistance(units, origins, destinations, key);
            call.enqueue(new Callback<DistanceResponse>() {
                @Override
                public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                    if (response.body() != null &&
                            response.body().getRows() != null &&
                            response.body().getRows().size() > 0 &&
                            response.body().getRows().get(0) != null &&
                            response.body().getRows().get(0).getElements() != null &&
                            response.body().getRows().get(0).getElements().size() > 0 &&
                            response.body().getRows().get(0).getElements().get(0) != null &&
                            response.body().getRows().get(0).getElements().get(0).getDistance() != null &&
                            response.body().getRows().get(0).getElements().get(0).getDuration() != null) {

                        Element element = response.body().getRows().get(0).getElements().get(0);
                        SchoolOrderedByRefPositionBO schoolOrderedByRefPositionBO = new SchoolOrderedByRefPositionBO();
                        schoolOrderedByRefPositionBO.setSchoolCode(schoolBOSTemp.get(index).getCode());
                        schoolOrderedByRefPositionBO.setName(schoolBOSTemp.get(index).getName());
                        schoolOrderedByRefPositionBO.setLat(schoolBOSTemp.get(index).getLat());
                        schoolOrderedByRefPositionBO.setLng(schoolBOSTemp.get(index).getLng());
                        schoolOrderedByRefPositionBO.setDistanceText(element.getDistance().getText());
                        schoolOrderedByRefPositionBO.setDistanceValue(Double.valueOf(element.getDistance().getValue()));
                        schoolOrderedByRefPositionBO.setDurationText(element.getDuration().getText());
                        schoolOrderedByRefPositionBO.setDurationValue(Double.valueOf(element.getDuration().getValue()));
                        schoolOrderedByRefPositionBOS.add(schoolOrderedByRefPositionBO);
                        index = index + 1;
                        if (index == schoolBOSTemp.size() ) {
                            orderSchoolByDistance();
                        }else{
                            getDistanceToRefPosition();
                        }

                    }else {
                        index = index + 1;
                        if (index == schoolBOSTemp.size() ) {
                            orderSchoolByDistance();
                        }else{
                            getDistanceToRefPosition();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DistanceResponse> call, Throwable t) {
                    index = index + 1;
                    if (index == schoolBOSTemp.size() ) {
                        orderSchoolByDistance();
                    }else{
                        getDistanceToRefPosition();
                    }
                }
            });
    }

    private void orderSchoolByDistance() {
        Collections.sort(schoolOrderedByRefPositionBOS, new Comparator<SchoolOrderedByRefPositionBO>() {
            @Override
            public int compare(SchoolOrderedByRefPositionBO s1, SchoolOrderedByRefPositionBO s2) {
                return s1.getDistanceValue().compareTo(s2.getDistanceValue());
            }
            @Override
            public Comparator<SchoolOrderedByRefPositionBO> reversed() {
                return null;
            }
            @Override
            public Comparator<SchoolOrderedByRefPositionBO> thenComparing(Comparator<? super SchoolOrderedByRefPositionBO> other) {
                return null;
            }
            @Override
            public <U> Comparator<SchoolOrderedByRefPositionBO> thenComparing(Function<? super SchoolOrderedByRefPositionBO, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
                return null;
            }
            @Override
            public <U extends Comparable<? super U>> Comparator<SchoolOrderedByRefPositionBO> thenComparing(Function<? super SchoolOrderedByRefPositionBO, ? extends U> keyExtractor) {
                return null;
            }
            @Override
            public Comparator<SchoolOrderedByRefPositionBO> thenComparingInt(ToIntFunction<? super SchoolOrderedByRefPositionBO> keyExtractor) {
                return null;
            }
            @Override
            public Comparator<SchoolOrderedByRefPositionBO> thenComparingLong(ToLongFunction<? super SchoolOrderedByRefPositionBO> keyExtractor) {
                return null;
            }
            @Override
            public Comparator<SchoolOrderedByRefPositionBO> thenComparingDouble(ToDoubleFunction<? super SchoolOrderedByRefPositionBO> keyExtractor) {
                return null;
            }
        });

        for (int index2 = 0; index2 < schoolOrderedByRefPositionBOS.size(); index2++) {
            schoolOrderedByRefPositionBOS.get(index2).setPosition(index2 + 1);
        }

        mSchoolListOrderedInteractor.saveSchools(schoolOrderedByRefPositionBOS);
        refPositionBOTemp.setHasSchoolOrderedList(true);
        mRefPositionInteractor.saveRefPositionNoDescription(refPositionBOTemp);
    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {
        mMainView.getRefPositionTransactionState(successful);
    }

    @Override
    public void getAttendantTransactionState(boolean successful) {
        mMainView.getAttendantTransactionState(successful);
    }

    @Override
    public void getStudentTransactionState(boolean successful) {

    }

}
