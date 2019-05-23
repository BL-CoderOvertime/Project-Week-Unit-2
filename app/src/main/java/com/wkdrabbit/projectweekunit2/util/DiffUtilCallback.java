package com.wkdrabbit.projectweekunit2.util;

import android.support.v7.util.DiffUtil;

import com.wkdrabbit.projectweekunit2.MenuItem;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {
	
	private List<MenuItem> oldList;
	private List<MenuItem> newList;
	
	public DiffUtilCallback(List<MenuItem> oldList, List<MenuItem> newList) {
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
