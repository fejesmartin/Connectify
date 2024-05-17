import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../models/User';
import { AxiosService } from '../axios.service';
import { baseURL } from '../../assets/baseURL';

@Component({
  selector: 'app-visit-user-profile',
  templateUrl: './visit-user-profile.component.html',
  styleUrls: ['./visit-user-profile.component.css']
})
export class VisitUserProfileComponent {
  user: User = {
    id: 0,
    username: "",
    email: "",
    authorities: [""],
    enabled: false,
    userId: 0,
    accountNonExpired: false,
    accountNonLocked: false,
    credentialsNonExpired: false
  };

  constructor(private route: ActivatedRoute, private axiosService: AxiosService) {}

  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        const username = params.get("username");
        if(username){
          this.axiosService.request("GET",baseURL + `/api/users/getByName/${username}`, null).then(response => this.user = response.data)
        }
      })
    };
  }
