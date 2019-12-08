package com.example.booklistingapk;

public class BookClass {
    String title;
    String publisher;
    String date;
    String link;
    String img;
    //String description;
    public BookClass(String Title, String Publisher, String Date, String Link, String Img){
        title=Title;
        publisher=Publisher;
        date=Date;
        link=Link;
        img=Img;
    }
    public void settitle(String Title){
        title=Title;
    }
    public void setPublisher(String Publisher){
        publisher=Publisher;
    }
    public void setdate(String Date){
        date=Date;
    }
    public void setLink(String Link){
        link=Link;
    }
    public void setImg(String Img){
        img=Img;
    }
    //public void setdescription(String Description){
    //  description=Description;
    // }
    public String gettitle(){
        return title;
    }
    public String getPublisher(){
        return publisher;
    }
    public String getdate(){
        return date;
    }
    public String getLink(){return link;}
    public String getImg(){return img;}
//   public String getdescription(){
    //    return description;
    //  }
}
