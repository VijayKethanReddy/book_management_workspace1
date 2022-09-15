import { Component, OnInit } from '@angular/core';
import { BookService } from '../services/bookservice/book.service';
import Book, { BookCategory } from '../entity/Book';
import { GetallauthorbooksComponent } from '../getallauthorbooks/getallauthorbooks.component';
import { AppComponent } from '../app.component';

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
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Content', 'Active'];
  column: any ="";
  
  constructor(public bookService: BookService) {
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }

  searchBooks(){
    console.log("clicked");
    const observable = this.bookService.searchBooks(this.title, this.category, this.author, this.price, this.publisher);
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
      this.books = books;
      this.message = "";
    },
    (error)=>{
      this.message = "No search results found. Please verify the details and search";
      this.books = [];
    })
  }

}
