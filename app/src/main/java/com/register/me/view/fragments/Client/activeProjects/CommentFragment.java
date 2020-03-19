package com.register.me.view.fragments.Client.activeProjects;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.presenter.CommentPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 11-03-2020AM 11:02.
 */
public class CommentFragment extends BaseFragment implements IFragment, CommentPresenter.ICommentListener {

    @BindView(R.id.description_container)
    LinearLayout commentContainer;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @Inject
    CommentPresenter presenter;
    private static int projecAssignId;
    private static List<ViewActCompProject.Comment> mComment;

    public static IFragment newInstance(List<ViewActCompProject.Comment> comments, int id) {
        mComment = comments;
        projecAssignId = id;
        return new CommentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), projecAssignId,this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildUI();
    }

    private void buildUI() {
        if(mComment.size()!=0){
        commentContainer.removeAllViews();
        for (ViewActCompProject.Comment commentItem : mComment) {
            View inflater = presenter.setUpDescription(commentItem);
            commentContainer.addView(inflater);}
        }
    }


    @OnClick(R.id.addComment_main)
    public void onClickAddComment() {
        presenter.setCommentId(0);
        presenter.showAlert("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_screen;
    }

    @Override
    public String getFragmentName() {
        return "CommentFragment";
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Project Status");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mComment = null;
    }

    @Override
    public void showMessage(String message) {
        KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void updateUI(List<ViewActCompProject.Comment> commentList) {
        mComment.clear();
        mComment.addAll(commentList);
        buildUI();
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
    }
}
