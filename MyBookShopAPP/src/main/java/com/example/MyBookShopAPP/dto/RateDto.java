package com.example.MyBookShopAPP.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RateDto {

    private int totalRate;
    private int countStarsOne;
    private int countStarsTwo;
    private int countStarsThree;
    private int countStarsFour;
    private int countStarsFive;
    private int avgRate;

    private List<Integer> allCountStars;

    public void addAllCountStars(){
        allCountStars = new ArrayList<>();
        allCountStars.add( this.countStarsOne);
        allCountStars.add(this.countStarsTwo);
        allCountStars.add(this.countStarsThree);
        allCountStars.add(this.countStarsFour);
        allCountStars.add(this.countStarsFive);
    }
}
