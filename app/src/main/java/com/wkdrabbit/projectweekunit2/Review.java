package com.wkdrabbit.projectweekunit2;

public class Review {
	
	private String review;
	private int rating;
	
	public Review(String review, int rating){
		this.rating = rating;
		this.review = review.toLowerCase();
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
	
	public double getRatingFromReview(){
		String[] contextWords;
		int[] ratingArr = new int[5];
		int value = 0;
		int totalWords = 1;
		
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
					
					ratingArr[value-1]++;
				}
			}
		}
		
		double value1 = ratingArr[0];
		double value2 = ratingArr[1] * 2;
		double value3 = ratingArr[2] * 3;
		double value4 = ratingArr[3] * 4;
		double value5 = ratingArr[4] * 5;
		
		double totalValue =
				value1 +
				value2 +
				value3 +
				value4 +
				value5;
		
		double result;
		
		if(totalValue > 0) {
			 result = totalValue / totalWords;
		} else {result = 0;}
		
		return result;
		
	}
}
