package com.example.yujin.whereismyroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yujin.whereismyroom.DetailRoomActivity;
import com.example.yujin.whereismyroom.R;
import com.example.yujin.whereismyroom.RecyclerViewClickListener;
import com.example.yujin.whereismyroom.Room;
import com.example.yujin.whereismyroom.common.Util;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> roomList;
    private Context context;
    private Util util;
    private static RecyclerViewClickListener itemListener;

    public RoomAdapter(Context context, RecyclerViewClickListener itemListener, List<Room> roomList) {
        this.context = context;
        this.itemListener = itemListener;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        util = new Util();
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, final int position) {
        holder.imgRoom.setImageResource(R.drawable.room_sample);    // TODO: 이미지 추가 할 것
        holder.rentType.setText(roomList.get(position).rentType);

        if (roomList.get(position).rentType.equals("전세")) {
            holder.rentCost.setText(util.calDeposit(String.valueOf(roomList.get(position).deposit)));
        } else {
            holder.rentCost.setText(util.calDeposit(roomList.get(position).deposit) + "/" + roomList.get(position).rentMonth);
        }

        String detail = roomList.get(position).roomType.equals("-") ? "" : roomList.get(position).roomType + " | ";
        detail += roomList.get(position).myFloor.equals("-") ? "" : roomList.get(position).myFloor + "층 | ";
        detail += roomList.get(position).roomSizeP.length() == 0 ? "" : roomList.get(position).roomSizeP + "평 | "; // TODO: 제곱미터 or 평?
        detail += roomList.get(position).utilities.equals("0") ? "" : "관리비 " + roomList.get(position).utilities + "만 |";

        holder.roomDetail.setText(detail);

        // TODO: 지하철 추가
        holder.imgSubwayLine.setImageResource(R.drawable.line2);
        holder.subwayStation.setText("역삼");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailRoomActivity.class);
                intent.putExtra("pageType", "DETAIL");
                intent.putExtra("room", roomList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView imgRoom;
        TextView rentType;
        TextView rentCost;
        TextView roomDetail;
        ImageView imgSubwayLine;
        TextView subwayStation;

        RoomViewHolder(View view) {
            super(view);
            imgRoom = view.findViewById(R.id.item_img_room);
            rentType = view.findViewById(R.id.item_txt_rent_type);
            rentCost = view.findViewById(R.id.item_txt_rent_cost);
            roomDetail = view.findViewById(R.id.item_txt_room_detail);
            imgSubwayLine = view.findViewById(R.id.item_img_subway_line);
            subwayStation = view.findViewById(R.id.item_txt_subway_station);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            return false;
        }
    }
}