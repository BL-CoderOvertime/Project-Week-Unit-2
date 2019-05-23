package com.wkdrabbit.projectweekunit2;

public class Review {
	
	private String review;
	private int rating;
	
	public Review(String review, int rating){
		this.rating = rating;
		this.review = review;
	}
	
	public String getReview() {
		return review;
	}
	
	public int getRating() {
		return rating;
	}
	
	public Boolean containsMenuItem(MenuItem menuItem){
		return review.contains(menuItem.getName());
	}
	
	public int getRatingFromReview(){
		String[] contextWords;
		int[] ratingArr = new int[5];
		int value = 0;
		int totalWords = 0;
		
		for(int i = 0; i < 5; i++){
			switch (i){
				case 0:
					contextWords = Constants.MENU_RATING_ONE_STAR;
					value = 1;
					break;
				case 1:
					contextWords = Constants.MENU_RATING_TWO_STAR;
					value = 2;
					break;
				case 2:
					contextWords = Constants.MENU_RATING_THREE_STAR;
					value = 3;
					break;
				case 3:
					contextWords = Constants.MENU_RATING_FOUR_STAR;
					value = 4;
					break;
				case 4:
					contextWords = Constants.MENU_RATING_FIVE_STAR;
					value = 5;
					break;
					default:
						contextWords = new String[0];
			}
			
			for(int j = 0; j < contextWords.length; j++){
				if(review.contains(contextWords[j])){
					totalWords++;
					ratingArr[value-1]++;
				}
			}
		}
		
		return ((ratingArr[0]) + (ratingArr[1] * 2) + (ratingArr[2] * 3) + (ratingArr[3] * 4) + (ratingArr[4] * 5))/totalWords;
		
	}
}
