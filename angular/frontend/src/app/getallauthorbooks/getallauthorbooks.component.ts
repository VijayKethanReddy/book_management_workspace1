import { Component, OnInit } from '@angular/core';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-getallauthorbooks',
  templateUrl: './getallauthorbooks.component.html',
  styleUrls: ['./getallauthorbooks.component.scss']
})
export class GetallauthorbooksComponent implements OnInit {
  books: any = [];
  message: any = "";
  displayedColumns: string[] = ['id', 'title', 'logo', 'category', 'authorUserName', 'authorName', 'price', 'publisher', 'publishedDate', 'content', 'active'];
  column: any ="";

  constructor(public bookService: BookService) { }

  ngOnInit(): void {
      this.getAllAuthorBooks();
  }

  getAllAuthorBooks(){
    console.log("clicked");
    const observable = this.bookService.getAllAuthorBooks();
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
      this.message = "";
    },
    (error)=>{
      this.message = "No books found for author";
      this.books = [];
    })
  }

}
