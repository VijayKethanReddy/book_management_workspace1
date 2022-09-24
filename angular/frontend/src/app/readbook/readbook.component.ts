import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-readbook',
  templateUrl: './readbook.component.html',
  styleUrls: ['./readbook.component.scss']
})
export class ReadbookComponent implements OnInit {
  book1 : any;
  form: any;
  successMessage: any = "";
  errorMessage: any = "";
  isSuccessful = false;

  constructor(public bookService: BookService) { 
    AppComponent.isInitialHome=false;
    this.book1 = this.bookService.book1;
    this.form = this.book1;
  }

  ngOnInit(): void {
  }

  refund(): void{
    this.book1 = this.form;
    const observable = this.bookService.getRefund(this.book1.id);
    observable.subscribe((response)=>{
      if(Number.isFinite(Number(response))){
        this.successMessage = "Refund is successful. Book "+this.book1.title+" is removed from purchased book list";
        this.errorMessage = "";
      }
      if(response == null){
        this.errorMessage = "Failed to get refund for purchased book.";
        this.successMessage = "";
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.errorMessage = "Failed to get refund for purchased book.";
      this.successMessage = "";
    })
  }

}
