package com.pjt.andrdgambas.HOME;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.PRDDETAIL.PrdDetailActivity;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;
import com.pjt.andrdgambas.SUBSCRIBE.Activity_Subscribe_ContentsList;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private Context context;
    private ArrayList<HomeData> mData = new ArrayList<>();
    public String fileName = "test.png";

    // 생성자에서 데이터 리스트 객체를 전달받음.
    HomeAdapter(Context context, ArrayList<HomeData> list) {
        this.context = context;
        this.mData = list ;
    }
    //ClickEvent 이름을 정하고 인터페이스로 선언
    public interface OnItemClickListener {
        void onItemClickListener1(View v, int position);
    }
    //만든 인터페이스를 전역변수로 선언
    private OnItemClickListener mListener = null;

    //OnItemClickListener 를 객체 생성할때 값을 넣어주기 위해서 선언
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, nickname, price, day, term, liked, subs;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            title = (TextView)itemView.findViewById(R.id.tv_title) ;
            nickname = (TextView)itemView.findViewById(R.id.tv_nickname);
            imageView = (ImageView)itemView.findViewById(R.id.iv_product);
            price = (TextView)itemView.findViewById(R.id.tv_price) ;
            day = (TextView)itemView.findViewById(R.id.tv_day);
            term = (TextView)itemView.findViewById(R.id.tv_term);
            liked = (TextView)itemView.findViewById(R.id.tv_liked);
            subs = (TextView)itemView.findViewById(R.id.tv_subs);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    STATICDATA.PRD_SEQNO = mData.get(pos).getPrdSeq();
                    Log.e("PrdSeqno", String.valueOf(STATICDATA.PRD_SEQNO));

                    Intent intent = new Intent(view.getContext(), PrdDetailActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homelistview, parent, false);
        HomeAdapter.ViewHolder vh = new HomeAdapter.ViewHolder(view) ;
        return vh ;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        String nickname =mData.get(position).getNickname();
        String price = mData.get(position).getPrice();
        String day = mData.get(position).getDay();
        String term = mData.get(position).getTerm();
        String liked = mData.get(position).getLike();
        String subs = mData.get(position).getSubs();

        if(subs.equals("null")){
            subs = "0";
        }
        String image = mData.get(position).getImage();

        holder.title.setText(title) ;
        holder.nickname.setText(nickname);
        holder.price.setText(price + " 원");
        holder.day.setText(day);
        holder.term.setText(term);
        holder.liked.setText(liked);
        holder.subs.setText(subs);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference dateRef = storageRef.child("prdImage/" + image); // fileName은 테스트용!!! image로 넣어야함.
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.v("Firebase URL", uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .apply(new RequestOptions().centerCrop())
                        .placeholder(R.drawable.gambaslogo)
                        .into(holder.imageView);
            }
        });





    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}

