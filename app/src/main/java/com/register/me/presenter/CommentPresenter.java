package com.register.me.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.PostReply;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 11-03-2020PM 12:50.
 */
public class CommentPresenter implements Utils.UtilAlertInterface, ClientNetworkCall.NetworkCallInterface {
    Context context;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    private ICommentListener listener;
    private int commentId = 0;
    private int proAssignId;

    public void init(Context context, int id, ICommentListener listener) {
        this.context = context;
        this.listener = listener;
        proAssignId = id;
        ((BaseActivity) context).injector().inject(this);
    }

    public void showAlert(String commentTopic) {
        utils.setCommentTopic(commentTopic);
        utils.showAlert(context, 10, this);
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$ALERT$")) {
            listener.showMessage("Please fill all the fields");
        } else {
            String[] data = success.split(":");
            JsonObject object = getJsonObject(data);
            apiCall(object);

        }
    }

    private void apiCall(JsonObject object) {
        if (utils.isOnline(context)) {
            listener.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            if (object != null) {
                networkCall.postReply(apiInterface, token, object, this);
            } else {
                String id = constants.getProjectID();
                networkCall.getACDetails(apiInterface, token, id, this);
            }
        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @NotNull
    private JsonObject getJsonObject(String[] data) {
        JsonObject object = new JsonObject();
        object.addProperty("projectassignid", proAssignId);
        object.addProperty("topic", data[0]);
        object.addProperty("content", data[1]);
        object.addProperty("commentid", getCommentId());
        return object;
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof PostReply) {
            if (((PostReply) response).getData().getMessage().equals("Posted Successfully")) {
                apiCall(null);
            }
        } else if (response instanceof ViewActCompProject) {
            List<ViewActCompProject.Comment> commentList = ((ViewActCompProject) response).getData().getComments();
            listener.updateUI(commentList);
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);

    }


    public View setUpDescription(ViewActCompProject.Comment commentItem) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.comment_item, null, false);
        TextView commentTopic = inflater.findViewById(R.id.commentTopic);
        TextView commentDate = inflater.findViewById(R.id.commentDate);
        TextView txtCreatedby = inflater.findViewById(R.id.txt_createdBy);
        TextView description = inflater.findViewById(R.id.description);
        View containerView = inflater.findViewById(R.id.headerView);
        LinearLayout subContainer = inflater.findViewById(R.id.subDescription_container);
        CardView addComment = inflater.findViewById(R.id.addComment);


        commentTopic.setText(commentItem.getCommentTopic());
        commentDate.setText(commentItem.getCreatedDate());
        txtCreatedby.setText(commentItem.getCreatedby());
        description.setText(commentItem.getDescription());

        addComment.setOnClickListener(view1 -> {
            setCommentId(commentItem.getCommentID());
            showAlert(commentItem.getCommentTopic());
//            Toast.makeText(context, getCommentId()+"", Toast.LENGTH_SHORT).show();
        });

        if (commentItem.getSubDescriptions().size() != 0) {
            subContainer.removeAllViews();
            for (ViewActCompProject.SubDescription subItem : commentItem.getSubDescriptions()) {
                View subInflater = setUpSubDescription(subItem);
                subContainer.addView(subInflater);
            }
            containerView.setOnClickListener(view1 -> {
                if (subContainer.getVisibility() == View.VISIBLE) {
                    subContainer.setVisibility(View.GONE);
                } else {
                    subContainer.setVisibility(View.VISIBLE);
                }
            });
        }
        return inflater;
    }

    public View setUpSubDescription(ViewActCompProject.SubDescription subItem) {
        View subInflater = LayoutInflater.from(context).inflate(R.layout.comment_sub_item, null, false);

        TextView subCommentDate = subInflater.findViewById(R.id.subCommentDate);
        TextView subCommentBy = subInflater.findViewById(R.id.subCommentBy);
        TextView subDescription = subInflater.findViewById(R.id.subDescription);

        subCommentDate.setText(subItem.getCreatedDate());
        subCommentBy.setText(subItem.getCreatedBy());
        subDescription.setText(subItem.getSubDescription());
        return subInflater;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public interface ICommentListener {
        void showMessage(String message);

        void updateUI(List<ViewActCompProject.Comment> commentList);

        void showProgress();

        void dismissProgress();

    }
}
