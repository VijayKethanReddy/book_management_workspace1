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
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Content', 'Active'];
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
    console.log("clicked");
    const observable = this.bookService.getAllPurchasedBooks();
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
      this.message = "";
    },
    (error)=>{
      this.message = "No purchased books found for reader";
      this.books = [];
    })
  }

  searchPurchasedBookByPaymentId(){
    console.log("clicked");
    this.isSearched = true;
    const { paymentId } = this.form;
    const observable = this.bookService.searchPurchasedBookByPaymentId(paymentId);
    observable.subscribe((books)=>{
      console.log(books);
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
      this.message = "No search results found. Please verify the payment id and search";
      this.books = [];
    })
  }

  tableRowClicked(book: any){
    console.log("clicked");
    console.log(book);
    this.bookService.book1 = book; 
    this.router.navigate(['/readbook']);
  }

}
