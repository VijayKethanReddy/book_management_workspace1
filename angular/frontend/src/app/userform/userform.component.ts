import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import User from '../entity/User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-userform',
  templateUrl: './userform.component.html',
  styleUrls: ['./userform.component.scss']
})
export class UserformComponent{
  user:User= new User(1,'Ram', 20);

  constructor(public userService: UserService) { }

  save(){
    console.log("clicked");
    const observable = this.userService.saveUser(this.user);
    observable.subscribe((response)=>{
      console.log(response);
      const id= response;
      console.log("id:"+id);
    },
    (error)=>{
      alert('Something went wrong');
    })
  }

  get(){
    console.log("inside get");
    // const observable: Observable<User> = this.userService.getUser();
    // var l1 = new Array<User>();
    // observable.forEach(element: any => {
    //   console.log(element);
    //   l1.push(element);
    // });
    // observable.forEach(function(u1){
    //   return l1.push(u1);
    // })
    // observable.subscribe((response)=>{
    //   console.log(response);
    //   l1 = response;
    // })
  }
}
