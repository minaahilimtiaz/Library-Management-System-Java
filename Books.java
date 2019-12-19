/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Minahil Imtiaz
 */
public class Books {

    private int book_id;
    private String author;
    private String title;
    private String subject;
    private int quantity; //a book may have more than one quantity

    Books() {
        this.book_id = -1;
        this.author = "  ";
        this.title = "  ";
        this.subject = "  ";
        this.quantity = 0;

    }

    Books(int book_id, String author, String title, String subject, int quantity) {

        this.book_id = book_id;
        this.author = author;
        this.title = title;
        this.subject = subject;
        this.quantity = quantity;
    }

    public void SetBookId(int book_id) {
        this.book_id = book_id;

    }

    public void SetAuthor(String author) {
        this.author = author;
        dbConnectivity db = new dbConnectivity();
        db.ChangeBookInfo(book_id, author, 2);
    }

    public void SetTitle(String title) {
        this.title = title;
        dbConnectivity db = new dbConnectivity();
        db.ChangeBookInfo(book_id, title, 1);
    }

    public void SetQuantity(int quantity) {
        this.quantity = quantity;
        dbConnectivity db = new dbConnectivity();
        db.UpdateBookQuantity(this.quantity, this.book_id);

    }

    public void SetSubject(String subject) {

        this.subject = subject;
        dbConnectivity db = new dbConnectivity();
        db.ChangeBookInfo(book_id, subject, 3);

    }

    public String GetTitle() {

        dbConnectivity db = new dbConnectivity();
        String titleofbook = db.GetTitleofBook(this.book_id);
        return titleofbook;
    }

    public String GetAuthor() {
        dbConnectivity db = new dbConnectivity();
        String authorofbook = db.GetAuthorofBook(this.book_id);
        return authorofbook;
    }

    public int GetBookId() {

        return this.book_id;

    }

    public String GetSubject() {
        dbConnectivity db = new dbConnectivity();
        String subjectofbook = db.GetSubjectofBook(this.book_id);
        return subjectofbook;
        //  return this.subject;
    }

    public Books GetaBook() {
        dbConnectivity db = new dbConnectivity();
        Books MyBook = db.GetaBookbyId(this.book_id);
        return MyBook;
    }

    public int GetQuantity() {
        dbConnectivity db = new dbConnectivity();
        int quantity_available = db.GetQuantityofBook(this.book_id);
        // return this.quantity;
        return quantity_available;

    }

    public boolean ChekcAvailability(int book_id) {
        dbConnectivity db = new dbConnectivity();
        int quantity_available = db.GetQuantityofBook(this.book_id);
        if (quantity_available <= 0) {
            return false;
        } else {
            return true;
        }

    }

    public void DecreaseQuantity() {
        if (quantity > 0) {
            this.quantity = this.quantity - 1;
            dbConnectivity db = new dbConnectivity();
            db.UpdateBookQuantity(this.quantity, book_id);
        }

    }

    public void IncreaseQuantity() {
        this.quantity = this.quantity + 1;
        dbConnectivity db = new dbConnectivity();
        db.UpdateBookQuantity(this.quantity, book_id);
    }

    public String PrintInformation() {
        boolean available = ChekcAvailability(this.book_id);
        String status;
        if (available == true) {
            status = "available";
        } else {
            status = "not available";
        }
        String Resultant=" ";
        Resultant =Resultant+"    "+this.book_id + "\t" + this.title + "\t" + this.author + "\t" + this.subject + "\t" + this.quantity + "\t" + status + "\n";
       return Resultant;
    }

}
