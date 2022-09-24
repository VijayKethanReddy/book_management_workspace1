import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-purchasedbooks',
  templateUrl: './purchasedbooks.component.html',
  styleUrls: ['./purchasedbooks.component.scss']
})
export class PurchasedbooksComponent implements OnInit {
  books: any = [];
  message: any = "";
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Active'];
  column: any ="";
  form: any = {
    paymentId: 1
  };
  isSearched: any = false;

  constructor(public bookService: BookService, private router: Router) { 
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
    this.getAllPurchasedBooks();
  }

  getAllPurchasedBooks(){
    const observable = this.bookService.getAllPurchasedBooks();
    observable.subscribe((books)=>{
      this.books = books;
      if(this.books.length == 0){
        this.message = "No purchased books found for reader";
      }
      else{
        this.message = "";
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.message = "No purchased books found for reader";
      this.books = [];
    })
  }

  searchPurchasedBookByPaymentId(){
    this.isSearched = true;
    const { paymentId } = this.form;
    const observable = this.bookService.searchPurchasedBookByPaymentId(paymentId);
    observable.subscribe((books)=>{
      this.books = [];
      if(books == null){
        this.message = "No search results found. Please verify the payment id and search";
      }
      else{
        this.books.push(books);
        this.message = "";
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.message = "No search results found. Please verify the payment id and search";
      this.books = [];
    })
  }

  tableRowClicked(book: any){
    this.bookService.book1 = book; 
    this.router.navigate(['/readbook']);
  }

}
