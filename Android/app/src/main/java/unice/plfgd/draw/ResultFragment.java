package unice.plfgd.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import unice.plfgd.R;
import unice.plfgd.common.data.Draw;
import unice.plfgd.home.HomeActivity;
import unice.plfgd.tool.Connexion;

import java.util.Objects;

public class ResultFragment extends Fragment implements ResultContract.View{

    public ResultFragment(){

    }

    public static ResultFragment newInstance(){
        return new ResultFragment();
    }

    private Button mReturn;
    private Draw mResult;
    private TextView mResponse;
    private TextView mComment;
    private DrawCanvas mCanvas;
    private Button mReplay;

    private ResultContract.Presenter mPresenter;

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSocketReset(Connexion.ResetSocketMessage message) {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
           mResult = (Draw) getArguments().getSerializable("draw");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment,container,false);

        mReturn = view.findViewById(R.id.result_back);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.back();
            }
        });
        mReplay = view.findViewById(R.id.result_replay);
        mReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.replay();
            }
        });

        mResponse = view.findViewById(R.id.result_text);
        mResponse.setText(R.string.good_job);
        mComment = view.findViewById(R.id.result_comment);
        mComment.setText(R.string.good_comment);

        mCanvas = view.findViewById(R.id.result_canvas);
        mPresenter.showResult(mResult);
        mCanvas.setActive(false);

        return view;
    }

    @Override
    public void back(){
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    public void drawResult(Draw draw){
        mCanvas.drawResult(draw);
    }

    @Override
    public void replay() {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

        DrawFragment fragment = DrawFragment.newInstance();
        DrawPresenter presenter = new DrawPresenter(fragment);

        ((DrawActivity) getActivity()).setPresenter(presenter);

        transaction.replace(R.id.contentFrame,fragment);
        transaction.commit();
    }
}