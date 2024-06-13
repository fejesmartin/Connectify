import { Component, OnInit } from '@angular/core';
import { User } from '../models/User';
import { AxiosService } from '../axios.service';
import { baseURL } from '../../assets/baseURL';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  friends: User[] = [];
  id: number = 0;
  showNotifications = false;

  constructor(private axiosService: AxiosService, private cookieService: CookieService) { }

  ngOnInit(): void {
    this.id = Number(this.cookieService.get("user_id"));
    this.getFriends();
    };

  toggleNotifications(): void{
    this.showNotifications = !this.showNotifications;
  }  

  getFriends(): void {
    this.axiosService.request("GET", baseURL + `/api/users/getFriendsById/${this.id}`, null).then(response => this.friends = response.data)
  }
}
