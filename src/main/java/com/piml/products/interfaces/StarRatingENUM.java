package com.piml.products.interfaces;

public enum StarRatingENUM {
    ONE(1l),
    TWO(2L),
    THREE(3L),
    FOUR(4L),
    FIVE(5L);

    private final Long numberOfStars;

    StarRatingENUM(Long numberOfStars) { this.numberOfStars = numberOfStars; }

    public Long getNumberOfStars() { return numberOfStars; }

    public static StarRatingENUM convertToEnum (Long numberOfStars) {
        StarRatingENUM convertedStarEnum;
        switch (numberOfStars.intValue()) {
            case 1:
                convertedStarEnum = ONE;
                break;
            case 2:
                convertedStarEnum = TWO;
                break;
            case 3:
                convertedStarEnum = THREE;
                break;
            case 4:
                convertedStarEnum = FOUR;
                break;
            case 5:
                convertedStarEnum = FIVE;
                break;
            default:
                throw new RuntimeException("Invalid number of stars");
        }
        return convertedStarEnum;
    }
}
