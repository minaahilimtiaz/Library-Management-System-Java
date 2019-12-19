/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Minahil Imtiaz
 */
public class Librarian extends Staff {

    Librarian(int user_id, String user_name, char gender) {
        super(user_id, user_name, gender);
    }

    public void AddBook(ArrayList<Books> BooksList ,String NewAuthor ,String NewTitle ,String NewSubject , int quantity) {

        Books LastBook = BooksList.get(BooksList.size()-1);
        int book_id = LastBook.GetBookId();
        book_id += 1;
        
        Books NewBook = new Books(book_id, NewTitle, NewAuthor, NewSubject, quantity);
        dbConnectivity db = new dbConnectivity();
        db.AddNewBook(book_id, NewTitle, NewAuthor, NewSubject, quantity);
        BooksList.add(NewBook);

    }

    public boolean DeleteBook(ArrayList<Books> BooksList, int book_id) {

       boolean deleted=false;
        Books ToDelete=new Books();
        dbConnectivity db = new dbConnectivity();
        boolean result =db.DeleteABook(book_id);
        if(result == true) {
        for (Books B : BooksList) {
            if (B.GetBookId() == book_id) {
                
                ToDelete=B;
                break;
            }
        }
 
         BooksList.remove(ToDelete);
         deleted=true;
        }
        
        
      return deleted;
    }

    public void ChangeInfo(ArrayList<Books> BooksList, int book_id , int command , String NewInfo , int Quantity) {

//        System.out.print("Enter the id of the book you want to update");
//        Scanner input = new Scanner(System.in);
//        int book_id = input.nextInt();
        for (Books b : BooksList) {
            if (b.GetBookId() == book_id) {

//                System.out.print("Press \n 1.to change title \n 2. to change author \n 3.to change subject \n 4.to change quantity");
//
//                int command = input.nextInt();
                if (command == 1) {
//                    System.out.print("Enter the  new title ");
//                    String Title = input.next();
                    b.SetTitle(NewInfo);
                } else if (command == 2) {
//                    System.out.print("Enter the new  author name ");
//                    String A_name = input.next();
                    b.SetAuthor(NewInfo);
                } else if (command == 3) {

//                    System.out.print("Enter the new  subject ");
//                    String subject = input.next();
                    b.SetSubject(NewInfo);
                } else if (command == 4) {
//                    System.out.print("Enter the new  quantity  ");
//                    int quantity = input.nextInt();
                    b.SetQuantity(Quantity);

                } else {
                    System.out.print("You pressed invalid key ! Can't perform any operation ");

                }
            }

        }

    }
}
