import { Component, OnInit } from '@angular/core';
import { BookService } from '../services/bookservice/book.service';
import { BookCategory } from '../entity/Book';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bookform',
  templateUrl: './bookform.component.html',
  styleUrls: ['./bookform.component.scss']
})
export class BookformComponent implements OnInit {
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  books: any = [];
  title: String = 'Book1';
  category: BookCategory = BookCategory.ADVENTURE;
  author: String = 'David';
  price: number = 1;
  publisher: String = 'ABC Publisher';
  message: any = "";
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Active'];
  column: any ="";
  
  constructor(public bookService: BookService, private router: Router) {
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
    this.categoryList.push(this.bookCategory.COMEDY);
    this.categoryList.push(this.bookCategory.EPIC);
    this.categoryList.push(this.bookCategory.FANTASY);
    this.categoryList.push(this.bookCategory.FICTION);
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }

  searchBooks(){
    console.log("clicked");
    if(this.price == null || this.price == undefined){
      this.price = 0;
    }
    const observable = this.bookService.searchBooks(this.title, this.category, this.author, this.price, this.publisher);
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
      this.books = books;
      this.message = "";
      if(this.books.length == 0){
        this.message = "No search results found. Please verify the details and search";
        this.books = [];
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.message = "No search results found. Please verify the details and search";
      this.books = [];
    })
  }

  tableRowClicked(book: any){
    console.log("clicked");
    console.log(book);
    this.bookService.book1 = book; 
    this.router.navigate(['/buybook']);
  }

}
