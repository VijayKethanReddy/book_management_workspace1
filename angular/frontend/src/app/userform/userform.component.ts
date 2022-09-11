import { Component, OnInit } from '@angular/core';
import User from '../entity/User';

@Component({
  selector: 'app-userform',
  templateUrl: './userform.component.html',
  styleUrls: ['./userform.component.scss']
})
export class UserformComponent{
  user:User= new User('Ram', 20);

  constructor() { }
}
