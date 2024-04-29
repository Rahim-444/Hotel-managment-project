package main.java.com.example.Poo.model;

public class Date {
  public int year;
  public int month;
  public int day;

  public Date(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public int CompareTo(Date date) {
    if (this.year == date.year && this.month == date.month && this.day == date.day) {
      return 0;
    } else if (this.year > date.year) {
      return 1;
    } else if (this.year < date.year) {
      return -1;
    } else if (this.month > date.month) {
      return 1;
    } else if (this.month < date.month) {
      return -1;
    } else if (this.day > date.day) {
      return 1;
    } else {
      return -1;
    }
  }

  public static int difference(Date date1, Date date2) {
    int diff = 0;
    if (date1.year == date2.year) {
      if (date1.month == date2.month) {
        diff = date1.day - date2.day;
      } else {
        diff = date1.month - date2.month;
        diff = diff * 30 + date1.day - date2.day;
      }
    } else {
      diff = date1.year - date2.year;
      diff = diff * 365 + date1.month - date2.month;
      diff = diff * 30 + date1.day - date2.day;
    }
    return diff;
  }

  public String toString() {
    return this.year + "/" + this.month + "/" + this.day;
  }
}
