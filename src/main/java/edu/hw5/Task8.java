package edu.hw5;

public class Task8 {
    public Task8() {
    }

    public Boolean hasOddLength(String string) {
        return string.matches("[01]([01][01])*");
    }

    public Boolean isStartsWithZeroHasOddLenOrStartsWithOneHasEvenLen(String string) {
        return string.matches("(0|1[01])([01][01])*");
    }

    public Boolean hasNumberOfZeroesDivisibleByThree(String string) {
        return string.matches("1*(1*01*01*01*)*");
    }

    public Boolean isAnyStringExcept11Or111(String string) {
        return string.matches("(?!111$|11$)([01]*)");
    }

    public Boolean hasEveryCharOnOddPositionEqualsZero(String string) {
        return string.matches("0([01]0|[01]$)*"); // or [01](0[01])* for 0-indexed
    }

    public Boolean hasAtLeast2ZeroesAndAtMost1One(String string) {
        return string.matches("((0+1?0+)|(0*1?00+)|(00+1?0*))");
    }

    public Boolean hasNoConsecutiveOnes(String string) {
        return string.matches("0*(1$|10+)*");
    }
}
