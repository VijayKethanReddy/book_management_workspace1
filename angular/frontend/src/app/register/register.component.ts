import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  form: any = {
    name: null,
    userName: null,
    emailId: null,
    password: null,
    author: false,
    reader: false
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  role:string[]=[];

  constructor(private authService: AuthService) { 
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { name, userName, emailId, password, author, reader } = this.form;
    this.role=(author && reader) ? ["author","reader"]:author?["author"]:reader?["reader"]:[];
    this.authService.register(name, userName, emailId, password, this.role).subscribe(
      response => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error => {
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
