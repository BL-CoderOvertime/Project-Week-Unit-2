package com.wkdrabbit.projectweekunit2.util;

import android.support.v7.util.DiffUtil;

import com.wkdrabbit.projectweekunit2.UserHistoryItem;

import java.util.List;

public class DiffUtilCallBackUserHistoryItem extends DiffUtil.Callback{
	
	
	private List<UserHistoryItem> oldList;
	private List<UserHistoryItem> newList;
	
	public DiffUtilCallBackUserHistoryItem(List<UserHistoryItem> oldList, List<UserHistoryItem> newList) {
		this.oldList = oldList;
		this.newList = newList;
	}
	
	
	@Override
	public int getOldListSize() {
		return oldList.size();
	}
	
	@Override
	public int getNewListSize() {
		return newList.size();
	}
	
	@Override
	public boolean areItemsTheSame(int oldPos, int newPos) {
		return oldPos == newPos;
	}
	
	@Override
	public boolean areContentsTheSame(int oldPos, int newPos) {
		return oldList.get(oldPos) == newList.get(newPos);
	}
}

